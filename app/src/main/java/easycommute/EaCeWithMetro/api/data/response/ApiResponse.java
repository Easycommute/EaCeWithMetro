package easycommute.EaCeWithMetro.api.data.response;

import com.google.gson.annotations.SerializedName;

import easycommute.EaCeWithMetro.models.Commuter;

/**
 * Created by Ram Prasad on 10/14/2015.
 */
public class ApiResponse {
    @SerializedName("returnCode")
    public ResponseStatus responseStatus;

    @SerializedName("message")
    public String message;

    @SerializedName("response")
    public Commuter commuter;

    @SerializedName("booking_id")
    public int bookingId;

    @SerializedName("max_active_bookings_number")
    public Integer maxActiveBookings;

    /**
     * Enum for the response status
     */
    public enum ResponseStatus {

        //--------- User --------
        @SerializedName("USER_ALREADY_EXIST")USER_ALREADY_EXIST("User already exist"),
        @SerializedName("USER_CREATED_SUCCESSFULLY")USER_CREATED_SUCCESSFULLY("User created Successfully"),
        @SerializedName("USER_ALREADY_VERIFIED")USER_ALREADY_VERIFIED("User already verified"),
        @SerializedName("USER_VERIFIED")USER_VERIFIED("User verified"),
        @SerializedName("USER_EXIST_OTP_GENERATED")USER_EXIST_OTP_GENERATED("User exist OTP generated"),
        @SerializedName("USER_CREATED_OTP_GENERATED")USER_CREATED_OTP_GENERATED("User created OTP generated"),
        @SerializedName("USER_CREATION_FAILED")USER_CREATION_FAILED("User Creation Failed"),
        @SerializedName("USER_NOT_EXIST")USER_NOT_EXIST("User Not Exist"),
        @SerializedName("USER_VERIFICATION_FAILED")USER_VERIFICATION_FAILED("User Verification Failed"),
        @SerializedName("USER_INVALID_OTP")USER_INVALID_OTP("Verification Failed. Please check your OTP again.."),
        @SerializedName("USER_OTP_EXPIRED_OTP_GENERATED")USER_OTP_EXPIRED_OTP_GENERATED("OTP expired. You will receive another OTP shortly.."),

        //--------- Booking --------
        @SerializedName("RESCHEDULE_SUCCESSFUL")RESCHEDULE_SUCCESSFUL("Reschedule Successful"),
        @SerializedName("RESCHEDULE_FAILED")RESCHEDULE_FAILED("Reschedule Failed"),
        @SerializedName("BOOKING_SUCCESSFUL")BOOKING_SUCCESSFUL("Booking Successful"),
        @SerializedName("BOOKING_FAILED")BOOKING_FAILED("Booking Failed"),
        @SerializedName("BOOKING_SEATS_NOT_AVAILABLE")BOOKING_SEATS_NOT_AVAILABLE("Seats not Available"),
        @SerializedName("BOOKING_NOT_ENOUGH_BALANCE")BOOKING_NOT_ENOUGH_BALANCE("Booking Not enough Balance"),
        @SerializedName("BOOKING_NOT_AVAILABLE")BOOKING_NOT_AVAILABLE("Booking not available"),
        @SerializedName("ALREADY_HAVE_MAX_ACTIVE_BOOKINGS")ALREADY_HAVE_MAX_ACTIVE_BOOKINGS("We are sorry that we can't allow more active bookings. Please try again later."),
        @SerializedName("BOOKING_GENERIC_ERROR")BOOKING_GENERIC_ERROR("Something went wrong. Please try again."),

