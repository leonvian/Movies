package lvc.com.movies.util;

/**
 * Created by leonardo2050 on 28/01/18.
 */

public class Objects {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

}
