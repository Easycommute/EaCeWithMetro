package easycommute.EaCeWithMetro.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 11/15/2015.
 */
public class Stop {

    @SerializedName("stop_id")
    public int id;

    @SerializedName("stop_name")
    public String name;

    @SerializedName("stop_number")
    public int stopNo;

    @SerializedName("stop_time")
    public String stopTime;

    @SerializedName("shortcode")
    public String code;

    @SerializedName("help_text")
    public String helpTxt;

    @SerializedName("image_path")
    public String imagePath;

    @SerializedName("vehicle_started_point")
    public boolean isStartingPnt;

    @SerializedName("user_boarding_point")
    public boolean isBoardingPnt;

    @SerializedName("user_drop_off_point")
    public boolean isDroppingPnt;

}
