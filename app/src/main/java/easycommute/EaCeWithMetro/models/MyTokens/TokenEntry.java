
package easycommute.EaCeWithMetro.models.MyTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class TokenEntry {

    @SerializedName("plan_id")
    @Expose
    public Integer plan_id;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("token_created_at")
    @Expose
    public String token_created_at;

    @SerializedName("token_valid_till")
    @Expose
    public String token_valid_till;

    @SerializedName("planned_travel_date")
    @Expose
    public String planned_travel_date;

    @SerializedName("commuter_id")
    @Expose
    public Integer commuter_id;

    @SerializedName("is_token_verified")
    @Expose
    public Boolean is_token_verified;

    @SerializedName("token_verified_by")
    @Expose
    public Integer token_verified_by;


    @SerializedName("display_tkt_msg")
    @Expose
    public String display_tkt_msg;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("updated_at")
    @Expose
    public String updated_at;
}
