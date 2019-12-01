package easycommute.EaCeWithMetro.utils;

import easycommute.EaCeWithMetro.EasyCommuteApplication;
import easycommute.EaCeWithMetro.models.Commuter;

/**
 * Created by Ram Prasad on 10/16/2015.
 */
public class EasySingleton {
    private static EasySingleton singleton = new EasySingleton();

    private EasySingleton() {
    }

    public static EasySingleton getInstance() {
        return singleton;
    }

    public Commuter getCommuter() {
        return EasyCommuteApplication.getsInstance().getCommuter();
    }

    public void setCommuter(Commuter commuter) {
        if (commuter != null ) {
            EasyCommuteApplication.getsInstance().setCommuter(commuter);
        }
    }
}
