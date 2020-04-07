package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Niranjan on 11/26/2015.
 */
public class AboutUs {

    @SerializedName("returnCode")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("response")
    public Response aboutUsDetails;

    public class Response {
        @SerializedName("about_us_desc")
        public String description;

        @SerializedName("customer_care_email")
        public String email;

        @SerializedName("customer_care_number")
        public String phone;

        @SerializedName("whatsapp_care_number")
        public String whatsapp;
    }

}
