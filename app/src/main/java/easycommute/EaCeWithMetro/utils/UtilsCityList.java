package easycommute.EaCeWithMetro.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

public class UtilsCityList {
    public  String[] city = {"Select City", "Hyderabad", "Mumbai", "Delhi-NCR", "Banglore", "Pune", "Kolkata","Chennai"};
    public  String[] cityNames = { "Hyderabad", "Mumbai", "Delhi-NCR", "Banglore", "Pune", "Kolkata","Chennai"};

    public ArrayAdapter setAdapter(Context context, String[] cityList){
        ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        return aa;
    }

}
