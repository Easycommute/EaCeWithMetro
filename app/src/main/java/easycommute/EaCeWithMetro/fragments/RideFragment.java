package easycommute.EaCeWithMetro.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.models.RideReq;
import easycommute.EaCeWithMetro.models.ride_screen.BookRideResponse;
import easycommute.EaCeWithMetro.models.ride_screen.GenerateTokenModel;
import easycommute.EaCeWithMetro.models.ride_screen.PlansMap;
import easycommute.EaCeWithMetro.models.ride_screen.RideModel;
import easycommute.EaCeWithMetro.models.ride_screen.SourceStopModel;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RideFragment extends BaseFragment {

    Spinner spinnerStarting,spinnerEnd;
    List<String> startingListDesc, endList;
    List<Integer> startingListKey;
    TextView txtChangeSelection,txtTitle,title_label;
    View view1;
    Button btnValidate,btnAddMoney;
    PreferenceManager preferenceManager = null;
    int account_balance,travel_plan_fare,travel_plan_id;
    List<PlansMap> plansMapList;
    GenerateTokenModel generateTokenModel;

    public RideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ride, container, false);
    }


    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.ride;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
        loadSpinner();
        showProgressBar();
        loadData();
    }

    private void loadData()
    {
        Commuter commuter=preferenceManager.getCommuter();

        RideReq rideReq= new RideReq("1",String.valueOf(commuter.commuterId),"1");
        EasyCommuteApi.getService().getCityActiveList(rideReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RideModel>() {
                    @Override
                    public void call(final RideModel rideModel)
                    {
                        hideProgressBar();
                        account_balance = rideModel.getAccount_balance();
                        startingListDesc.add(getResources().getString(R.string.select_starting_point));
                        startingListKey.add(0);
                        final List<SourceStopModel> list = new ArrayList<SourceStopModel>(rideModel.getSource_stops());
                        for (int i=0; i<list.size();i++)
                        {
                            startingListKey.add(list.get(i).getStopId());
                            startingListDesc.add(list.get(i).getDisplayName());
                        }
                        ArrayAdapter<String> startingAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, startingListDesc);
                        startingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerStarting.setAdapter(startingAdapter);


                        spinnerStarting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                if (position > 0)
                                {
                                    endList.clear();
                                    spinnerEnd.setEnabled(true);
                                    spinnerEnd.setClickable(true);
                                    int startingPoint = startingListKey.get(startingListDesc.indexOf(spinnerStarting.getSelectedItem()));
                                    plansMapList = rideModel.getPlans_map().get(startingPoint);

                                    endList.add(getResources().getString(R.string.select_end_point));
                                    for(int i=0;i<plansMapList.size();i++)
                                    {
                                        endList.add(plansMapList.get(i).getDestination_stop_display());
                                    }

                                    ArrayAdapter<String> endAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, endList);
                                    endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerEnd.setAdapter(endAdapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                  if (position>0) {
                                      spinnerEnd.setVisibility(View.GONE);
                                      spinnerStarting.setVisibility(View.GONE);
                                      txtTitle.setVisibility(View.VISIBLE);
                                      txtTitle.setText(plansMapList.get(position-1).getTravel_plan_display());
                                      travel_plan_fare = plansMapList.get(position-1).getTravel_plan_fare();

                                      travel_plan_id = plansMapList.get(position-1).getTravel_plan_id();
                                      if (account_balance < travel_plan_fare)
                                      {
                                          btnValidate.setText(getResources().getString(R.string.add_money));
                                      }
                                      txtChangeSelection.setVisibility(View.VISIBLE);
                                      btnValidate.setBackgroundResource(R.drawable.rounded_button);
                                      btnValidate.setVisibility(View.VISIBLE);
                                      btnValidate.setEnabled(true);
                                  }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                }, errorHandler);
    }

    private void getEasyToken(int travel_plan_id)
    {
        showProgressBar();
        generateTokenModel= new GenerateTokenModel(String.valueOf(travel_plan_id),String.valueOf(getCommuterId()));
        view1.setVisibility(View.VISIBLE);
        title_label.setVisibility(View.VISIBLE);

        EasyCommuteApi.getService().getEasyToken(generateTokenModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookRideResponse>()
                {
                    @Override
                    public void call(BookRideResponse generateTokenResponse)
                    {
                        title_label.setText(generateTokenResponse.getTokenResponse().getToken_display_msg());

                        if( generateTokenResponse.getTokenResponse().getAvaiable_balance() < travel_plan_fare)
                        {
                            btnAddMoney.setVisibility(View.VISIBLE);
                            btnValidate.setVisibility(View.GONE);
                        }
                        else
                        {
                            btnAddMoney.setVisibility(View.GONE);
                            btnValidate.setText(getResources().getString(R.string.get_another_token));
                        }

                        hideProgressBar();
                    }
                }, errorHandler);
    }

    // bind data to spinner
    private void loadSpinner()
    {
        endList.add(getResources().getString(R.string.select_end_point));
        ArrayAdapter<String> endAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, endList);
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnd.setAdapter(endAdapter);

        spinnerEnd.setEnabled(false);
        spinnerEnd.setClickable(false);

        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {
                    spinnerEnd.setVisibility(View.GONE);
                    spinnerStarting.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtChangeSelection.setVisibility(View.VISIBLE);
                    btnValidate.setBackgroundResource(R.drawable.rounded_button);
                    btnValidate.setVisibility(View.VISIBLE);
                    btnValidate.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // handle back press
    @Override
    public boolean onBackPressed()
    {
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage(getResources().getString(R.string.want_to_exit))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getActivity().finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
        return false;
    }

    // widget initialization
    private void init()
    {
        startingListKey=new ArrayList<>();
        startingListDesc=new ArrayList<>();
        endList = new ArrayList<>();

        spinnerStarting=(Spinner)getView().findViewById(R.id.spinnerStarting);
        spinnerEnd=(Spinner)getView().findViewById(R.id.spinnerEnd);
        txtTitle=(TextView) getView().findViewById(R.id.txtTitle);
        txtChangeSelection=(TextView) getView().findViewById(R.id.txtChangeSelection);
        preferenceManager = new PreferenceManager(getActivity());

        title_label=(TextView) getView().findViewById(R.id.title_label);
        view1=(View) getView().findViewById(R.id.view1);
        title_label.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        btnValidate=(Button) getView().findViewById(R.id.btnValidate);
        btnAddMoney=(Button) getView().findViewById(R.id.btnAddMoney);

        btnValidate.setEnabled(false);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (btnValidate.getText().toString().equals(getResources().getString(R.string.add_money)))
                {
                    Fragment fragment = new WalletFragment();
                    launchFragment(fragment, fragment.getTag());
                }
                else if (btnValidate.getText().toString().equals(getResources().getString(R.string.get_easy_token)))
                {
                    getEasyToken(travel_plan_id);
                }
                else if (btnValidate.getText().toString().equals(getResources().getString(R.string.get_another_token)))
                {
                    getEasyToken(travel_plan_id);
                }
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.navigateToFragment(new WalletFragment(), new WalletFragment().getTag(), false, true);

            }
        });

        txtChangeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                spinnerStarting.setVisibility(View.VISIBLE);
                spinnerEnd.setVisibility(View.VISIBLE);
                spinnerEnd.setSelection(0);
                spinnerStarting.setSelection(0);
                btnValidate.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                txtChangeSelection.setVisibility(View.GONE);
                spinnerEnd.setEnabled(false);
                spinnerEnd.setClickable(false);
                btnValidate.setBackgroundResource(R.drawable.rounded_button_disable);
                btnValidate.setText(getResources().getString(R.string.get_easy_token));
                btnValidate.setEnabled(false);
            }
        });
    }
}
