package easycommute.EaCeWithMetro.api;


import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.models.City;
import easycommute.EaCeWithMetro.models.Commuter;
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

    @POST("/commuter/user/profile/update")
    Observable<ApiResponse> updateProfile(@Body Commuter commuter);

    @POST("/commuter/verifyCommuter")
    Observable<ApiResponse> verifyCommuter(@Body Commuter commuter);

    @POST("/commuter/findById/{commuter_id}")
    void fetchCommuterDetailsById(@Path("commuter_id") int commuterId);

    @POST("/commuter/unregisterCommuter/{mobile}")
    void unregisterCommuter(@Path("commuter_id") int commuterId);

    @POST("/commuter/updateGCMRegistrationId")
    Observable<ApiResponse> updateGCMRegistrationId(@Body Commuter commuter);
    // void updateGCMRegistrationId(@Body Commuter commuter);

    @POST("/commuter/regenrateOTP")
    Observable<ApiResponse> regenerateOTP(@Body Commuter commuter);

    @GET("/city/list")
    Observable<City> getCityList();

    //--------- Booking --------


}
