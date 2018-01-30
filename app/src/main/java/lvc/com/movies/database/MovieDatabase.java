package lvc.com.movies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;

/**
 * Created by leonardo2050 on 27/01/18.
 */

@Database(entities = {Movie.class, MovieSearch.class}, version = 1)
@TypeConverters({RoomTypeConverters.class})
public abstract  class MovieDatabase extends RoomDatabase {

    abstract public MovieDao movieDao();

}
