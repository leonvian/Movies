package lvc.com.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lvc.com.movies.database.MovieDatabase;
import lvc.com.movies.model.Data;
import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import lvc.com.movies.webservices.ApiResponse;
import lvc.com.movies.webservices.MovieService;
import retrofit2.Response;

/**
 * Created by leonardo2050 on 30/01/18.
 */

public class FetchNextPageMovieSearchTask implements Runnable {

    private final MutableLiveData<Data<Boolean>> hasMorePageLiveData = new MutableLiveData<>();
    private final String query;
    private final MovieService movieService;
    private final MovieDatabase db;

    FetchNextPageMovieSearchTask(String query, MovieService movieService, MovieDatabase db) {
        this.query = query;
        this.movieService = movieService;
        this.db = db;
    }

    @Override
    public void run() {
        MovieSearch currentMovieSearch = db.movieDao().getMovieSearchByQuery(query);
        if(currentMovieSearch == null) {
            hasMorePageLiveData.postValue(null);
            return;
        }

        final Integer pageToSearch = currentMovieSearch.getNextPage();
        if (pageToSearch == null) {
            hasMorePageLiveData.postValue(Data.success(false));
            return;
        }

        try {
            Response<MovieSearch> response = movieService.getAllUpcomingMoviesDirect(MovieService.API_KEY, pageToSearch).execute();
            ApiResponse<MovieSearch> apiResponse = new ApiResponse<>(response);
            if (apiResponse.isSuccessful()) {
                Integer nextPage = fetchDataAndReturnNextPage(currentMovieSearch, apiResponse);
                hasMorePageLiveData.postValue(Data.success(nextPage != null));
            } else {
                hasMorePageLiveData.postValue(Data.error(apiResponse.errorMessage, true));
            }
        } catch (IOException e) {
            e.printStackTrace();
            hasMorePageLiveData.postValue(Data.error(e.getMessage(), true));
        }
    }

    private Integer fetchDataAndReturnNextPage(MovieSearch currentMovieSearch, ApiResponse<MovieSearch> apiResponse) {
        MovieSearch mergedMovieSearch = new MovieSearch();

        mergedMovieSearch.setQueryTerm(query);

        List<Integer> idMovies = new ArrayList<>();
        idMovies.addAll(currentMovieSearch.getMovieIDs());
        List<Integer> moviesids = moviesToIds(apiResponse.body);
        idMovies.addAll(moviesids);
        mergedMovieSearch.setMovieIDs(idMovies);

        mergedMovieSearch.setPage(apiResponse.body.getPage());
        Integer nextPage = getNextPage(apiResponse);
        mergedMovieSearch.setNextPage(nextPage);

        List<Movie> movies = apiResponse.body.getResults();
        saveDataLocally(mergedMovieSearch, movies);

        return nextPage;
    }

    private List<Integer> moviesToIds(MovieSearch movieSearch) {
        List<Movie> movies = movieSearch.getResults();
        List<Integer> ids = new ArrayList<>();
        for (Movie movie : movies) {
            ids.add(movie.getId());
        }

        return ids;
    }

    private void saveDataLocally(MovieSearch movieSearch, List<Movie> movies) {
        try {
            db.beginTransaction();
            db.movieDao().insertMovieSearch(movieSearch);
            db.movieDao().insertMovies(movies);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private Integer getNextPage(ApiResponse<MovieSearch> apiResponse) {
        int currentPage = apiResponse.body.getPage();
        int totalPages = apiResponse.body.getTotalPages();
        if (currentPage >= totalPages) {
            return null;
        } else {
            currentPage ++;
            return currentPage;
        }
    }

    private Response<MovieSearch> getMovieSearch(String query, int nextPage) throws IOException {
        if (MovieSearch.QUERY_UPCOMING_MOVIES.equals(query)) {
            return movieService.getAllUpcomingMoviesDirect(query, nextPage).execute();
        } else {
            return null; // ADD HERE feature get movies searching by custom query
        }

    }

    LiveData<Data<Boolean>> getHasMorePageLiveData() {
        return hasMorePageLiveData;
    }
}

