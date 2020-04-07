package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

public class CityReq {

    @SerializedName("ride_type_id")
    public int rideTypeId = 1;  // 1 refers to lastmile product

    public CityReq(int rideTypeId){
        this.rideTypeId = rideTypeId;
    }
}
