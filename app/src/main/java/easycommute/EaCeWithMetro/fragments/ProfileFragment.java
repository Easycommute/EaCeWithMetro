package easycommute.EaCeWithMetro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import easycommute.EaCeWithMetro.utils.Refreshable;

public class ProfileFragment extends BaseFragment implements easycommute.EaCeWithMetro.utils.Refreshable {

    private Object implObj = null;

    public ProfileFragment() {
        // Required empty public constructor
        Object a = 2;
      //  Refreshable.refresh(a);
        showToast("Aditi: you won with the value!" + a.toString());

        a = 5;
        showToast("Aditi: you won with the value!" + a.toString());

    }

    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.title_profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PreferenceManager prefs =  new PreferenceManager(getActivity());

        showToast("Aditi: hello hi you won with the value!");
        String s = "ping pong";
       // Refreshable.refresh(s);

    }


}
