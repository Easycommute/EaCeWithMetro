package easycommute.EaCeWithMetro.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import easycommute.EaCeWithMetro.BuildConfig;
import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.fragments.MyHistoryFragment;
import easycommute.EaCeWithMetro.fragments.ProfileFragment;
import easycommute.EaCeWithMetro.fragments.RideFragment;
import easycommute.EaCeWithMetro.fragments.WalletFragment;
import easycommute.EaCeWithMetro.models.BookingInfo;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.BaseActivity;
import easycommute.EaCeWithMetro.utils.EasySingleton;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import easycommute.EaCeWithMetro.utils.Utils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    private BookingInfo booking;
    Fragment fragment=null;
    ImageView imgCall;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView txtVersion,nav_header_name,nav_mob_no,nav_header_email;
    private PreferenceManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        showNavHeaderData();
        // initializing fragment with walletFragment
        fragment = new WalletFragment();
        // call fragment
        navigateToFragment(fragment, fragment.getTag(), false, true);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            // get package info
            String version = pInfo.versionName;
            txtVersion.setText("v"+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "8374590066";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

    }

    private void showNavHeaderData()
    {
        Commuter commuter = EasySingleton.getInstance().getCommuter();
        nav_header_email.setText(commuter.email);
        nav_header_name.setText(commuter.name);
        nav_mob_no.setText(commuter.phone);
    }

    // widget initialization
    private void init()
    {
        prefManager = new PreferenceManager(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        imgCall = findViewById(R.id.imgCall);
        txtVersion = findViewById(R.id.txtVersion);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nav_header_email = (TextView) headerView.findViewById(R.id.nav_header_email);
        nav_header_name = (TextView) headerView.findViewById(R.id.nav_header_name);
        nav_mob_no = (TextView) headerView.findViewById(R.id.nav_mob_no);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }



    // handle back press
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // call fragment on click of Side menu items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.nav_profile:
                fragment= new ProfileFragment();
                break;

            case R.id.nav_wallet:
                fragment = new WalletFragment();
                break;

            case R.id.nav_ride:
                fragment=new RideFragment();
                break;

            case R.id.nav_rate_us:
                rateApp();
                break;

                case R.id.nav_share:
                shareApp();
                break;

            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setMessage(getResources().getString(R.string.want_to_logout))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                                prefManager.clearSession();
                                QuickRegistrationActivity.tempCommuter=null;
                                startActivity(new Intent(MainActivity.this,QuickRegistrationActivity.class));
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                break;

                case R.id.nav_history:
                    booking=new BookingInfo();
                    booking.commuterId = 173306;
                    booking.tripId = 228263;
                    booking.fromStopId = 42;
                    booking.toStopId =183;
                    booking.fromStop = "Gachibowli-TCS Synergy Park";
                    booking.toStop ="Sagar X Road";
                    booking.noOfSeat = 01;

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstants.BOOKING, booking);
                    bundle.putString("from", "Gachibowli-TCS Synergy Park");
                    bundle.putString("to", "Sagar X Road");
                    bundle.putInt(AppConstants.BOOKING_ID, 0);
                    bundle.putString(AppConstants.ACTION, null);
                    fragment = new MyHistoryFragment();
                    fragment.setArguments(bundle);
                   break;
        }
                if (fragment != null) {
            navigateToFragment(fragment, fragment.getTag(), false, true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void rateApp()
    {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getResources().getString(R.string.app_store_url) + this.getPackageName())));
        }
    }

    private void shareApp()
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.EasyCommuteMetro));
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + getResources().getString(R.string.app_store_url) + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.choose_one)));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
