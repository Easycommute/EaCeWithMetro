package easycommute.EaCeWithMetro.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.adapter.HistoryAdapter;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.api.data.AccountBalance;
import easycommute.EaCeWithMetro.api.data.Fare;
import easycommute.EaCeWithMetro.models.BookingInfo;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.models.Myhistory.HistoryReq;
import easycommute.EaCeWithMetro.models.Myhistory.HistoryResponse;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class MyHistoryFragment extends BaseFragment {
    private PreferenceManager prefManager;
    private RecyclerView recyclerView;
    private AlertDialog confirmDialog;
    private Button buy_a_pass;
    private BookingInfo booking;
    private Fare fare;
    private TextView seats, credits, balanceTxt;
    private Button ridePay, justMe, plus1, plus2, plus3;
    int chargedFare, actualFare;
    private AccountBalance accountBalance;
    private boolean showPromoBox;
    private String fromPayment,promoCode;
    String from,to;
    Integer bookedBookingId=0;
    String action;
    Commuter commuterFCM;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_history, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        fetchBookingDetails();
    }

    // fetch the list
    private void fetchBookingDetails()
    {
        showProgressBar();

        HistoryReq historyReq= new HistoryReq(getCommuterId());
        EasyCommuteApi.getService().getHistoryDetails(historyReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HistoryResponse>()
                {
                    @Override
                    public void call(HistoryResponse historyResponse) {
                        hideProgressBar();
                        updateContent(historyResponse);

                    }
                }, errorHandler);
    }


    private void updateContent(HistoryResponse historyResponse) {
        recyclerView.setAdapter(new HistoryAdapter(historyResponse.getResponse()));
    }

    @Override
    public boolean onBackPressed() {
        onBackPressedFragment();
        return true;
    }


    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.history;
    }


}
