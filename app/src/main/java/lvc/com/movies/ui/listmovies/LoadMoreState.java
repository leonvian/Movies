package lvc.com.movies.ui.listmovies;

/**
 * Created by leonardo2050 on 30/01/18.
 */

public class LoadMoreState {

    private final boolean running;
    private final String errorMessage;
    private boolean handledError = false;

    LoadMoreState(boolean running, String errorMessage) {
        this.running = running;
        this.errorMessage = errorMessage;
    }

    boolean isRunning() {
        return running;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    String getErrorMessageIfNotHandled() {
        if (handledError) {
            return null;
        }
        handledError = true;
        return errorMessage;
    }
}