        @SerializedName("BOOKING_CANCELLATION_SUCCESSFUL")BOOKING_CANCELLATION_SUCCESSFUL("Booking cancelled successfully!"),
        @SerializedName("BOOKING_CANCELLATION_FAILED")BOOKING_CANCELLATION_FAILED("Sorry! Booking Cancellation Failed!"),
        @SerializedName("BOOKING_ALREADY_CANCELLED_FULL_REFUNDED")BOOKING_ALREADY_CANCELLED_FULL_REFUNDED("Booking already cancelled. Going to refund full amount"),
        @SerializedName("BOOKING_ALREADY_CANCELLED_NO_REFUND_GIVEN")BOOKING_ALREADY_CANCELLED_NO_REFUND_GIVEN("Booking already cancelled. Full Amount will be refunded"),
        @SerializedName("BOOKING_ALREADY_CANCELLED_PARTIAL_REFUNDED")BOOKING_ALREADY_CANCELLED_PARTIAL_REFUNDED("Booking already cancelled. Partial Amount will be refunded"),
        @SerializedName("BOOKING_CANCELLATION_NOT_ALLOWED")BOOKING_CANCELLATION_NOT_ALLOWED("Sorry! Booking cancellation not allowed!"),
        @SerializedName("BOOKING_ID_NOT_EXIST")BOOKING_ID_NOT_EXIST("Booking Id not exist!"),
        @SerializedName("BOOKING_ALREADY_EXPIRED")BOOKING_ALREADY_EXPIRED("Booking Id already expired!"),

        //--------- Transaction --------
        @SerializedName("TRANSACION_COMPLETED_SUCCESSFULLY")TRANSACTION_COMPLETED_SUCCESSFULLY("Transaction Success!"),
        @SerializedName("TRANSACION_MANDATORY_PARAMETERS_MISSING")TRANSACTION_MANDATORY_PARAMETERS_MISSING("Missing Mandatory Fields"),
        @SerializedName("TRANSACION_UPDATION_FAILED")TRANSACTION_UPDATION_FAILED("Failed to update Transaction"),
        @SerializedName("TRANSACION_ACCOUNT_UPDATION_FAILED")TRANSACTION_ACCOUNT_UPDATION_FAILED("Transaction updated!"),

        @SerializedName("PAYMENT_TRANSACTION_UPDATION_SUCCESS")PAYMENT_TRANSACTION_UPDATION_SUCCESS("Payment Info updated!"),
        @SerializedName("PAYMENT_TRANSACTION_UPDATION_FAILED")PAYMENT_TRANSACTION_UPDATION_FAILED("Failed to updated Payment Info"),

        // ------------- Profile -----------
        @SerializedName("USER_DETAIL_UPDATION_SUCCESS")USER_DETAIL_UPDATION_SUCCESS("Your details are updated successfully"),

        //-------------- Others ------------
        @SerializedName("SUCCESS")SUCCESS("Success!"),
        @SerializedName("UNKNOWN_ERROR")UNKNOWN_ERROR("Oops! Something went wrong. Please try after sometime"),

        //----------------Routes -------------

        @SerializedName("SAVE_ROUTE_ALREADY_EXISTS")SAVE_ROUTE_ALREADY_EXISTS("Route already saved!"),
        @SerializedName("SAVE_ROUTE_SUCCESS")SAVE_ROUTE_SUCCESS("Your route saved! You can use 'Quick Book' to book quickly!"),
        @SerializedName("SAVE_ROUTE_FAILED")SAVE_ROUTE_FAILED("Failed! Please try again."),

        //---------------Monthly pass payment------
        @SerializedName("MONTHLY_PASS_PAID")MONTHLY_PASS_PAID("Successfully paid"),
        @SerializedName("MONTHLY_PASS_PARTIALLY_PAID")MONTHLY_PASS_PARTIALLY_PAID("Partially paid"),
        @SerializedName("MONTHLY_PASS_NOT_PAID")MONTHLY_PASS_NOT_PAID("Not paid"),

        //-----------------Buy a pass-------------
        @SerializedName("MP_NOT_ENOUGH_BALANCE")MP_NOT_ENOUGH_BALANCE("Balance not enough"),
        @SerializedName("MP_ALREADY_ACTIVATED")MP_ALREADY_ACTIVATED("MP_ALREADY_ACTIVATED"),
        @SerializedName("MP_ACTIVATE_FAILED")MP_ACTIVATE_FAILED("MP_ACTIVATE_FAILED"),
        @SerializedName("MP_SEATS_NOT_AVAILABLE")MP_SEATS_NOT_AVAILABLE("MP_SEATS_NOT_AVAILABLE"),
        @SerializedName("MP_ACTIVATE_GENERIC_ERROR")MP_ACTIVATE_GENERIC_ERROR("MP_ACTIVATE_GENERIC_ERROR"),
        @SerializedName("MP_ACTIVATE_SUCCESSFUL")MP_ACTIVATE_SUCCESSFUL("Success!");


        private String status;

        ResponseStatus(String status) {
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
}
