package lvc.com.movies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lvc.com.movies.R;
import lvc.com.movies.model.Movie;
import lvc.com.movies.util.GenreUtils;
import lvc.com.movies.util.ImageURLBuilder;

/**
 * Created by leonardo2050 on 27/01/18.
 */

public class DetailMovieFragment extends Fragment {

    public static final String EXTRA_MOVIE = "movie_extra";

    public static DetailMovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);

        Movie movie = getArguments().getParcelable(EXTRA_MOVIE);

        ImageView imageViewMovie  = view.findViewById(R.id.image_view_movie);
        loadImageByUrl(imageViewMovie, movie.getPosterPath());

        TextView textViewMovie = view.findViewById(R.id.text_view_movie);
        textViewMovie.setText(movie.getTitle());

        TextView textViewGenre = view.findViewById(R.id.text_view_genre);
        textViewGenre.setText(genreString(movie));


        TextView textViewVotes = view.findViewById(R.id.text_view_votes);
        textViewVotes.setText(String.valueOf(movie.getVoteCount()));

        TextView textViewPopularity = view.findViewById(R.id.text_view_popularity);
        String popularity = String.valueOf(round(movie.getPopularity()));
        textViewPopularity.setText(popularity);

        TextView textViewVoteAverage = view.findViewById(R.id.text_view_vote_average);
        textViewVoteAverage.setText(String.valueOf(movie.getVoteAverage()));


        TextView textViewMovieOverview = view.findViewById(R.id.text_view_movie_overview);
        textViewMovieOverview.setText(movie.getOverview());

        return view;
    }

    private String genreString(Movie movie) {
        List<Integer> genres = movie.getGenreIds();
        int[] arrayStrings = GenreUtils.getGenreReString(genres);
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (int str : arrayStrings) {
            if (!first) {
                stringBuilder.append(", ");
            } else {
                first = false;
            }
            stringBuilder.append(getContext().getString(str));
        }

        return stringBuilder.toString();
    }

    private static double round(double value) {
        int places = 2;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void loadImageByUrl(ImageView imageView, String path) {
        if (path == null) {
            return;
        }

        String url = ImageURLBuilder.getURLPosterSize(path);
        Glide.with(getContext())
                .load(url)
                .into(imageView);
    }

}
