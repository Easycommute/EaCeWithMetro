package easycommute.EaCeWithMetro.events;

import com.squareup.otto.Bus;

/**
 * Created by Ram Prasad on 6/10/2015.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    /**
     * @return Instance of the event bus {@link Bus}
     */
    public static Bus getInstance() {
        return BUS;
    }

    /**
     * Default constructor. No instances.
     */
    private BusProvider() { }
}
