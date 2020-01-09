package easycommute.EaCeWithMetro.api.data.response;

import com.google.gson.annotations.SerializedName;

import easycommute.EaCeWithMetro.api.data.StopListInfo;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class PreBookingApiResponse {
    @SerializedName("response")
    public StopListInfo bookingDtls;

    @SerializedName("returnCode")
    public ApiResponse.ResponseStatus status;

    @SerializedName("message")
    public String message;

    @SerializedName("show_promo")
    public boolean showPromo;

}
