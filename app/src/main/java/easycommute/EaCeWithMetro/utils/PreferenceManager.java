package easycommute.EaCeWithMetro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import easycommute.EaCeWithMetro.models.Commuter;

public class PreferenceManager {
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    Context mContext;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "EasyCommute";

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
//    private static final String BOOKING_ID = "bookingId";
    private static final String AVAIL_CREDITS = "availableCredits";
    private static final String AVAIL_FREE_RIDES = "availableFreeRaids";
    private static final String AVAIL_FREE_RIDES_MESSAGE = "availableFreeRaidsmessage";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_NAME  = "name";
    public static final String KEY_ID    = "id";
    public static final String KEY_BOOKING_ID    = "bookingID";
    public static final String KEY_BOOKING_MESSAGE    = "bookingMessage";
    private static final String KEY_COMPANY = "_company";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_CITY_ID = "cityId";
    private static final String KEY_CITY_ID_STOPS="cityIdStops";
    private static final String KEY_CHECK_UPDATES = "canCheckUpdates";
    private static final String KEY_CHECK_OFFER = "canCheckOffer";
    private static final String KEY_SEND_LOC = "shouldSendLocation";


    public PreferenceManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void   updateCommuter(Commuter commuter) {
        editor.putString(KEY_PHONE, commuter.phone);
        editor.putString(KEY_EMAIL, commuter.email);

        editor.putInt(KEY_ID, commuter.commuterId);
        editor.putString(KEY_NAME, commuter.name);
        editor.putString(KEY_GENDER, commuter.gender);
        editor.putString(KEY_COMPANY, commuter.companyName);
        editor.putInt(KEY_CITY_ID,commuter.cityId);
        editor.commit();
    }
    public int getUserId(){
        return Integer.parseInt(String.valueOf(pref.getInt(KEY_ID,0)));
    }


    public void setCheckOffer(boolean canCheck) {
        editor.putBoolean(KEY_CHECK_OFFER, canCheck);
        editor.commit();
    }

    public boolean getCheckOffer(){
        return pref.getBoolean(KEY_CHECK_OFFER, true);
    }

    public void setCheckUpdate(boolean canCheck) {
        editor.putBoolean(KEY_CHECK_UPDATES, canCheck);
        editor.commit();
    }

    public boolean getCheckUpdate(){
        return pref.getBoolean(KEY_CHECK_UPDATES, false);
    }

    public void setSendLocation(boolean shouldSend) {
        editor.putBoolean(KEY_SEND_LOC, shouldSend);
        editor.commit();
    }
    public boolean getSendLocation(){
        return pref.getBoolean(KEY_CHECK_UPDATES, true);
    }
    public Commuter getCommuter() {
        Commuter commuter = new Commuter();
        commuter.regId = pref.getString(AppConstants.REGISTRATION_ID, "");

        commuter.name  = pref.getString(KEY_NAME, "");
        commuter.commuterId = pref.getInt(KEY_ID, 0);

        commuter.phone = pref.getString(KEY_PHONE, "");
        commuter.email = pref.getString(KEY_EMAIL, "");
        commuter.gender = pref.getString(KEY_GENDER, "");
        commuter.companyName = pref.getString(KEY_COMPANY, "");
        commuter.cityId=pref.getInt(KEY_CITY_ID,0);
        return commuter;
    }

    public void setWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_PHONE, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_PHONE, null);
    }

    public void setAvailCredits(int credits) {
        editor.putInt(AVAIL_CREDITS, credits);
        editor.commit();
    }
    public String getAvailCredits() {
        return String.valueOf(pref.getInt(AVAIL_CREDITS, 0));
    }
    public void setAvailFreeRaidsMessage(String message) {
        editor.putString(AVAIL_FREE_RIDES_MESSAGE, message);
        editor.commit();
    }
    public String getAvailFreeRaidsMessage(){
        return pref.getString(AVAIL_FREE_RIDES_MESSAGE, "");
    }
    public void setAvailFreeRaids(int credits) {
        editor.putInt(AVAIL_FREE_RIDES, credits);
        editor.commit();
    }
    public int getAvailFreeRaids(){
        return pref.getInt(AVAIL_FREE_RIDES, 0);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUserRegistered(boolean isUserRegistered) {
        editor.putBoolean(AppConstants.USER_REGISTERED, isUserRegistered);
        editor.commit();
    }

    public boolean isUserRegistered() {
        return pref.getBoolean(AppConstants.USER_REGISTERED, false);
    }

    public void setRegistrationId(String regId) {
        editor.putString(AppConstants.REGISTRATION_ID, regId);
        editor.commit();
    }

    public String getRegistrationId() {
        return pref.getString(AppConstants.REGISTRATION_ID, null);
    }

//    public void setBookingId(int bookingId) {
//        editor.putInt(BOOKING_ID, bookingId);
//        editor.commit();
//    }
//
//    public int getBookingId() {
//        return pref.getInt(BOOKING_ID, 0);
//    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }



    public void setBookingMessage(String bookingMsg) {
        editor.putString(KEY_BOOKING_MESSAGE, bookingMsg);
        editor.commit();
    }

    public String getBookingMessage() {
        return pref.getString(KEY_BOOKING_MESSAGE, null);
    }

    public void setBookingId(String bookingID) {
        editor.putString(KEY_BOOKING_ID, bookingID);
        editor.commit();
    }

    public String getBookingId() {
        return pref.getString(KEY_BOOKING_ID, null);
    }

}
