package easycommute.EaCeWithMetro.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.events.BusProvider;
import easycommute.EaCeWithMetro.ui.AnimateTextView;
import easycommute.EaCeWithMetro.utils.BaseActivity;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import easycommute.EaCeWithMetro.utils.Utils;

public class SplashActivity extends BaseActivity {
    private PreferenceManager prefManger;
    String regId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        prefManger = new PreferenceManager(SplashActivity.this);
        AnimateTextView animateTextView = (AnimateTextView) findViewById(R.id.animate_text_view);
        animateTextView.setCharacterDelay(50);
        animateTextView.animateText("Sit. . . Relax. . . Commute. . .");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task)
                    {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        regId = task.getResult().getToken();
                        prefManger.setRegistrationId(regId);
                        Log.v("FCM_Token:",regId);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);


        if (Utils.isConnectedToInternet(this)) {
            new SplashTask().execute();
        } else {
            showNetworkDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }



    private class SplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            registerWithServer();
        }
    }

    private void registerWithServer() {
        PackageManager packageManager = this.getPackageManager();
        try {
            packageManager.getPackageInfo("com.google.android.gsf", 0);
        } catch (PackageManager.NameNotFoundException var4) {
        }


        try{
        } catch (Exception e){
            e.printStackTrace();
        }
        if (!prefManger.isUserRegistered())
        {
            resolveNextActivity();
        }
        else {
            resolveNextActivity();
        }
    }


    private void resolveNextActivity() {

        if (prefManger.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, QuickRegistrationActivity.class));
        }
    }



}
