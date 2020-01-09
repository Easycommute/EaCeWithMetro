package easycommute.EaCeWithMetro.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 10/15/2015.
 */
public class BookingReq {
    @SerializedName("commuter_id")
    public int commuterId;

    @SerializedName("trip_id")
    public int tripId;

    @SerializedName("from_stop_id")    // from_stop_code_id
    public int fromStopId;

    @SerializedName("to_stop_id")  // to_stop_code_id
    public int toStopId;

    @SerializedName("num_seats_choosen")
    public int noOfSeat;

    @SerializedName("promo_code")
    public String promoCode;

//    public int bookedBookingId;
//
//    public String action;
    @SerializedName("prev_booking_id")
Integer prev_booking_id=-1;

    @SerializedName("action")
    String action;

    public BookingReq(int commuterId, int tripId, int fromStopId, int toStopId, int noOfSeat, String promoCode) {
        this.commuterId = commuterId;
        this.tripId = tripId;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.noOfSeat = noOfSeat;
        this.promoCode = promoCode;
    }

    public BookingReq(int commuterId, int tripId, int fromStopId, int toStopId, int noOfSeat, String promoCode, int bookedBookingId, String action) {
        this.commuterId = commuterId;
        this.tripId = tripId;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.noOfSeat = noOfSeat;
        this.promoCode = promoCode;
        this.prev_booking_id= bookedBookingId;
        this.action = action;
    }
}
