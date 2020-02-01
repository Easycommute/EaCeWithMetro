package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.SerializedName;

import easycommute.EaCeWithMetro.models.wallet_model.WalletResponse;

public class GenerateTokenResponse
{
    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private TokenResponse response;

    @SerializedName("returnCode")
    private String returnCode;

    public TokenResponse getResponse() {
        return response;
    }

    public void setResponse(TokenResponse response) {
        this.response = response;
    }
}
