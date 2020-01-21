package easycommute.EaCeWithMetro.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import easycommute.EaCeWithMetro.utils.PaymentConstants;


@SuppressLint("SetJavaScriptEnabled")
public class PaymentsActivity extends Activity implements PaymentResultWithDataListener
{

    String TAG="Payment Error";
    private String amount;
    private String orderID;
    private String rechargeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        Checkout.preload(getApplicationContext());

        openPaymentScreen();
    }


    private void openPaymentScreen()    // open razorpay screen
    {
        amount = getIntent().getStringExtra(PaymentConstants.AMOUNT);
        rechargeId = getIntent().getStringExtra(PaymentConstants.RECHARGE_ID);
        //orderID = getIntent().getStringExtra(PaymentConstants.ORDER_ID);
        Checkout checkout = new Checkout();
        //checkout.setImage(R.mipmap.rsz_32logo);
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Easy Commute");
            options.put("description", "Affordable Bus Shuttle Services");
            options.put("currency", "INR");
           // options.put("order_id", orderID);
            options.put("amount", amount);
            options.put("prefill.email", "johndoe@noname.com");
            options.put("prefill.contact", "9876543210");
            checkout.open(PaymentsActivity.this, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }




    @Override
    public void onBackPressed() {   // handle back press
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData)  // handle payment success
    {

        Intent intent = new Intent();
        intent.putExtra("orderID",  paymentData.getOrderId());
        intent.putExtra("signature",  paymentData.getSignature());
        intent.putExtra("paymentID",  paymentData.getPaymentId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) // handle payment failure
    {
         Toast.makeText(PaymentsActivity.this, s, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
         setResult(RESULT_OK, intent);
         finish();

    }
}

