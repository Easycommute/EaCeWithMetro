package easycommute.EaCeWithMetro.models.ride_screen;

import android.media.session.MediaSession;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookRideResponse
{
    @SerializedName("response")
    private TokenResponse token;

    @SerializedName("message")
    private String message;

    @SerializedName("returnCode")
    private String returnCode;

    public TokenResponse getTokenResponse() {
        return token;
    }

    public void setTokenResponse( TokenResponse response) {
        this.token = response;
    }
}
