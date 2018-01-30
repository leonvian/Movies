package lvc.com.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lvc.com.movies.database.DatabaseFacade;
import lvc.com.movies.database.MovieDao;
import lvc.com.movies.database.MovieDatabase;
import lvc.com.movies.model.Data;
import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import lvc.com.movies.util.AbsentLiveData;
import lvc.com.movies.util.AppExecutors;
import lvc.com.movies.webservices.ApiResponse;
import lvc.com.movies.webservices.MovieService;
import lvc.com.movies.webservices.RetrofitFacade;

/**
 * Created by leonardo2050 on 28/01/18.
 */

public class MoviesRepository {

    private MovieDatabase movieDatabase;

    public MoviesRepository(MovieDatabase movieDatabase) {
        this.movieDatabase = movieDatabase;
    }

    public LiveData<Data<Boolean>> searchNextPage(String query) {
        MovieService movieService = RetrofitFacade.getInstance().getMovieService();
        FetchNextPageMovieSearchTask fetchNextSearchPageTask = new FetchNextPageMovieSearchTask(query, movieService, movieDatabase);
        AppExecutors.getInstance().networkIO().execute(fetchNextSearchPageTask);

        return fetchNextSearchPageTask.getHasMorePageLiveData();
    }

    public LiveData<Data<List<Movie>>> getMovies(String query) {
        return new NetworkBoundResource<List<Movie>, MovieSearch>() {

            @Override
            protected void saveCallResult (@NonNull MovieSearch movieSearch){
                List<Movie> movies = movieSearch.getResults();
                try {
                    movieDatabase.beginTransaction();
                    MovieDao movieDao = movieDatabase.movieDao();
                    movieDao.insertMovies(movies);

                    List<Integer> idMovies = new ArrayList<>();
                    for (Movie movie : movies) {
                        idMovies.add(movie.getId());
                    }
                    movieSearch.setMovieIDs(idMovies);
                    movieSearch.setQueryTerm(query);
                    int currentPage = movieSearch.getPage();
                    int totalPages = movieSearch.getTotalPages();
                    Integer nextPage = currentPage + 1;
                    if (currentPage >= totalPages) {
                        nextPage = null;
                    }
                    movieSearch.setNextPage(nextPage);
                    movieDao.insertMovieSearch(movieSearch);

                    movieDatabase.setTransactionSuccessful();
                } finally {
                    movieDatabase.endTransaction();
                }

            }

            @Override
            protected boolean shouldFetch (@Nullable List < Movie > data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb () {
                MovieDao movieDao = movieDatabase.movieDao();
                return Transformations.switchMap(movieDao.getMovieSearchLiveDataByQuery(query), movieSearch -> {
                    if(movieSearch == null ) {
                        return AbsentLiveData.create();
                    } else {
                        List<Integer> movieIds = movieSearch.getMovieIDs();
                        return movieDao.getMoviesByIds(movieIds);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieSearch>> createCall () {
                if (MovieSearch.QUERY_UPCOMING_MOVIES.equals(query)) {
                    return RetrofitFacade.getInstance().getMovieService().getAllUpcomingMovies(MovieService.API_KEY);
                } else  {
                    return null; //New FEATURE get movies by query
                }

            }
        }.asLiveData();

    }
}