package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {

    @SerializedName("response")
    public List<CityData> cityDataList;

    @SerializedName("returnCode")
    public String returnCode;

    @SerializedName("message")
    public String message;

    public class CityData
    {
        @SerializedName("id")
        public Integer id;

        @SerializedName("name")
        public String name;
    }
}
