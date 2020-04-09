package easycommute.EaCeWithMetro.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import easycommute.EaCeWithMetro.models.City;

public class UtilsCityList {
    public ArrayAdapter<City> setAdapter(Context context, City cityList){
        ArrayAdapter<City> aa = new ArrayAdapter<City>(context, android.R.layout.simple_spinner_item, (List<City>) cityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        return aa;
    }

}
