package lvc.com.movies.webservices;

import android.support.annotation.Nullable;
import java.io.IOException;
import retrofit2.Response;


public class ApiResponse<T> {

    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            errorMessage = getErrorMessage(response);
            body = null;
        }
    }

    private String getErrorMessage(Response<T> response) {
        String message = null;
        if (response.errorBody() != null) {
            try {
                message = response.errorBody().string();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
        if (message == null || message.trim().length() == 0) {
            message = response.message();
        }

        return message;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    public T getBody() {
        return body;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

}