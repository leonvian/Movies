package lvc.com.movies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import lvc.com.movies.util.TestData;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static lvc.com.movies.util.LiveDataTestUtil.getValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

/**
 * Created by leonardo2050 on 27/01/18.
 */

@RunWith(AndroidJUnit4.class)
public class MovieDaoTest {

    private MovieDao movieDao;
    private MovieDatabase movieDatabase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        movieDatabase = Room.inMemoryDatabaseBuilder(context, MovieDatabase.class).build();
        movieDao = movieDatabase.movieDao();
    }

    @After
    public void closeDb() throws IOException {
        movieDatabase.close();
    }

    @Test
    public void shouldSaveMovieSearch() throws InterruptedException {
        MovieSearch movieSearch = TestData.createMovieSearchPageOneTwoMovies();
        movieDao.insertMovieSearch(movieSearch);

        LiveData<MovieSearch> movieSearchData = movieDao.getMovieSearchLiveDataByQuery(MovieSearch.QUERY_UPCOMING_MOVIES);
        MovieSearch loaded = getValue(movieSearchData);
        assertThat(loaded.getQueryTerm(), is(MovieSearch.QUERY_UPCOMING_MOVIES));
        assertThat(loaded.getPage(), is(1));
        assertThat(loaded.getNextPage(), is(2));
        assertThat(loaded.getTotalResults(), is(2));
        assertThat(loaded.getMovieIDs().size(), is(TestData.createMovieIds().size()));

        List<Integer> ids = TestData.createMovieIds();
        for (Integer movieId : ids) {
            assertThat(loaded.getMovieIDs().contains(movieId), is(true));
        }

        assertThat(loaded.getResults().isEmpty(), is(true));
        assertThat(loaded.getUpcomingMoviesDateRange().getMin().getTime(), is(TestData.RELEASE_DATE.getTime()));
        assertThat(loaded.getUpcomingMoviesDateRange().getMax().getTime(), is(TestData.getMaxDateRange().getTime()));
    }


    @Test
    public void shouldSaveAndReturnMovies() throws InterruptedException {
        Movie titanic = TestData.createTitanicFakeMovie();
        Movie madMax = TestData.createMadMaxFakeMovie();
        movieDao.insert(titanic);
        movieDao.insert(madMax);

        LiveData<List<Movie>> data = movieDao.getAllUpcomingMovies();
        List<Movie> movies = getValue(data);
        assertThat(movies.size(), is(2));
        assertThat(movies.contains(titanic), is(true));
        assertThat(movies.contains(madMax), is(true));

        int indexMovie = movies.indexOf(titanic);
        Movie loadedMovie = movies.get(indexMovie);

        assertThat(loadedMovie.getId(), is(132));
        assertThat(loadedMovie.getGenreIds().size(), is(TestData.createGenreIds().size()));

        List<Integer> genderIds = TestData.createGenreIds();
        for (Integer genderId : genderIds) {
            assertThat(loadedMovie.getGenreIds().contains(genderId), is(true));
        }

        assertThat(loadedMovie.getVoteCount(), is(32));
        assertThat(loadedMovie.getVoteAverage(), is(9.8f));
        assertThat(loadedMovie.getTitle(), is("Titanic"));
        assertThat(loadedMovie.getPosterPath(), is("posterPath"));
        assertThat(loadedMovie.getBackdropPath(), is("backdropPath"));
        assertThat(loadedMovie.isAdult(), is(false));
        assertThat(loadedMovie.getReleaseDate().getTime(), is(TestData.RELEASE_DATE.getTime()));
    }

    @Test
    public void shouldDeleteMovies() throws InterruptedException {
        Movie titanic = TestData.createTitanicFakeMovie();
        Movie madMax = TestData.createMadMaxFakeMovie();
        movieDao.insert(titanic);
        movieDao.insert(madMax);

        movieDao.delete(titanic);

        List<Movie> movies1 = getValue(movieDao.getAllUpcomingMovies());
        assertThat(movies1.size(), is(1));
    }


}
