package easycommute.EaCeWithMetro.utils;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;

import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.models.Commuter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class HttpService extends IntentService {
    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp) {
        Commuter commuter = EasySingleton.getInstance().getCommuter();
        commuter.otp = otp;
        EasyCommuteApi.getService().verifyCommuter(commuter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ApiResponse>() {
                    @Override
                    public void call(ApiResponse response) {
                        try {
                            parseJSONResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(HttpService.this, "Oops! Verification failed. Try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void parseJSONResponse(ApiResponse apiResponse) throws JSONException {
        ApiResponse.ResponseStatus status = apiResponse.responseStatus;

        switch (status) {
            case USER_ALREADY_VERIFIED:
            case USER_VERIFIED:
                EasySingleton.getInstance().setCommuter(apiResponse.commuter);
                new PreferenceManager(this).setLoggedIn(true);
                Intent i = new Intent();

                i.setAction(AppConstants.CUSTOM_INTENT);
                sendBroadcast(i);
                break;

            default:
                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
