package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 10/13/2015.
 */
public class Commuter {
    @SerializedName("id")
    public int commuterId;

    @SerializedName("name")
    public String name;

    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("gender")
    public String gender;

    @SerializedName("email")
    public String email;

    @SerializedName("mobile")
    public String phone;

    //@SerializedName("gcm_reg_id")
    @SerializedName("fcm_reg_id")
    public String regId;

    @SerializedName("otp")
    public String otp;

    @SerializedName("sms_send_enabled")
    public boolean isSmsTriggered = true;

    @SerializedName("referral_code")
    public String referralCode;

    @SerializedName("city_id")
    public int cityId;

    public Commuter() {
    }

    public Commuter(String name, String email, String phone, String gender, String regId) {
        this.email  = email;
        this.phone  = phone;
        this.name   = name;
        this.gender = gender;
        this.regId  = regId;
    }

    public Commuter(int commuterId, String regId, int cityId )
    {
        this.commuterId=commuterId;
        this.regId=regId;
        this.cityId=cityId;
    }
}
