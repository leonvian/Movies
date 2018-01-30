package lvc.com.movies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;

/**
 * Created by leonardo2050 on 27/01/18.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovieSearch(MovieSearch movieSearch);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAllUpcomingMovies();

    @Query("SELECT * FROM MovieSearch")
    LiveData<List<MovieSearch>> getAllMovieSearch();

    @Query("SELECT * FROM MovieSearch WHERE queryTerm = :query")
    MovieSearch getMovieSearchByQuery(String query);

    @Query("SELECT * FROM MovieSearch WHERE queryTerm = :query")
    LiveData<MovieSearch> getMovieSearchLiveDataByQuery(String query);

    @Query("SELECT * FROM movie WHERE id in (:ids)")
    LiveData<List<Movie>> getMoviesByIds(List<Integer> ids);

}
