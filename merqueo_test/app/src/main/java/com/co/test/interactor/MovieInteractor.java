package com.co.test.interactor;

import com.co.test.model.Movie;

public interface MovieInteractor {

    @SuppressWarnings("JavaDoc")
    interface onRequestFinished {

        /**
         * method for notifying an error of the request
         *
         * @param error
         */
        void onFailureRequest(String error);

        /**
         * method that notifies the service response
         *
         */
        <T> void onSuccessRequest(T movie);
    }

    void getMovies(onRequestFinished finished);

    void getMoviesStore(onRequestFinished finished);

    void getMovieDetail(String id, onRequestFinished finished);

    void clearCompositeDisposable();

    void disposeCompositeDisposable();
}
