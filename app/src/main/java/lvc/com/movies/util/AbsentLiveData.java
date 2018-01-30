package lvc.com.movies.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by leonardo2050 on 29/01/18.
 */

public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }

}
