package easycommute.EaCeWithMetro.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class StopListInfo {
    @SerializedName("stops")
    public List<Stop> stops;

    @SerializedName("fare")
    public Fare fare;

    @SerializedName("account")
    public AccountBalance account;

    @SerializedName("offer_enabled")
    public boolean isOfferEnabled;

    @SerializedName("offer_message")
    public String offerMessage;

    // DONT USE THIS VARIABLE FROM NOW ON
    //@SerializedName("monthly_pass_enabled")
    //public Boolean monthlyPassEnabled;

    @SerializedName("monthly_pass_enabled_v2")
    public Boolean monthlyPassEnabledV2;

    @SerializedName("monthly_pass_enabled_v2_trip")
    public Boolean monthlyPassEnabledV2Trip = true;
}
