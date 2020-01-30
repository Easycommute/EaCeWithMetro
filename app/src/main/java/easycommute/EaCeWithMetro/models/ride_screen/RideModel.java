package easycommute.EaCeWithMetro.models.ride_screen;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RideModel implements Serializable
{
    @SerializedName("source_stops")
    private Set<SourceStopModel> source_stops = new HashSet<SourceStopModel>();

    @SerializedName("plans_map")
    private Map<Integer, List<PlansMap> > plans_map = new HashMap <Integer, List<PlansMap> >();

    @SerializedName("account_balance")
    private Integer account_balance;

    public Set<SourceStopModel> getSource_stops() {
        return source_stops;
    }

    public void setSource_stops(Set<SourceStopModel> source_stops) {
        this.source_stops = source_stops;
    }

    public Map<Integer, List<PlansMap>> getPlans_map() {
        return plans_map;
    }

    public void setPlans_map(Map<Integer, List<PlansMap> > plans_map) {
        this.plans_map = plans_map;
    }

    public Integer getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(Integer account_balance) {
        this.account_balance = account_balance;
    }
}