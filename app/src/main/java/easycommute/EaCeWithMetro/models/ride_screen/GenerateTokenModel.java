package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.SerializedName;

public class GenerateTokenModel
{

    @SerializedName("commuter_id")
    public String commuter_id;

    @SerializedName("travel_plan_id")
    public String travel_plan_id;

    public GenerateTokenModel(String travel_plan_id,String commuter_id)
    {
        this.travel_plan_id = travel_plan_id;
        this.commuter_id = commuter_id;
    }
}
