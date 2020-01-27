package easycommute.EaCeWithMetro.fragments;


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

import java.util.Arrays;
import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.utils.BaseFragment;

public class RideFragment extends BaseFragment {

    Spinner spinnerStarting,spinnerEnd;
    List<String> startingList, endList;
    TextView txtChangeSelection,txtTitle,title_label,title_label1,title_label2;
    View view1,view2,view3;
    Button btnValidate,btnAddMoney;

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
    }

    // bind data to spinner
    private void loadSpinner()
    {
        startingList = Arrays.asList(getResources().getStringArray(R.array.source));
        endList = Arrays.asList(getResources().getStringArray(R.array.destination));

        ArrayAdapter<String> startingAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, startingList);
        startingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStarting.setAdapter(startingAdapter);

        ArrayAdapter<String> endAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, endList);
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnd.setAdapter(endAdapter);

        spinnerEnd.setEnabled(false);
        spinnerEnd.setClickable(false);

        spinnerStarting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {
                    spinnerEnd.setEnabled(true);
                    spinnerEnd.setClickable(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
    public boolean onBackPressed() {
       // WalletFragment stopSelectionFragment = new WalletFragment();
       // launchFragment(stopSelectionFragment, stopSelectionFragment.getTag());
        return super.onBackPressed();
    }

    // widget initialization
    private void init()
    {
        spinnerStarting=(Spinner)getView().findViewById(R.id.spinnerStarting);
        spinnerEnd=(Spinner)getView().findViewById(R.id.spinnerEnd);
        txtTitle=(TextView) getView().findViewById(R.id.txtTitle);
        txtChangeSelection=(TextView) getView().findViewById(R.id.txtChangeSelection);

        title_label=(TextView) getView().findViewById(R.id.title_label);
        title_label1=(TextView) getView().findViewById(R.id.title_label1);
        title_label2=(TextView) getView().findViewById(R.id.title_label2);

        view1=(View) getView().findViewById(R.id.view1);
        view2=(View) getView().findViewById(R.id.view2);
        view3=(View) getView().findViewById(R.id.view3);

        title_label.setVisibility(View.GONE);
        title_label1.setVisibility(View.GONE);
        title_label2.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);

        btnValidate=(Button) getView().findViewById(R.id.btnValidate);
        btnAddMoney=(Button) getView().findViewById(R.id.btnAddMoney);

        btnValidate.setEnabled(false);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // btnAddMoney.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                title_label.setVisibility(View.VISIBLE);
                btnValidate.setText(getResources().getString(R.string.get_another_token));
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
