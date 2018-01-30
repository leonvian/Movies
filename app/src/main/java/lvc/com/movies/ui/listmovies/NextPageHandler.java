package lvc.com.movies.ui.listmovies;

/**
 * Created by leonardo2050 on 30/01/18.
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import lvc.com.movies.model.Data;
import lvc.com.movies.repository.MoviesRepository;
import lvc.com.movies.util.Objects;


public class NextPageHandler implements Observer<Data<Boolean>> {

    @Nullable
    private LiveData<Data<Boolean>> nextPageLiveData;
    private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
    private String query;
    private final MoviesRepository repository;

    boolean hasMore;

    NextPageHandler(MoviesRepository repository) {
        this.repository = repository;
        reset();
    }

    void queryNextPage(String query) {
        if (Objects.equals(this.query, query)) {
            return;
        }

        unregister();
        this.query = query;
        nextPageLiveData = repository.searchNextPage(query);
        loadMoreState.setValue(new LoadMoreState(true, null));
        //noinspection ConstantConditions
        nextPageLiveData.observeForever(this);
    }

    @Override
    public void onChanged(@Nullable Data<Boolean> result) {
        if (result == null) {
            reset();
            return;
        } else {
            switch (result.getStatus()) {
                case SUCCESS:
                    hasMore = Boolean.TRUE.equals(result.getData());
                    unregister();
                    loadMoreState.setValue(new LoadMoreState(false, null));
                    break;
                case ERROR:
                    hasMore = true;
                    unregister();
                    loadMoreState.setValue(new LoadMoreState(false, result.getMessage()));
                    break;
            }
        }
    }

    private void unregister() {
        if (nextPageLiveData != null) {
            nextPageLiveData.removeObserver(this);
            nextPageLiveData = null;
            if (hasMore) {
                query = null;
            }
        }
    }

    protected void reset() {
        unregister();
        hasMore = true;
        loadMoreState.setValue(new LoadMoreState(false, null));
    }

    MutableLiveData<LoadMoreState> getLoadMoreState() {
        return loadMoreState;
    }
}
