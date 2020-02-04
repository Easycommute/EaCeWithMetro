package easycommute.EaCeWithMetro.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.fragments.RideFragment;
import easycommute.EaCeWithMetro.interfaces.ActionEventListener;
import easycommute.EaCeWithMetro.interfaces.FragmentListener;
import retrofit.RetrofitError;
import rx.functions.Action1;

/**
 * Created by Ram Prasad on 10/11/2015.
 */
public class BaseActivity extends AppCompatActivity implements FragmentListener {
    private RelativeLayout progressLayout;
    protected Toolbar mToolbar;
    AlertDialog dialog;
    private ActionEventListener mFragment;

    protected void initLayout() {
        // set up progress layout
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);

        if (progressLayout != null) {
            progressLayout.setOnClickListener(null);
        }

        // set up toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public void setFragment(ActionEventListener fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag) {
        navigateToFragment(fragment, tag, false, false);
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag, boolean noHistory) {
        navigateToFragment(fragment, tag, noHistory, false);
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag, boolean noHistory, boolean clearStack) {
        FragmentTransaction beginTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (clearStack) {
            getSupportFragmentManager().popBackStack(null, FragmentManager
                    .POP_BACK_STACK_INCLUSIVE);
        }

        if (!noHistory) {
           beginTransaction.addToBackStack(tag);
        }

        beginTransaction.replace(R.id.container, fragment, tag);
        beginTransaction.commit();
        invalidateOptionsMenu();

    }

    @Override
    public void showProgressBar() {
        if (progressLayout != null)
        {
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }
    @Override
    public void showProgressBarWithoutBackground() {
        if (progressLayout != null) {
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }
    @Override
    public void hideProgressBar() {
        if (progressLayout != null) {
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed()
    {
         if (mFragment.getClass().getSimpleName().equals("RideFragment"))
         {
             mFragment.onBackPressed();
         }
        else if (new RideFragment() != null) {
            navigateToFragment(new RideFragment(), new RideFragment().getTag(), false, true);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resource) {
        showToast(getString(resource));
    }

    @Override
    public void showNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.error_internet_conn)
                .setTitle(R.string.netwrok_error)
                //.setIcon(R.mipmap.network_error)
                .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivityForResult(settings, 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        try {
            dialog.show();
        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void setActionTitle(String title) {
        if (mToolbar != null) {
            //((TextView) mToolbar.findViewById(R.id.title)).setText(title);
        }
    }

    @Override
    public void setProfileInfo() {
    }


    @Override
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    protected Action1<Throwable> errorHandler = new Action1<Throwable>() {

        @Override
        public void call(Throwable throwable) {
            try {
            }catch (Throwable t){
                //ignore
            }
            try {
                RetrofitError error = (RetrofitError) throwable;
                hideProgressBar();
                switch (error.getKind()) {
                    case NETWORK:
                        showNetworkDialog();
                        break;

                    default:
                        showToast(error.getMessage());
                }
            } catch (NullPointerException ex) {
                Log.d("ERROR", ex.toString());
            } catch (Exception ex) {
                Log.d("ERROR", ex.toString());
            }
        }
    };


}
