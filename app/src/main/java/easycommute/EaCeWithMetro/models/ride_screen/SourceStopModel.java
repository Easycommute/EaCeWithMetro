package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SourceStopModel implements Serializable {

    /**
     *
     */
    @SerializedName("stop_id")
    @Expose
    private Integer stopId;
    @SerializedName("display_name")
    @Expose
    private String displayName;

    public Integer getStopId() {
        return stopId;
    }

    public void setStopId(Integer stopId) {
        this.stopId = stopId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}






