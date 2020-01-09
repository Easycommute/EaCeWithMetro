package easycommute.EaCeWithMetro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import easycommute.EaCeWithMetro.R;
import easycommute.EaCeWithMetro.api.data.Stop;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.ViewHolder> {
    private static final int STARTING_PNT = 1;
    private static final int BOARDING_PNT = 2;
    private static final int DROPPING_PNT = 3;
    private static final int INTERMID_PNT = 4;

    private List<Stop> stops;

    public StopListAdapter(List<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getStopView(viewType),
                parent, false);

        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop stop = getStop(position);
        if (holder.description != null) {
            holder.description.setText(stop.helpTxt);
        }

        holder.stopName.setText(stop.name);
        holder.time.setText(stop.stopTime);

        holder.itemView.findViewById(R.id.offset).setVisibility(position != 0
                ? View.VISIBLE : View.INVISIBLE);
    }
    // Getting stop based on Stop Number
    private Stop getStop(int position) {
        int itemCount = getItemCount();
        for(int i = 0; i < itemCount; i++) {
            if(stops.get(i).stopNo == position + 1)
                return stops.get(i);
        }
        return stops.get(position);
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    @Override
    public int getItemViewType(int position) {
        Stop stop = getStop(position);

        if (stop.isBoardingPnt) {
            return BOARDING_PNT;
        } else if (stop.isStartingPnt) {
            return STARTING_PNT;
        } else if (stop.isDroppingPnt) {
            return DROPPING_PNT;
        } else {
            return INTERMID_PNT;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;

        protected TextView stopName;
        protected TextView time;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            stopName = (TextView) itemView.findViewById(R.id.stopName);
            time = (TextView) itemView.findViewById(R.id.time);

            if (viewType == BOARDING_PNT || viewType == INTERMID_PNT) {
                description = (TextView) itemView.findViewById(R.id.description);
            }
        }
    }

    private int getStopView(int viewType) {
        switch (viewType) {
            case STARTING_PNT:
                return R.layout.item_starting_point;
            case BOARDING_PNT:
                return R.layout.item_boarding_point;
            case DROPPING_PNT:
                return R.layout.item_dropping_point;
            case INTERMID_PNT:
                return R.layout.item_intermid_point;

            default:
                throw new IllegalStateException("Unknown view");
        }
    }

}
