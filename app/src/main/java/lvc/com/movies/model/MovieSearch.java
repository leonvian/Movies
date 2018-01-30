package lvc.com.movies.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lvc.com.movies.database.RoomTypeConverters;

/**
 * Created by leonardo2050 on 28/01/18.
 */

@Entity(primaryKeys = {"queryTerm"})
@TypeConverters(RoomTypeConverters.class)
public class MovieSearch {

    public static final String QUERY_UPCOMING_MOVIES = "get_upcoming_movies";

    @NonNull
    private String queryTerm;

    private int page;

    private Integer nextPage;

    @Ignore
    private List<Movie> results = new ArrayList<>();

    private List<Integer> movieIDs = new ArrayList<>();

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @Embedded
    @SerializedName("dates")
    private DateRange upcomingMoviesDateRange;

    public MovieSearch() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public DateRange getUpcomingMoviesDateRange() {
        return upcomingMoviesDateRange;
    }

    public void setUpcomingMoviesDateRange(DateRange upcomingMoviesDateRange) {
        this.upcomingMoviesDateRange = upcomingMoviesDateRange;
    }

    public List<Integer> getMovieIDs() {
        return movieIDs;
    }

    public void setMovieIDs(List<Integer> movieIDs) {
        this.movieIDs = movieIDs;
    }

    public String getQueryTerm() {
        return queryTerm;
    }

    public void setQueryTerm(String queryTerm) {
        this.queryTerm = queryTerm;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public static class DateRange {

        @SerializedName("maximum")
        private Date max;

        @SerializedName("minimum")
        private Date min;

        public DateRange() {
        }

        public Date getMax() {
            return max;
        }

        public void setMax(Date max) {
            this.max = max;
        }

        public Date getMin() {
            return min;
        }

        public void setMin(Date min) {
            this.min = min;
        }
    }
}
