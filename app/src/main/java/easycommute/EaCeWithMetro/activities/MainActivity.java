package easycommute.EaCeWithMetro.activities;

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

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.fragments.MyHistoryFragment;
import easycommute.EaCeWithMetro.fragments.ProfileFragment;
import easycommute.EaCeWithMetro.fragments.RideFragment;
import easycommute.EaCeWithMetro.fragments.WalletFragment;
import easycommute.EaCeWithMetro.models.BookingInfo;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    private BookingInfo booking;
    Fragment fragment=null;
    ImageView imgCall;
    TextView txtVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        imgCall = findViewById(R.id.imgCall);
        txtVersion = findViewById(R.id.txtVersion);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new WalletFragment();   // initializing fragment with wallettFragment
        navigateToFragment(fragment, fragment.getTag(), false, true);   // call fragment

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;// get package info
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

    @Override
    public void onBackPressed()   // handle back press
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)// call fragment on click of Side menu items
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
}
