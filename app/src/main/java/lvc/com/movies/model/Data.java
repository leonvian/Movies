package lvc.com.movies.model;

import static lvc.com.movies.model.Data.Status.ERROR;
import static lvc.com.movies.model.Data.Status.LOADING;
import static lvc.com.movies.model.Data.Status.SUCCESS;

/**
 * Created by leonardo2050 on 28/01/18.
 */

public class Data<T> {

    private final Status status;
    private final T data;
    private final String message;

    public Data(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Data<T> success(T data) {
        return new Data<>(SUCCESS, data, null);
    }

    public static <T> Data<T> error(String msg, T data) {
        return new Data<>(ERROR, data, msg);
    }

    public static <T> Data<T> loading( T data) {
        return new Data<>(LOADING, data, null);
    }


    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public enum Status {
        SUCCESS, ERROR, LOADING
    }

    @Override
    public String toString() {
        return "Data{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
