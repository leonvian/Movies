package lvc.com.movies.webservices;

import lvc.com.movies.util.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leonardo2050 on 27/01/18.
 */

public class RetrofitFacade {

    private static final String URL_BASE = "https://api.themoviedb.org/3/";

    private MovieService movieService;
    private static RetrofitFacade instance;


    public static RetrofitFacade getInstance() {
        if (instance == null) {
            instance = new RetrofitFacade();
        }
        return instance;
    }

    public MovieService getMovieService() {
        if (movieService == null) {
            movieService = createRetrofit().create(MovieService.class);
        }

        return movieService;
    }

    private Retrofit createRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        return retrofit;
    }

}
