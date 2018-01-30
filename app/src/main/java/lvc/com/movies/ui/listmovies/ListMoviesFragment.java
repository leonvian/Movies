package lvc.com.movies.ui.listmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import lvc.com.movies.R;
import lvc.com.movies.model.Movie;
import lvc.com.movies.model.MovieSearch;
import lvc.com.movies.ui.DetailMovieFragment;

/**
 * Created by leonardo2050 on 27/01/18.
 */

public class ListMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private View progressBar;
    private MoviesAdapter moviesAdapter;
    private ListMoviesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_movies, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListMoviesViewModel.class);
        viewModel.getMovies().observe(this, dataMovies -> {
            switch (dataMovies.getStatus()) {
                case ERROR:
                    Toast.makeText(getContext(), dataMovies.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.INVISIBLE);
                    moviesAdapter.replace(dataMovies.getData());
                    break;
            }
        });

        viewModel.getLoadMoreStatus().observe(this, loadingMore -> {
            if (loadingMore == null) {
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                showProgressBar(loadingMore.isRunning());
                String error = loadingMore.getErrorMessageIfNotHandled();
                if (error != null) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });

        initRecyclerView();
        viewModel.refresh();
    }

    private void showProgressBar(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecyclerView() {
        moviesAdapter = new MoviesAdapter (getContext());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnItemClickRV(selectedMovie -> {
            goToDetailFragment(selectedMovie);
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if (lastPosition == moviesAdapter.getItemCount() - 1) {
                    viewModel.loadNextPage();
                }
            }
        });
     }

    public void goToDetailFragment(Movie movie) {
        Fragment detail = DetailMovieFragment.newInstance(movie);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detail, "detailsFragment").addToBackStack("details").commit();
    }


}
