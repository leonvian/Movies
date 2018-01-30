package lvc.com.movies.util;

/**
 * Created by leonardo2050 on 29/01/18.
 */

public class ImageURLBuilder {


    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String PROFILE_SIZE = "w185";
    private static final String POSTER_SIZE = "w342"; //w342 w500


    public static String getURLProfileSize(String path) {
        String url = getURLImage(PROFILE_SIZE, path);
        return url;
    }

    public static String getURLPosterSize(String path) {
        String url = getURLImage(POSTER_SIZE, path);
        return url;
    }

    private static String getURLImage(String size, String path) {
        String url = BASE_URL.concat(size).concat(path);
        return url;
    }

}
