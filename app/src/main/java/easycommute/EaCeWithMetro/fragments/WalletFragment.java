package easycommute.EaCeWithMetro.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.activities.PaymentsActivity;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.models.PaymentApiResponse;
import easycommute.EaCeWithMetro.models.RazorpayDTO;
import easycommute.EaCeWithMetro.models.RideReq;
import easycommute.EaCeWithMetro.models.wallet_model.RechargePack;
import easycommute.EaCeWithMetro.models.wallet_model.WalletModel;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PaymentConstants;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends BaseFragment {

    Button btnAddMoney;
    long priceList[];
    long selectedPrice;
    int numOfButtonsPerRow;
    PreferenceManager preferenceManager = null;
    List<RechargePack> rechargePacks;
    HashMap<Integer,Integer> rechargePackID;
    HashMap<String,String> orderIDs;
    String selectedOrderID,selectedPriceID;
    TextView txtCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getWalletDetails();
    }


    private void getWalletDetails()
    {
        showProgressBar();
        preferenceManager = new PreferenceManager(getActivity());
        Commuter commuter=preferenceManager.getCommuter();
        RideReq rideReq= new RideReq("1",String.valueOf(commuter.commuterId),"1");
        EasyCommuteApi.getService().getWalletDetails(rideReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WalletModel>() {
                    @Override
                    public void call(final WalletModel walletModel)
                    {
                        rechargePacks = new ArrayList<RechargePack>();
                        rechargePackID = new HashMap<Integer,Integer>();
                        rechargePacks = walletModel.getResponse().getRechargePacks();
                        priceList = new long[rechargePacks.size()];

                        numOfButtonsPerRow = Integer.parseInt(walletModel.getResponse().getNumButtonsPerRow());
                        for(int i = 0; i < rechargePacks.size(); i++)
                        {
                            priceList[i] = rechargePacks.get(i).recharge_amount;
                            rechargePackID.put(rechargePacks.get(i).recharge_amount,rechargePacks.get(i).id);
                        }
                        orderIDs = walletModel.getResponse().getRpOrderMap();
                        init();
                        txtCredit.setText(walletModel.getResponse().getAccountBalance());
                        hideProgressBar();
                    }
                }, errorHandler);


    }

    private void unSelectAllPriceButtons() {

        int numrows = (priceList.length / numOfButtonsPerRow) + 1;
        for (int i = 0; i < numrows; i++) {
            String lLayout = "priceLayout" + (i + 1);
            int resID = getResources().getIdentifier(lLayout, "id", getContext().getPackageName());
            LinearLayout row = (LinearLayout) getView().findViewById(resID);
            final int childCount = row.getChildCount();
            for (int j = 0; j < childCount; j++) {
                Button b1 = row.getChildAt(j).findViewById(R.id.price_btn);
                b1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }

        }
    }

    // widget initialization
    private void init()
    {
        btnAddMoney=(Button)getView().findViewById(R.id.btnAddMoney);
        txtCredit=(TextView) getView().findViewById(R.id.txtCredit);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentScreen();

            }
        });

        int counter = 0, rowCouter = 0;
        String layoutId;
        while(counter < priceList.length )
        {
            if((counter % numOfButtonsPerRow) == 0 ){
                rowCouter++;
            }
            layoutId = "priceLayout" + rowCouter;
            int resID = getResources().getIdentifier(layoutId, "id", getContext().getPackageName());
            LinearLayout item = (LinearLayout)getView().findViewById(resID);
            View child = getLayoutInflater().inflate(R.layout.layout_recharge_options,null);
            item.addView(child);
            final Button b = (Button)child.findViewById(R.id.price_btn);
            b.setText( String.valueOf(priceList[counter]) );
            final long price = priceList[counter];

            b.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            //Clear existing selected buttons
                            unSelectAllPriceButtons();
                            selectedPrice = price * 100;
                            int priceID =rechargePackID.get(Integer.parseInt(String.valueOf(price)));
                            selectedOrderID = orderIDs.get(String.valueOf(priceID));
                            selectedPriceID=String.valueOf(priceID);
                            b.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                            btnAddMoney.setBackgroundResource(R.drawable.rounded_button);

                        }
                    }
            );
            counter++;
        }

        for(int i = 0; i < rowCouter; i++) {
            layoutId = "priceLayout" + (i + 1);
            int resID = getResources().getIdentifier(layoutId, "id", getContext().getPackageName());
            LinearLayout item = (LinearLayout)getView().findViewById(resID);
            item.setWeightSum(item.getChildCount());
        }
    }


    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.wallet;
    }

    // open razorpay screen
    private void openPaymentScreen()
    {
        Intent intent = new Intent(getActivity(), PaymentsActivity.class);
        intent.putExtra(PaymentConstants.AMOUNT, String.valueOf(selectedPrice));
        intent.putExtra(PaymentConstants.RECHARGE_ID, selectedPriceID);
        intent.putExtra(PaymentConstants.ORDER_ID, selectedOrderID);
        startActivityForResult(intent, 1234);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String orderID="",signature="",paymentID="";
        if (data != null)
        {
            orderID = data.getStringExtra("orderID");
            signature = data.getStringExtra("signature");
            paymentID = data.getStringExtra("paymentID");
        }
        if (resultCode == Activity.RESULT_OK)
        {
            handlePaymentResponse(orderID,signature,paymentID);
        }
        else if (resultCode == Activity.RESULT_CANCELED)
        {
            showToast("Operation cancelled intentionally..");
        }
    }

    private void handlePaymentResponse(String orderID,String signature,String paymentID) {
        updatePaymentTransaction(orderID,signature,paymentID);
    }

    private void updatePaymentTransaction(String orderID,String signature,String paymentID) {
        RazorpayDTO razorpayDTO = new RazorpayDTO();
        razorpayDTO.amount = (int) selectedPrice / 100;
        razorpayDTO.commuterId = getCommuterId();
        razorpayDTO.order_id = orderID;
        razorpayDTO.signature = signature;
        razorpayDTO.payment_id = paymentID;

        if (orderID != null || signature != null || paymentID != null) {
            showProgressBar();
            EasyCommuteApi.getService().updatePaymentTransR(razorpayDTO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<PaymentApiResponse>() {
                        @Override
                        public void call(PaymentApiResponse paymentApiResponse) {
                            hideProgressBar();
                            handlePaymentResponse(paymentApiResponse);
                        }
                    }, errorHandler);
        }
    }


    private void handlePaymentResponse(PaymentApiResponse apiResponse) {
        switch (apiResponse.status) {
            case PAYMENT_TRANSACTION_UPDATION_SUCCESS:
                showToast("Payment Successful!");
                refreshFragment();
                break;
            default:
                showToast(apiResponse.status.toString());
        }
    }

    private void refreshFragment()
    {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }
}