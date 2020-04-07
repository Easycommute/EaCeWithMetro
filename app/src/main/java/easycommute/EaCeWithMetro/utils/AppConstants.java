package easycommute.EaCeWithMetro.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 10/18/2015.
 */
public class AppConstants {
    /**
     * Project ID
     */
    public static final String SENDER_ID = "201621376191";

    /**
     * API Key
     */
    public static final String API_KEY = "AIzaSyCiqhbUqjx2NjxT4xCQAIAOZHmIhkg4zlU";

    /**
     * Package Name
     */
    public static final String DISPLAY_MESSAGE_ACTION = "com.dotndot.buzzoop.DISPLAY_MESSAGE";

    public static final String USER_REGISTERED = "isRegistered";
    public static final String REGISTRATION_ID = "registration_id";

    public static final String BOOKING_ID = "bookingId";
    public static final String BOOKING = "booking";
    public static final String PREV_BOOKING_ID = "bookedBookingId";
    public static final String WEB_LINK = "webLink";

    public static final String ACTION = "action";
    public static final String RESCHEDULE = "reschedule";

    public static final String IS_CREDITS_IN_SHORT = "isCreditsInShort";
    public static final String CUSTOM_INTENT = "com.easy.commute.verify.success.messge";
    public static final String WAIT_SMS = "com.easy.commute.verify.wait.sms";

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String NAME_PATTERN ="^[A-Za-z][A-Za-z- \\,\\']*$";
    public static final String EMAIL_ID = "care@easycommute.co";
    public static final String WHATSAPP = "91990859937";
    public static final String PHONE_NO = "+918099927902";
    public static final String APP_VERSION = "2.2";

    public static final boolean isTesting = false;
    public static final boolean isStaging = true;

    public static final String FRAGMENT_NUM = "fragment";
    public static final String SEND_LOCATION = "send.location.commuter.details";
    public static final String LOCATION_DATA = "locationData";
    public static boolean routeSaved = false;
    public static final String USER_BOARDING_POINT = "user_boarding_point";
    public static final String STOP_IMAGE_INDEX = "stop_image_prefix";

    // For TABS

    public static final int BOOKING_PLAN = 20;
    public static final int BOARDING_DETAILS = 21;
    public static final int SHARE_AND_EARN = 8;

    public static final boolean SHOW_MONTHLY_PASS_OPTION = false;

    // for fragments
    public static final int MY_CREDITS = 5;
    public static final int BOOKING_CONFIRM = 22;


    public static String CLIENT_ID = "695284217151-ik781as443t5lrotns4s9djaerjt76t6.apps.googleusercontent.com";
    // Use your own client id

    public static String CLIENT_SECRET = "nireasyworkoutno1i3#0[[";
    // Use your own client secret

    public static String REDIRECT_URI = "http://localhost";
    public static String GRANT_TYPE = "authorization_code";
    public static String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    public static String OAUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static String OAUTH_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    public static final String CONTACTS_URL = "https://www.google.com/m8/feeds/contacts/default/full";
    public static final int MAX_NB_CONTACTS = 1000;
    public static final String APP = "";

    public static final String SUGGEST_ROUTE_COMPLETED_IN_APP = "SUGGEST_ROUTE_COMPLETED_IN_APP";
    public static final String SUGGEST_ROUTE_SKIP_RECHECKING = "SUGGEST_ROUTE_SKIP_RECHECKING";

    public static final String TAG = "easycommuteapp=>";
    public static final String FRESHCHAT_APP_KEY = "e376dc77-6a31-4156-8b25-dea304764500";
    public static final String FRESHCHAT_APP_ID = "5e29d90d-b445-4c48-b489-8419a70d8a24";


    public enum BookingStatus {
        //------------- BoardingPass details ---------
        @SerializedName("RECEIVED") RECEIVED("your booking Confirmed!"),
        @SerializedName("ACCEPTED") ACCEPTED("your booking Confirmed!"),
        @SerializedName("REJECTED") REJECTED("Your booking Rejected!"),
        @SerializedName("CANCELLED_NOREFUND") CANCELLED_NOREFUND("Your booking canceled with no refund!"),
        @SerializedName("CANCELLED_REFUNDED") CANCELLED_REFUNDED("Your booking canceled with refund!"),
        @SerializedName("CANCELLED_PARTIAL_REFUNDED") CANCELLED_PARTIAL_REFUNDED("Your booking partially refunded!"),
        @SerializedName("EXPIRED") EXPIRED("your booking expired!"),
        @SerializedName("RESCHEDULED") RESCHEDULED("your booking Rescheduled!"),
        @SerializedName("CANCELLED_BY_SYSTEM") CANCELLED_BY_SYSTEM("your booking canceled by system");

        private String status;

        BookingStatus(String status) {
            this.status = status;
        }

        public boolean equalsName(String status) {
            return (status != null) && this.status.equals(status);
        }

        @Override
        public String toString() {
            return this.status;
        }

    }

    public enum UserAppNotificationType {
        //------------- BoardingPass details ---------
        @SerializedName("BOOKING_DETAILS") BOOKING_DETAILS(1, "View"),
        @SerializedName("PROFILE") PROFILE(2, "View"),
        @SerializedName("REFER") REFER(3, "View"),
        @SerializedName("SHUTTLE_LISTING") SHUTTLE_LISTING(4, "Reserve a seat"),
        @SerializedName("RECHARGE") RECHARGE(5, "View"),
        @SerializedName("WEBVIEW_RESPONSE") WEBVIEW_RESPONSE(6, "View"),
        @SerializedName("TRACK") TRACK(7, "View"),
        @SerializedName("GENERAL") GENERAL(8, "View");
//        BOOKING_DETAILS(1, ""),
//        PROFILE (2, ""),
//        REFER(3, "Check"),
//        SHUTTLE_LISTING(4,"View"),
//        RECHARGE(5, "Check offer"),
//        WEBVIEW_RESPONSE(6, "View"),
//        TRACK(7,"Track"),
//        GENERAL(8,"");
        //Reserve a seat, View, Refer,

        private String status;
        private Integer id;

        UserAppNotificationType(Integer id, String status) {
            this.id = id;
            this.status = status;
        }

        public static UserAppNotificationType getDynamicNotificationUserAppType(Integer value) {
            for (UserAppNotificationType bs : values()) {
                if (bs.id == value) {
                    return bs;
                }
            }
            return null;
        }

        public boolean equalsName(String status) {
            return (status != null) && this.status.equals(status);
        }

        @Override
        public String toString() {
            return this.status;
        }

        public Integer getId() {
            return this.id;
        }

    }
}