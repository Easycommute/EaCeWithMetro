package easycommute.EaCeWithMetro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.models.Myhistory.Response;

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
             holder.txtCredit.setText(response.getTxnEntry().getTransactionDescription().getDescription()+" "+String.valueOf(response.getTxnEntry().getTxnValue())+" | "+ " "+response.getTimeEntry());
       }
       else   // credit
       {
           holder.txtDebit.setText(response.getTimeEntry()  +" | "+ String.valueOf(response.getTxnEntry().getTxnValue())+ " "+response.getTxnEntry().getTransactionDescription().getDescription());
       }
       holder.itemView.findViewById(R.id.offset).setVisibility(position != 0 ? View.VISIBLE : View.INVISIBLE);
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
        protected TextView txtCredit;
        protected TextView txtDebit;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            txtCredit = (TextView) itemView.findViewById(R.id.txtCredit);
            txtDebit = (TextView) itemView.findViewById(R.id.txtDebit);
        }
    }
}
