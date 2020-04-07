package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.SerializedName;

public class TokenResponse
{
    @SerializedName("avaiable_balance")
    private Integer avaiable_balance;

    @SerializedName("token_display_msg")
    private String token_display_msg;

    public Integer getAvaiable_balance() {
        return avaiable_balance;
    }

    public void setAvaiable_balance(Integer avaiable_balance) {
        this.avaiable_balance = avaiable_balance;
    }

    public String getToken_display_msg() {
        return token_display_msg;
    }

    public void setToken_display_msg(String token_display_msg) {
        this.token_display_msg = token_display_msg;
    }
}