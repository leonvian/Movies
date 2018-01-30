package lvc.com.movies.ui.listmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lvc.com.movies.R;
import lvc.com.movies.model.Movie;
import lvc.com.movies.ui.DiffAdapter;
import lvc.com.movies.ui.OnItemClickRV;
import lvc.com.movies.util.ImageURLBuilder;
import lvc.com.movies.util.Objects;

/**
 * Created by leonardo2050 on 28/01/18.
 */
//
public class MoviesAdapter extends DiffAdapter<Movie, MoviesAdapter.MovieViewHolder> {

    private OnItemClickRV<Movie> onItemClickRV;
    private Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickRV(OnItemClickRV<Movie> onItemClickRV) {
        this.onItemClickRV = onItemClickRV;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final Movie movie = getItem(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewOverview.setText(toStr(movie.getReleaseDate()));
        loadImageByUrl(holder.imageViewMovie, movie.getPosterPath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickRV == null) {
                    return;
                }
                onItemClickRV.onItemClick(movie);
            }
        });
    }

    private static String toStr(Date releaseDate) {
        String dateStr = "";
        if(releaseDate != null) {
            dateStr = SimpleDateFormat.getDateInstance().format(releaseDate);
        }
        return dateStr;
    }

    private void loadImageByUrl(ImageView imageView, String path) {
        if (path == null) {
            return;
        }

        String url = ImageURLBuilder.getURLProfileSize(path);
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    protected boolean areItemsTheSame(Movie oldItem, Movie newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(Movie oldItem, Movie newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle());
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewMovie;
        TextView textViewTitle;
        TextView textViewOverview;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.image_view_movie);
            textViewTitle = itemView.findViewById(R.id.text_view_movie);
            textViewOverview = itemView.findViewById(R.id.text_view_movie_overview);
        }
    }


}