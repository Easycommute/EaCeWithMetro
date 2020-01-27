package easycommute.EaCeWithMetro.activities;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.regex.Pattern;


import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.BaseActivity;
import easycommute.EaCeWithMetro.utils.EasySingleton;
import easycommute.EaCeWithMetro.utils.HttpService;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class QuickRegistrationActivity extends BaseActivity implements View.OnClickListener {
    private EditText inputEmail, inputMobile, inputName, inputOtp;
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private ProgressBar progressBar;

    private RelativeLayout layoutEditMobile;
    private PreferenceManager prefManager;

    private ImageView btnEditMobile;
    private TextView txtEditMobile;
    private RadioGroup radioGroup;

    RelativeLayout back_dim_layout;
    Receiver receiver;
    private PopupWindow successDialog;
    private static final int REQUEST_CODE_EMAIL = 1;
    private String referralCode = "";

    CheckBox checkBox;
    private static Commuter tempCommuter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quick_registration);
        initLayout();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            inputEmail.setText(accountName);
           // Log.d("Debug", accountName);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstants.CUSTOM_INTENT);
        filter.addAction(AppConstants.WAIT_SMS);
        receiver = new Receiver();
        registerReceiver(receiver, filter);
    }

    // widget initialization
    @Override
    protected void initLayout()
    {
        super.initLayout();

        viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        inputEmail  = (EditText) findViewById(R.id.inputEmail);
        inputMobile = (EditText) findViewById(R.id.inputMobile);
        inputName = (EditText) findViewById(R.id.inputName);
        inputOtp  = (EditText) findViewById(R.id.inputOtp);

        btnEditMobile = (ImageView) findViewById(R.id.btn_edit_mobile);
        txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
        btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
        btnVerifyOtp  = (Button) findViewById(R.id.btn_verify_otp);
        back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);

        layoutEditMobile = (RelativeLayout) findViewById(R.id.layout_edit_mobile);
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        checkBox = (CheckBox) findViewById(R.id.terms);


        TextView termsText = (TextView) findViewById(R.id.terms_text);
        termsText.setText(Html.fromHtml(getString(R.string.termsNconditions)));
        termsText.setMovementMethod(LinkMovementMethod.getInstance());


        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()) {
                    btnRequestSms.setBackgroundColor(getResources().getColor(R.color.material_grey_300));
                    btnRequestSms.setEnabled(false);
                } else {
                    btnRequestSms.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    btnRequestSms.setEnabled(true);
                }
            }
        });


        // view click listeners
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);
        findViewById(R.id.reg_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                inputOtp.setHint(R.string.regenerating_otp);
                Commuter commuter = EasySingleton.getInstance().getCommuter();
                EasyCommuteApi.getService().regenerateOTP(commuter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ApiResponse>() {

                            @Override
                            public void call(ApiResponse apiResponse) {
                                hideProgressBar();
                                inputOtp.setHint(R.string.wait_sms);
                            }
                        }, new Action1<Throwable>() {

                            @Override
                            public void call(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });
            }
        });

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);
        prefManager = new PreferenceManager(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (prefManager.isLoggedIn()) {

            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        }

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);

        //Disabling viewparger horizontal swipe
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
                return true;
            }
        });

        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (prefManager.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            layoutEditMobile.setVisibility(View.VISIBLE);
        }

        /*checking whether if user entered the data previously, if data is there pre filling the form*/
        if(tempCommuter != null) {
            inputEmail.setText(tempCommuter.email);
            inputName.setText(tempCommuter.name);
            inputMobile.setText(tempCommuter.phone);
            txtEditMobile.setText("+91 " + tempCommuter.phone);
            inputOtp.setText(tempCommuter.otp);
            if(tempCommuter.gender.equals("M"))
                radioGroup.check(R.id.male);
            else
                radioGroup.check(R.id.female);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btn_request_sms:
                try {
                    validateForm();
                } catch (Exception e) {
                    showToast(e.getMessage());
                }
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

            case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                prefManager.setWaitingForSms(false);
                break;
        }
    }

    /**
     * Validating user details form
     */
    private void validateForm()   // validate form fields
    {
        String mobile = inputMobile.getText().toString().trim();
        String email  = inputEmail.getText().toString().trim();
        String name   = inputName.getText().toString().trim();

        verifyInput(name);
        verifyEmail(email);

        // validating mobile number
        // it should be of 10 digits length
        if (!isValidPhoneNumber(mobile)) {
            if(mobile.equals(""))
                showToast("Please enter your mobile number");
            else
                 showToast("Please enter a valid mobile number");
            return;
        }

        prefManager.setMobileNumber(mobile);
        Commuter commuter = new Commuter(name, email, mobile, getGender(),
                prefManager.getRegistrationId());
        //TODO  If referral code not works out we con remove comment for requestForSms(commuter) and comment openReferralDialog()
        commuter.deviceId = getDeviceID();
        this.tempCommuter = commuter;
     //   requestForSMS(commuter);
        showReferralCodeDialog(commuter);

    }

    // get unique deviceID
    public String getDeviceID()
    {
        String deviceId =  Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    private String getGender()
    {
        return radioGroup.getCheckedRadioButtonId() == R.id.male ? "M" : "F";
    }

    // email validation
    private void verifyEmail(String email)
    {
        if (email.isEmpty()) {
            throw new RuntimeException(getString(R.string.warning_email_empty));
        } else if (!Pattern.compile(AppConstants.EMAIL_PATTERN).matcher(email).matches()) {
            throw new RuntimeException(getString(R.string.warning_email_not_proper));
        }
    }

    private void verifyInput(String input) {
        if (input.isEmpty()) {
            throw new RuntimeException(getString(R.string.warning_name_empty));
        } else if (Pattern.compile("[^A-Za-z0-9 ]").matcher(input).find()) {
            throw new RuntimeException(getString(R.string.warning_name_spl_char));
        } else if (Character.isDigit(input.charAt(0))) {
            throw new RuntimeException(getString(R.string.warning_name_digit));
        }
    }

    /**
     * Method initiates the SMS request on the server
     *
     * @param commuter details of commuter
     */
    private void requestForSMS(Commuter commuter)
    {
        commuter.regId=prefManager.getRegistrationId();
        showProgressBar();
        EasyCommuteApi.getService().registerCommuter(commuter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ApiResponse>() {
                    @Override
                    public void call(ApiResponse response) {
                        hideProgressBar();
                        validateResponse(response);
                    }
                }, errorHandler);
    }

    // handle OTP response
    private void validateResponse(ApiResponse apiResponse)
    {
        ApiResponse.ResponseStatus status = apiResponse.responseStatus;
        switch (status) {
            case USER_CREATED_OTP_GENERATED:
            case USER_EXIST_OTP_GENERATED:
            case USER_ALREADY_EXIST:
                EasySingleton.getInstance().setCommuter(apiResponse.commuter);
                viewPager.setCurrentItem(1);

                layoutEditMobile.setVisibility(View.VISIBLE);
                txtEditMobile.setText("+91 " + prefManager.getMobileNumber());
                prefManager.setWaitingForSms(true);
                break;

            case USER_VERIFICATION_FAILED:
                showToast(status.toString());

            default:
              //  showToast(status.toString());
        }
    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp()
    {
        String otp = inputOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent smsService = new Intent(getApplicationContext(), HttpService.class);
            smsService.putExtra("otp", otp);
            startService(smsService);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }

            return findViewById(resId);
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public class Receiver extends android.content.BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(AppConstants.CUSTOM_INTENT)) {
                //showSortPopup(QuickRegistrationActivity.this);
                startHome();


            } else if(intent.getAction().equals(AppConstants.WAIT_SMS)) {
                inputOtp.setHint(R.string.enter_otp);
            }
        }
    }

    //TODO: Need to refactor this piece of code
