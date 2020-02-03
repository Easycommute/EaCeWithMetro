package easycommute.EaCeWithMetro.api;


import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.api.data.response.PreBookingApiResponse;
import easycommute.EaCeWithMetro.models.AboutUs;
import easycommute.EaCeWithMetro.models.BookingReq;
import easycommute.EaCeWithMetro.models.City;
import easycommute.EaCeWithMetro.models.CityReq;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.models.Myhistory.HistoryReq;
import easycommute.EaCeWithMetro.models.Myhistory.HistoryResponse;
import easycommute.EaCeWithMetro.models.ride_screen.GenerateTokenModel;
import easycommute.EaCeWithMetro.models.PaymentApiResponse;
import easycommute.EaCeWithMetro.models.RazorpayDTO;
import easycommute.EaCeWithMetro.models.RideReq;
import easycommute.EaCeWithMetro.models.ride_screen.GenerateTokenResponse;
import easycommute.EaCeWithMetro.models.ride_screen.RideModel;
import easycommute.EaCeWithMetro.models.wallet_model.WalletModel;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Ram Prasad on 10/8/2015.
 */
public interface EasyCommuteService {

    //--------- Commuter --------

    @POST("/commuter/registerCommuter")
    Observable<ApiResponse> registerCommuter(@Body Commuter commuter);
    @POST("/booking/pre_booking_details")
    Observable<PreBookingApiResponse> getPreBookingDetails(@Body BookingReq bookingReq);

    @POST("/commuter/user/profile/update")
    Observable<ApiResponse> updateProfile(@Body Commuter commuter);

    @POST("/commuter/verifyCommuter")
    Observable<ApiResponse> verifyCommuter(@Body Commuter commuter);

    @POST("/commuter/findById/{commuter_id}")
    void fetchCommuterDetailsById(@Path("commuter_id") int commuterId);

    @POST("/commuter/unregisterCommuter/{mobile}")
    void unregisterCommuter(@Path("commuter_id") int commuterId);

    @POST("/commuter/regenrateOTP")
    Observable<ApiResponse> regenerateOTP(@Body Commuter commuter);

    @POST("/city/active_list")
    Observable<City> getCityActiveList(@Body CityReq cityRequest);


    @POST("/getRideScreenDetails")
    Observable<RideModel> getCityActiveList(@Body RideReq rideReq);


    @POST("/transaction/getWalletScreenDetails")
    Observable<WalletModel> getWalletDetails(@Body RideReq rideReq);


    @POST("/transaction/razorpayment/update")
    Observable<PaymentApiResponse> updatePaymentTransR(@Body RazorpayDTO razorpayDTO);

    @POST("/transaction/generateToken")
    Observable<GenerateTokenResponse> getEasyToken(@Body GenerateTokenModel generateTokenModel);

    @POST("/transaction/commuter/history")
    Observable<HistoryResponse> getHistoryDetails(@Body HistoryReq historyReq);

    @GET("/general/about_us")
    Observable<AboutUs> getAboutUsDetails();
}
