package easycommute.EaCeWithMetro.models.MyTokens;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TokenResponse
{
    @SerializedName("response")
    private List<TokenEntry> tokenHistory;

    @SerializedName("message")
    private String message;

    @SerializedName("returnCode")
    private String returnCode;

    public List<TokenEntry> getTokenHistory() {
        return tokenHistory;
    }

    public void setTokenHistory( List<TokenEntry> response) {
        this.tokenHistory = response;
    }
}
