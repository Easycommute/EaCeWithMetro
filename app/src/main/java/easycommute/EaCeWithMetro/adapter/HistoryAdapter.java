package easycommute.EaCeWithMetro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.models.Myhistory.Response;
import easycommute.EaCeWithMetro.models.Myhistory.TxnEntry;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Response> historyResponse;

    public HistoryAdapter(List<Response> historyResponse) {
        this.historyResponse = historyResponse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Response response = historyResponse.get(position);
       if (response.getTxnEntry().getTxnValue()<0)   // Debit
       {
           TxnEntry T =  response.getTxnEntry();
           Integer value = -1 * T.getTxnValue();

           holder.txtDebit.setText("Rs. " + String.valueOf(value));
           holder.txtDebitDesc.setText(T.getTransactionDescription().getDescription());
           holder.txtDebitTime.setText(response.getTimeEntry());
           holder.CreditView.setVisibility(View.INVISIBLE);
       }
       else   // credit
       {
           TxnEntry T =  response.getTxnEntry();
           Integer value = T.getTxnValue();

           holder.txtCredit.setText("Rs. " + String.valueOf(value));
           holder.txtCreditDesc.setText(T.getTransactionDescription().getDescription());
           holder.txtCreditTime.setText(response.getTimeEntry());
           holder.DebitView.setVisibility(View.INVISIBLE);

       }

    }

    @Override
    public int getItemCount() {
        return historyResponse.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtCredit, txtCreditTime, txtCreditDesc;
        protected TextView txtDebit, txtDebitTime, txtDebitDesc;
        protected ImageView CreditView, DebitView;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            txtCredit = (TextView) itemView.findViewById(R.id.txtCredit);
            txtDebit = (TextView) itemView.findViewById(R.id.txtDebit);
            txtCreditDesc = (TextView) itemView.findViewById(R.id.txtCreditDesc);
            txtDebitDesc = (TextView) itemView.findViewById(R.id.txtDebitDesc);
            txtCreditTime = (TextView) itemView.findViewById(R.id.txtCreditTime);
            txtDebitTime = (TextView) itemView.findViewById(R.id.txtDebitTime);

            CreditView = (ImageView) itemView.findViewById(R.id.CreditImg);
            DebitView = (ImageView) itemView.findViewById(R.id.DebitImg);
        }
    }
}
