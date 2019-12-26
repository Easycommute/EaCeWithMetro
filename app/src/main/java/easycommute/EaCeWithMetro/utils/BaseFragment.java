package easycommute.EaCeWithMetro.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.interfaces.ActionEventListener;
import easycommute.EaCeWithMetro.interfaces.FragmentListener;
import retrofit.RetrofitError;
import rx.functions.Action1;

/**
 * Created by Ram Prasad on 10/28/2015.
 */
public class BaseFragment extends Fragment implements ActionEventListener {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected FragmentListener mActivity;
    protected String fragmentTitle;
    protected AlertDialog policyDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        //setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (FragmentListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "ClassCastException: ", e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mActivity != null) {
            mActivity.setFragment(this);
            setActionTitle();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // sending google analytics
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mActivity != null) {
            mActivity.setFragment(null);
        }

        mActivity = null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void onBackPressedFragment(){
        //No-op
    }

    protected void launchFragment(Fragment fragment, String tag) {
        if (mActivity != null) {
            mActivity.navigateToFragment(fragment, tag);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resource) {
        showToast(getString(resource));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    protected int getCommuterId() {
        return EasySingleton.getInstance().getCommuter().commuterId;
    }


    protected void updateProfileInfo() {
        if (mActivity != null) mActivity.setProfileInfo();
    }

    protected Action1<Throwable> errorHandler = new Action1<Throwable>() {

        @Override
        public void call(Throwable throwable) {
            try {
                if (throwable instanceof java.lang.NullPointerException) {
                    throwable.printStackTrace();
                    Log.d("ERROR_HANDLER", throwable.toString());
                    showToast(R.string.generic_error+throwable.toString());
                }else {
                    RetrofitError error = (RetrofitError) throwable;
                    hideProgressBar();

                    switch (error.getKind()) {
                        case NETWORK:
                            if (mActivity != null) mActivity.showNetworkDialog();
                            break;
                        default:
                            showToast(R.string.generic_error);
                            error.printStackTrace();
                    }
                }
            } catch(NullPointerException ex){
                Log.d("ERROR", ex.toString());
            } catch(Exception ex){
                Log.d("ERROR", ex.toString());
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        hideSoftKeyboard();
    }

    protected void hideSoftKeyboard() {
        if (mActivity != null)
            mActivity.hideSoftKeyboard(getActivity());
    }

    protected void showProgressBar() {
        if (mActivity != null) {
            mActivity.showProgressBar();
        }
    }

    protected void showProgressBarWithoutBackground() {
        if (mActivity != null) {
            mActivity.showProgressBarWithoutBackground();
        }
    }

    protected void hideProgressBar() {
        if (mActivity != null) {
            mActivity.hideProgressBar();
        }
    }



    public void setActionTitle() {
       // fragmentTitle = getString(getTitle());
        try {
            fragmentTitle = getString(getTitle());
        }catch(Throwable t) {
            fragmentTitle = "EasyCommute";
        }
        if (mActivity != null) {
            mActivity.setActionTitle(fragmentTitle);
        }
    }



    protected int getTitle() {
        return R.string.app_name;
    }



}
