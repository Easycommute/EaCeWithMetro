package easycommute.EaCeWithMetro.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.activities.PaymentsActivity;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PaymentConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends BaseFragment {

    Button btnAddMoney;
    List<Button> btnGroup = new ArrayList<Button>();

    //Price has to be multiplied  by 100 for razorpay to work
    //TODO:- Hardcoded values needs to be brought in via API call
    long priceList[] = { 15*100, 20*100, 30*100, 50*100, 60*100, 75*100,  100*100, 150*100 };

    long selectedPrice;
    int selectedBtnIndex = -1;

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

    private void unselectButtonAtIndex(int buttonIndex) {
        btnGroup.get(buttonIndex).setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
    }

    // widget initialization
    private void init()
    {
        for (int i = 1; i <= 8; i++) {
            final int j = i - 1;
            String buttonId = "btn" + i;
            int resID = getResources().getIdentifier(buttonId, "id", getContext().getPackageName());

            final Button localButton = (Button)getView().findViewById(resID);
            localButton.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            btnGroup.add(localButton);

            final long price =  priceList[i-1];
            localButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedBtnIndex != -1) {
                        unselectButtonAtIndex(selectedBtnIndex);
                    }

                    selectedPrice = price;
                    localButton.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                    selectedBtnIndex = j;
                }});
        }

        btnAddMoney=(Button)getView().findViewById(R.id.btnAddMoney);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentScreen();

            }
        });
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
