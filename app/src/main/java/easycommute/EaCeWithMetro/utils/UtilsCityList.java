package easycommute.EaCeWithMetro.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class UtilsCityList {
    public ArrayAdapter setAdapter(Context context, List<String> cityList){
        ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        return aa;
    }

}
