package lvc.com.movies.database;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by leonardo2050 on 27/01/18.
 */

public class DatabaseFacade {

    private MovieDatabase movieDatabase;
    private static DatabaseFacade instance;

    public static  DatabaseFacade getInstance() {
        if (instance == null) {
            instance = new DatabaseFacade();
        }
        return instance;
    }

    public MovieDatabase getMovieDatabase(Context context) {
        if (movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movies-database").build();
        }

        return movieDatabase;
    }

}
