package easycommute.EaCeWithMetro.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.activities.PaymentsActivity;
import easycommute.EaCeWithMetro.utils.PaymentConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btnAddMoney;
    int selectedPrice;
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
    private void init()
    {
        btn1=(Button)getView().findViewById(R.id.btn1);
        btn2=(Button)getView().findViewById(R.id.btn2);
        btn3=(Button)getView().findViewById(R.id.btn3);
        btn4=(Button)getView().findViewById(R.id.btn4);
        btn5=(Button)getView().findViewById(R.id.btn5);
        btn6=(Button)getView().findViewById(R.id.btn6);
        btn7=(Button)getView().findViewById(R.id.btn7);
        btn8=(Button)getView().findViewById(R.id.btn8);
        btnAddMoney=(Button)getView().findViewById(R.id.btnAddMoney);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  selectedPrice =15*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =20*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =30*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }
        });
         btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =50*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =60*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =75*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =100*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPrice =150*100;
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn5.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn6.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn7.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
                btn8.setBackground(getResources().getDrawable(R.drawable.rounded_button_selected));
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentScreen();

            }
        });

    }



    private void openPaymentScreen()
    {
        Intent intent = new Intent(getActivity(), PaymentsActivity.class);
        intent.putExtra(PaymentConstants.AMOUNT, String.valueOf(selectedPrice));
        intent.putExtra(PaymentConstants.RECHARGE_ID, "112");
        intent.putExtra(PaymentConstants.ORDER_ID, "an0ha92h2bah18282");
        startActivityForResult(intent, 1234);
    }

}
