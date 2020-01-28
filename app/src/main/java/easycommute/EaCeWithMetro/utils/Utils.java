package easycommute.EaCeWithMetro.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.app.NotificationCompat;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import easycommute.EaCeWithMetro.R;


/**
 * Created by Niranjan on 11/3/2015.
 */
public class Utils {

    public String getMonthForInt(int num) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public String convertToDate(long timeStamp) {
        return getEasyDateFormat(timeStamp, "dd/MM/yyyy hh:mm a");
    }

    public static String getEasyDateFormat(long timeStamp, String format) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(tz);

        return sdf.format(new Date(timeStamp));
    }


    public static void alert(Context mContext, String msg, final onOkClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                        if (listener != null) listener.onOkClicked();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public interface onOkClickListener {
        public void onOkClicked();
    }

    public int getDeviceSize(Context mContext) {
        int density = mContext.getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                return 24;
            case DisplayMetrics.DENSITY_MEDIUM:
                return 48;
            case DisplayMetrics.DENSITY_HIGH:
                return 72;
            case DisplayMetrics.DENSITY_XHIGH:
                return 96;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 144;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 192;
            default:
                return 24;
        }
    }


}
