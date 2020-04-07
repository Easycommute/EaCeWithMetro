package easycommute.EaCeWithMetro.api;

import android.os.Build;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import easycommute.EaCeWithMetro.BuildConfig;
import easycommute.EaCeWithMetro.utils.AppConstants;
import easycommute.EaCeWithMetro.utils.EasySingleton;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidApacheClient;
import retrofit.android.AndroidLog;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.UrlConnectionClient;

/**
 * Created by Ram Prasad on 6/9/2015.
 */
public final class EasyCommuteApi {
    private static final String TEST_URL = "https://dev.easycommute.co/sts";
    //private static final String PRODUCTION_URL = "https://corporate.easycommute.co/corporate";
    //private static final String PRODUCTION_URL = "https://api.easycommute.co/sts";
    //http://prod.easycommute.co
  //    private static final String PRODUCTION_URL = "https://apiv2.easycommute.co/sts";
    private static final String PRODUCTION_URL = "https://metro.easycommute.in";
    private static final String STAGING_URL = "https://staging.easycommute.co";

    private static String SERVER_URL = PRODUCTION_URL;
    private static final String PLACES_URL = "https://maps.googleapis.com/maps/api";

    private static EasyCommuteApi sInstance;
    private EasyCommuteService mService;

    /**
     * Singleton "constructor"
     *
     * @return Returns the singleton sInstance
     */
    public static EasyCommuteService getService() {
        if (sInstance == null) {
            sInstance = new EasyCommuteApi();
        }

        return sInstance.mService;
    }

    /**
     * Private constructor
     */
    private EasyCommuteApi() {
        // configuring testing if testing
        // TODO: Need to handle this properly
        if (AppConstants.isTesting)
            SERVER_URL = TEST_URL;
        if (AppConstants.isStaging)
            SERVER_URL = STAGING_URL;

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(SERVER_URL);
        // add full request/response logs if we are a debug build
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL).setLog(
                    new AndroidLog("RetrofitApi"));
        }

        mService = builder.build().create(EasyCommuteService.class);
    }



    /**
     * A request interceptor used to modify all requests sent through this   service. Currently,
     * this is responsible for adding a User-Agent and X-Authorization header to  the request.
     */
    private RequestInterceptor requestInterceptor = new RequestInterceptor() {
       /* String credentials = "username:password";

        private final String auth =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);*/

        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("commuter_id", EasySingleton.getInstance().getCommuter().commuterId + "");
            //  request.addHeader("Authorization", auth);
        }
    };



    /**
     * Private constructor
     */
    private EasyCommuteApi(String BASEURL) {
        // configuring testing if testing
        // TODO: Need to handle this properly
        SERVER_URL = BASEURL;
        //setAuthenticate();

        Client client = null;
        RestAdapter.Builder builder = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(150, TimeUnit.SECONDS); // connect timeout
            okHttpClient.setWriteTimeout(150, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(120, TimeUnit.SECONDS); // socket timeout
            client = new OkClient(okHttpClient);

            builder = new RestAdapter.Builder()
                    .setRequestInterceptor(requestInterceptor)
                    .setClient(client)
                    .setEndpoint(SERVER_URL);

            // add full request/response logs if we are a debug build
            if (BuildConfig.DEBUG) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL).setLog(
                        new AndroidLog("RetrofitApi"));
            }

            mService = builder.build().create(EasyCommuteService.class);

            // try a test request after initializing the connection.

          //  mService.fetchFromStopsV2();
        } catch (Throwable t){
            t.printStackTrace();
            client = getClient();
            builder = new RestAdapter.Builder()
                    .setRequestInterceptor(requestInterceptor)
                    .setClient(client)
                    .setEndpoint(SERVER_URL);
            mService = builder.build().create(EasyCommuteService.class);
        }
    }



    private Client getClient(){
        final Client client;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            client = new AndroidApacheClient();
        } else {
            client = new UrlConnectionClient();
        }
        return client;
    }
}