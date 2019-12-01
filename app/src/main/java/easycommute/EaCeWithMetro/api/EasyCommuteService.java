package easycommute.EaCeWithMetro.api;

import android.database.Observable;

import easycommute.EaCeWithMetro.api.data.response.ApiResponse;
import easycommute.EaCeWithMetro.models.Commuter;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Ram Prasad on 10/8/2015.
 */
public interface EasyCommuteService {

    //--------- Commuter --------

    @POST("/commuter/registerCommuter")
    Observable<ApiResponse> registerCommuter(@Body Commuter commuter);

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

    //--------- Booking --------


}
