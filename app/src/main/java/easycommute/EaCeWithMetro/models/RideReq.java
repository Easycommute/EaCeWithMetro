package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

public class RideReq {

    @SerializedName("city_id")
    public String city_id = "1";

    @SerializedName("commuter_id")
    public String commuter_id;

    @SerializedName("ride_type_id")
    public String rideTypeId = "1";  // 1 refers to lastmile product

    public RideReq(String rideTypeId,String commuter_id,String city_id)
    {
        this.rideTypeId = rideTypeId;
        this.commuter_id = commuter_id;
        this.city_id = city_id;
    }
}
