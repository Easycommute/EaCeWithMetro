package easycommute.EaCeWithMetro.utils;

public interface Refreshable {
    public static Boolean supportsRefreshOfData = true;

    public class defaultImpl {
        defaultImpl() {
            System.out.println("DefaultImpl Constructor");
        }

        void returnNameOfClass(String funcName, Object args) {

        }
    }

    public static void refresh (Object obj, String funcName, Boolean isClassProvided) {
        if(Refreshable.supportsRefreshOfData ) {
            System.out.println("Hello World!");
            System.out.println (obj);
            System.out.println("Bye World!");

            if(!isClassProvided) {
                assert(funcName != null);
               // new Refreshable.defaultImpl().funcName();
                return;

            } else {
                assert(funcName != null);
                //Refreshable.defaultImpl sObj = new Refreshable.defaultImpl().
            }
        }
    }
}
