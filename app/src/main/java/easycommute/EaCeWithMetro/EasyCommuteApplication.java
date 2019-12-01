package easycommute.EaCeWithMetro;

import android.app.Application;
import android.content.Context;

import easycommute.EaCeWithMetro.api.EasyCommuteApi;
import easycommute.EaCeWithMetro.events.BusProvider;
import easycommute.EaCeWithMetro.models.Commuter;
import easycommute.EaCeWithMetro.utils.PreferenceManager;

/**
 * Created by Ram Prasad on 10/18/2015.
 */
public class EasyCommuteApplication extends Application {
    private static EasyCommuteApplication sInstance;

    public static EasyCommuteApplication getsInstance(){
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // listen for global events
        BusProvider.getInstance().register(this);

        // make sure our service gets instantiated when the applications starts,
        // because this is kind of an expensive operation...
        // NOTE: this will also register all of the events in BuzzoopApi with the event bus!
        EasyCommuteApi.getService();
    }

    public void setCommuter(Commuter commuter) {
        new PreferenceManager(this).updateCommuter(commuter);
    }

    public Commuter getCommuter() {
        return new PreferenceManager(this).getCommuter();
    }



}
