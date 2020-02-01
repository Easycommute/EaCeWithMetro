package easycommute.EaCeWithMetro.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.activities.PaymentsActivity;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PaymentConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallet extends BaseFragment {

    Button btnAddMoney;

    //TODO:- Hardcoded values needs to be brought in via API call
    long priceList[] = { 15, 20, 30, 50, 60, 75, 100, 150};
    long selectedPrice;
    int numButtonsPerRow = 4;

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
        init();
    }

    private void unselectAllPriceButtons() {

        int numrows = (priceList.length / numButtonsPerRow ) + 1;
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
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentScreen();

            }
        });

        int counter = 0, rowCouter = 0;
        String layoutId;
        while(counter < priceList.length ) {
            if((counter % numButtonsPerRow) == 0 ){
                rowCouter++;
            }

            layoutId = "priceLayout" + rowCouter;
            int resID = getResources().getIdentifier(layoutId, "id", getContext().getPackageName());
            LinearLayout item = (LinearLayout)getView().findViewById(resID);

            View child = getLayoutInflater().inflate(R.layout.layout_recharge_options,null);
            item.addView(child);

            final Button b = (Button)  child.findViewById(R.id.price_btn);
            b.setText( String.valueOf(priceList[counter]) );

            final long price = priceList[counter];
            b.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Clear existing selected buttons
                            unselectAllPriceButtons();
                            selectedPrice = price * 100;
                            b.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
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
        intent.putExtra(PaymentConstants.RECHARGE_ID, "112");
        intent.putExtra(PaymentConstants.ORDER_ID, "an0ha92h2bah18282");
        startActivityForResult(intent, 1234);
    }
}