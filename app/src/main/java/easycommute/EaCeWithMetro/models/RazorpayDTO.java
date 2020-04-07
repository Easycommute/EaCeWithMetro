package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

public class RazorpayDTO
{

    @SerializedName("commuter_id")
    public int commuterId;

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("payment_id")
    public String payment_id;

    @SerializedName("signature")
    public String signature;

    @SerializedName("amount")
    public int amount;
}
