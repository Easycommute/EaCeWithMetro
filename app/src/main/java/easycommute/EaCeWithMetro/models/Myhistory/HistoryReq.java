package easycommute.EaCeWithMetro.models.Myhistory;

import com.google.gson.annotations.SerializedName;

public class HistoryReq
{
    @SerializedName("commuter_id")
    public int commuterId;

    public HistoryReq(int commuterId)
    {
        this.commuterId = commuterId;
    }
}
