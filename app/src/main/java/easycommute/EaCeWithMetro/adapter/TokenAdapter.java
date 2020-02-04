package easycommute.EaCeWithMetro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.models.MyTokens.TokenResponse;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.ViewHolder> {

    private TokenResponse tokenResponse;

    public TokenAdapter(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String token = tokenResponse.getTokenHistory().get(position).display_tkt_msg;
        holder.tokenText.setText(token);

    }

    @Override
    public int getItemCount() {
        return tokenResponse.getTokenHistory().size();
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
        protected TextView tokenText;
        protected ImageView CreditView, DebitView;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            tokenText = (TextView) itemView.findViewById(R.id.token_text);
        }
    }
}
