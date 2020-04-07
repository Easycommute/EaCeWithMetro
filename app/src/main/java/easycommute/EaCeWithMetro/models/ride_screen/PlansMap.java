package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlansMap implements Serializable{


    @SerializedName("city_id")
    private int city_id;

    @SerializedName("source_stop_id")
    private Integer source_stop_id;

    @SerializedName("travel_plan_id")
    private Integer travel_plan_id;

    @SerializedName("destination_stop_display")
    private String destination_stop_display;

    @SerializedName("source_stop_display")
    private String source_stop_display;

    @SerializedName("travel_plan_display")
    private String travel_plan_display;

    @SerializedName("travel_plan_fare")
    private Integer travel_plan_fare;

    @SerializedName("ride_type_id")
    private Integer ride_type_id;


    public Integer getSource_stop_id() {
        return source_stop_id;
    }
    public void setSource_stop_id(Integer source_stop_id) {
        this.source_stop_id = source_stop_id;
    }
    public Integer getTravel_plan_id() {
        return travel_plan_id;
    }
    public void setTravel_plan_id(Integer travel_plan_id) {
        this.travel_plan_id = travel_plan_id;
    }
    public String getDestination_stop_display() {
        return destination_stop_display;
    }
    public void setDestination_stop_display(String destination_stop_display) {
        this.destination_stop_display = destination_stop_display;
    }
    public String getTravel_plan_display() {
        return travel_plan_display;
    }
    public void setTravel_plan_display(String travel_plan_display) {
        this.travel_plan_display = travel_plan_display;
    }
    public String getSource_stop_display() {
        return source_stop_display;
    }
    public void setSource_stop_display(String source_stop_display) {
        this.source_stop_display = source_stop_display;
    }
    public int getCity_id() {
        return city_id;
    }
    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
    public Integer getTravel_plan_fare() {
        return travel_plan_fare;
    }
    public void setTravel_plan_fare(Integer travel_plan_fare) {
        this.travel_plan_fare = travel_plan_fare;
    }
    public Integer getRide_type_id() {
        return ride_type_id;
    }
    public void setRide_type_id(Integer ride_type_id) {
        this.ride_type_id = ride_type_id;
    }

}

