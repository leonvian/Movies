package lvc.com.movies.ui.listmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import lvc.com.movies.database.DatabaseFacade;
import lvc.com.movies.database.MovieDao;
import lvc.com.movies.database.MovieDatabase;
import lvc.com.movies.model.Data;
import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import lvc.com.movies.repository.MoviesRepository;
import lvc.com.movies.util.Objects;

/**
 * Created by leonardo2050 on 28/01/18.
 */

public class ListMoviesViewModel extends AndroidViewModel {

    private final NextPageHandler nextPageHandler;

    private MoviesRepository moviesRepository;

    private MutableLiveData<String> queryInput = new MutableLiveData<>();

    private LiveData<Data<List<Movie>>> movies;

    public ListMoviesViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase = DatabaseFacade.getInstance().getMovieDatabase(application.getApplicationContext());
        moviesRepository = new MoviesRepository(movieDatabase);
        nextPageHandler = new NextPageHandler(moviesRepository);

        movies = Transformations.switchMap(queryInput, query -> moviesRepository.getMovies(query));
    }

    public void setQuery(@NonNull String query) {
        if (Objects.equals(query, queryInput.getValue())) {
            return;
        }
        nextPageHandler.reset();
        queryInput.setValue(query);
    }

    private boolean firstTime = true;

    public void refresh() {
        if (firstTime) {
            setQuery(MovieSearch.QUERY_UPCOMING_MOVIES);
            firstTime = false;
        }
    }

    public LiveData<LoadMoreState> getLoadMoreStatus() {
        return nextPageHandler.getLoadMoreState();
    }

    public void loadNextPage() {
        String value = queryInput.getValue();
        if (value == null || value.trim().length() == 0) {
            return;
        }

        nextPageHandler.queryNextPage(value);
    }

    public LiveData<Data<List<Movie>>> getMovies() {
        return movies;
    }

}
