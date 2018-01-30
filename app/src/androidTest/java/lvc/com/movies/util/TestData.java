package lvc.com.movies.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;

/**
 * Created by leonardo2050 on 28/01/18.
 */

public class TestData {

    public static final Date RELEASE_DATE = new Date();

    public static MovieSearch createMovieSearchPageOneTwoMovies() {
        MovieSearch movieSearch = createMovieSearchNoIdNoMovies();

        List<Movie> movies = new ArrayList<>();
        movies.add(createTitanicFakeMovie());
        movies.add(createMadMaxFakeMovie());
        movieSearch.setResults(movies);
        movieSearch.setMovieIDs(createMovieIds());
        return movieSearch;
    }

    public static List<Integer> createMovieIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(72);
        ids.add(32);
        ids.add(84);

        return ids;
    }

    public static MovieSearch createMovieSearchNoIdNoMovies() {
        MovieSearch movieSearch = new MovieSearch();
        movieSearch.setPage(1);
        movieSearch.setTotalResults(2);
        movieSearch.setNextPage(2);
        movieSearch.setTotalPages(2);
        movieSearch.setQueryTerm(MovieSearch.QUERY_UPCOMING_MOVIES);
        movieSearch.setResults(new ArrayList<>());

        MovieSearch.DateRange dateRange = new MovieSearch.DateRange();
        Date max = getMaxDateRange();
        dateRange.setMax(max);
        dateRange.setMin(RELEASE_DATE);
        movieSearch.setUpcomingMoviesDateRange(dateRange);

        return movieSearch;
    }
    public static Date getMaxDateRange() {
        return new Date(1523333505810L);
    }

    public static Movie createTitanicFakeMovie() {
        Movie titanic = createMovie(132, 32, 9.8f, "Titanic", "posterPath", "backdropPath", false, "Romantic movie", RELEASE_DATE);
        titanic.setGenreIds(createGenreIds());
        return titanic;
    }

    public static List<Integer> createGenreIds() {
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(60);
        genreIds.add(65);

        return genreIds;
    }

    public static Movie createMadMaxFakeMovie() {
        Movie madMax = createMovie(232, 6569, 9.0f, "MadMax", "MadMaxposterPath", "MadMaxbackdropPath", false, "Adventure movie", RELEASE_DATE);
        madMax.setGenreIds(createGenreIds());
        return madMax;
    }

    public static Movie createMovie(int id) {
        Movie madMax = createMovie(id, 6569, 9.0f, "MadMax", "MadMaxposterPath", "MadMaxbackdropPath", false, "Adventure movie", RELEASE_DATE);
        madMax.setGenreIds(createGenreIds());
        return madMax;
    }

    public static Movie createMovie(int id, int voteCount, float voteAverage, String title, String posterPath, String backdropPath, boolean adult, String overview, Date releaseDate) {
        Movie movie = new Movie(id, voteCount, voteAverage, title, posterPath, backdropPath, adult, overview, releaseDate);
        movie.setGenreIds(createGenreIds());
        return movie;
    }

}
