package easycommute.EaCeWithMetro.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class Fare {
    @SerializedName("from_stop")
    public String fromStop;

    @SerializedName("from_stop_id")
    public int fromStopId;

    @SerializedName("to_stop")
    public String toStop;

    @SerializedName("to_stop_id")
    public int toStopId;

    @SerializedName("charged_fare")
    public int chargedFare;

    @SerializedName("actual_fare")
    public int actualFare;

}
