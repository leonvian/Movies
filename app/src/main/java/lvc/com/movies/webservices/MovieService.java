package lvc.com.movies.webservices;

import android.arch.lifecycle.LiveData;

import java.util.List;

import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by leonardo2050 on 27/01/18.
 */

public interface MovieService {

    public static final String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    @GET("movie/upcoming")
    LiveData<ApiResponse<MovieSearch>> getAllUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieSearch> getAllUpcomingMoviesDirect(@Query("api_key") String apiKey, @Query("page") int page);

}
