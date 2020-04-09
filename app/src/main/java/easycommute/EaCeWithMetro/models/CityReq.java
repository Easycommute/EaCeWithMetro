package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

public class CityReq {
    @SerializedName("ride_type_id")
    private int rideTypeId;

    public CityReq() {
        this.rideTypeId = 1;
    }
    public CityReq(int rideTypeId ){
        this.rideTypeId = rideTypeId;
    }
}
