package easycommute.EaCeWithMetro.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ram Prasad on 11/14/2015.
 */
public class BookingInfo implements Parcelable {
    public int commuterId;
    public int noOfSeat;
    public int tripId;

    public String fromStop;
    public int fromStopId;

    public String toStop;
    public int toStopId;

    @SerializedName("from_stop_code")
    public String fromStopCode;

    @SerializedName("to_stop_code")
    public String toStopCode;

    public BookingInfo(int fromStopId, int toStopId, String fromStop, String toStop, String fromStopCode, String toStopCode) {
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.fromStopCode = fromStopCode;
        this.toStopCode = toStopCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.commuterId);
        dest.writeInt(this.noOfSeat);
        dest.writeInt(this.tripId);
        dest.writeString(this.fromStop);
        dest.writeInt(this.fromStopId);
        dest.writeString(this.toStop);
        dest.writeInt(this.toStopId);
        dest.writeString(this.fromStopCode);
        dest.writeString(this.toStopCode);
    }

    public BookingInfo() {
    }

    protected BookingInfo(Parcel in) {
        this.commuterId = in.readInt();
        this.noOfSeat = in.readInt();
        this.tripId = in.readInt();
        this.fromStop = in.readString();
        this.fromStopId = in.readInt();
        this.toStop = in.readString();
        this.toStopId = in.readInt();
        this.fromStopCode = in.readString();
        this.toStopCode = in.readString();
    }

    public static final Parcelable.Creator<BookingInfo> CREATOR = new Parcelable.Creator<BookingInfo>() {
        public BookingInfo createFromParcel(Parcel source) {
            return new BookingInfo(source);
        }

        public BookingInfo[] newArray(int size) {
            return new BookingInfo[size];
        }
    };



    public BookingReq getBookingReq(String promoCode) {
        return new BookingReq(commuterId, tripId, fromStopId, toStopId, noOfSeat, promoCode);
    }

    public BookingReq getBookingReq(String promoCode, int bookedBookingId, String action) {
        return new BookingReq(commuterId, tripId, fromStopId, toStopId, noOfSeat, promoCode, bookedBookingId, action);
    }



}