/*
    private void showSortPopup(final Activity context) {
        View layout = LayoutInflater.from(this).inflate(R.layout.popup_otp, null);
        successDialog = new PopupWindow(context);

        successDialog.setContentView(layout);
        successDialog.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        successDialog.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        successDialog.setFocusable(true);
        successDialog.setBackgroundDrawable(new BitmapDrawable());
        successDialog.showAtLocation(layout, Gravity.CENTER, 0, 0);
        back_dim_layout.setVisibility(View.VISIBLE);
        successDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                back_dim_layout.setVisibility(View.GONE);
            }
        });
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(successDialog != null)
            successDialog.dismiss();
    }

    public void startHome() {
        final Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this) {
                        // TODO: If you suppose to perform this operation on Main thread,
                        // it will kick out..
                        wait(3000);
                        launchHomeActivity();
                    }
                }
                catch(InterruptedException ex){
                }
            }
        };

        thread.start();
    }

    private void launchHomeActivity()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    // display dialog box with referral field
    private void showReferralCodeDialog(final Commuter commuter)
    {
        View layout = LayoutInflater.from(this).inflate(R.layout.referral_popup, null);
        final Dialog referralDialog = new Dialog(this, R.style.Theme_Dialog);
        referralDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        referralDialog.setContentView(layout);
        /*referralDialog.setTitle("Have Referral Code? ");*/
        final EditText referral = (EditText) layout.findViewById(R.id.referral_code);
        referral.setText(referralCode);
        Button yes = (Button) layout.findViewById(R.id.yes);
        Button no = (Button) layout.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!referral.getText().toString().equals("")) {
                    commuter.referralCode = referral.getText().toString().toUpperCase();
                    referralCode = commuter.referralCode;
                    requestForSMS(commuter);
                    referralDialog.dismiss();
                } else {
                    showToast(R.string.enter_referral);
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForSMS(commuter);
                referralDialog.dismiss();
            }
        });
    referralDialog.show();
    }


}
