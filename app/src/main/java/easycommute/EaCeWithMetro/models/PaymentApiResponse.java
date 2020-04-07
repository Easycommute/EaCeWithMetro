package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

import easycommute.EaCeWithMetro.api.data.response.ApiResponse;

/**
 * Created by Ram Prasad on 11/13/2015.
 */
public class PaymentApiResponse {

    @SerializedName("response")
    public PaymentInfo paymentInfo;

    @SerializedName("returnCode")
    public ApiResponse.ResponseStatus status;

    @SerializedName("message")
    public String message;

}
