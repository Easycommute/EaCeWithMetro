package easycommute.EaCeWithMetro.models.wallet_model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class WalletModel
{

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private WalletResponse response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public WalletResponse getResponse() {
        return response;
    }

    public void setResponse(WalletResponse response) {
        this.response = response;
    }
}
