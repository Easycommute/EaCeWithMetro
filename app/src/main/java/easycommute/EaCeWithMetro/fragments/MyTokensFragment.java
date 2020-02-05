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
import easycommute.EaCeWithMetro.adapter.TokenAdapter;
import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.models.MyTokens.TokenResponse;
import easycommute.EaCeWithMetro.models.Myhistory.HistoryReq;
import easycommute.EaCeWithMetro.utils.BaseFragment;
import easycommute.EaCeWithMetro.utils.PreferenceManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class MyTokensFragment extends BaseFragment {
    private PreferenceManager prefManager;
    private RecyclerView recyclerView;
   // private AlertDialog confirmDialog;
    private boolean showPromoBox;

    String action;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_tokens, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerTokenView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        fetchBookingDetails();
    }

    // fetch the list
    private void fetchBookingDetails()
    {
        showProgressBar();

        HistoryReq historyReq= new HistoryReq(getCommuterId());
        EasyCommuteApi.getService().getTokenDetails(historyReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TokenResponse>()
                {
                    @Override
                    public void call(TokenResponse tokenResponse) {
                        hideProgressBar();
                        updateContent(tokenResponse);

                    }
                }, errorHandler);
    }


    private void updateContent(TokenResponse tokenResponse) {
        recyclerView.setAdapter(new TokenAdapter(tokenResponse));
    }

    @Override
    public boolean onBackPressed() {
        onBackPressedFragment();
        return true;
    }


    @Override
    protected int getTitle() {
        // POTENTIAL_MULTILINGUAL_STRINGS
        return R.string.tokens;
    }


}
