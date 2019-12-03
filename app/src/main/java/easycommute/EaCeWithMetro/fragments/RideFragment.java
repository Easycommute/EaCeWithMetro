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

/**
 * A simple {@link Fragment} subclass.
 */
public class RideFragment extends BaseFragment {

    Spinner spinnerStarting,spinnerEnd;
    List<String> startingList, endList;
    TextView txtTitle;
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
        return R.string.ride;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
        loadSpinner();
    }

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


        spinnerStarting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {

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
                    btnValidate.setText(getResources().getString(R.string.get_easy_token));
                    btnValidate.setBackgroundResource(R.drawable.rounded_button);
                    btnValidate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init()
    {
        spinnerStarting=(Spinner)getView().findViewById(R.id.spinnerStarting);
        spinnerEnd=(Spinner)getView().findViewById(R.id.spinnerEnd);
        txtTitle=(TextView) getView().findViewById(R.id.txtTitle);
        btnValidate=(Button) getView().findViewById(R.id.btnValidate);
        btnAddMoney=(Button) getView().findViewById(R.id.btnAddMoney);
        btnValidate.setVisibility(View.GONE);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddMoney.setVisibility(View.VISIBLE);
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.navigateToFragment(new WalletFragment(), new WalletFragment().getTag(), false, true);

            }
        });



    }


}
