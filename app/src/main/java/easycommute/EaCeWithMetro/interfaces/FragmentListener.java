package easycommute.EaCeWithMetro.interfaces;

import android.app.Activity;
import androidx.fragment.app.Fragment;

/**
 * Created by Ram Prasad on 10/28/2015.
 */
public interface FragmentListener {
    void setFragment(ActionEventListener fragment);

    void navigateToFragment(Fragment fragment, String tag);

    void navigateToFragment(Fragment fragment, String tag, boolean noHistory);

    void navigateToFragment(Fragment fragment, String tag, boolean noHistory, boolean clearStack);

    void showProgressBar();

    void showProgressBarWithoutBackground();

    void hideProgressBar();

    void showNetworkDialog();

    void hideSoftKeyboard(Activity activity);

    void setActionTitle(String title);

    void setProfileInfo();
}
