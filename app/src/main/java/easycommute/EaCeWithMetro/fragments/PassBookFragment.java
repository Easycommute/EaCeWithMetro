package easycommute.EaCeWithMetro.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import easycommute.EaCeWithMetro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassBookFragment extends Fragment {


    public PassBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_book, container, false);
    }

}
