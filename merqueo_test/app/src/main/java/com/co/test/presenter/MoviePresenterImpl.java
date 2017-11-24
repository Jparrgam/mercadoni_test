package com.co.test.presenter;

import android.app.Activity;

import com.co.test.interactor.MovieInteractor;
import com.co.test.interactor.MovieInteractorImpl;
import com.co.test.model.Movie;
import com.co.test.view.ViewMovies;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class MoviePresenterImpl implements MoviePresenter {

    private MovieInteractor movieInteractor;
    private ViewMovies viewMovies;
    private boolean isNetworkEnabled = true;

    public MoviePresenterImpl(ViewMovies viewMovies) {
        this.viewMovies = viewMovies;
        this.movieInteractor = new MovieInteractorImpl();
    }

    @Override
    public void getMovies() {
        if(movieInteractor != null && viewMovies != null) {
            viewMovies.showProgress();
            if(isNetworkEnabled) {
                movieInteractor.getMovies(new MovieInteractor.onRequestFinished() {
                    @Override
                    public void onFailureRequest(String error) {
                        viewMovies.onFailureRequest(error);
                    }

                    @Override
                    public <T> void onSuccessRequest(T movie) {
                        viewMovies.onSuccessRequest(movie);
                    }
                });
            } else {
                movieInteractor.getMoviesStore(new MovieInteractor.onRequestFinished() {
                    @Override
                    public void onFailureRequest(String error) {
                        viewMovies.onFailureRequest(error);
                    }

                    @Override
                    public <T> void onSuccessRequest(T movie) {
                        viewMovies.onSuccessRequest(movie);
                    }
                });
            }
        }
    }

    @Override
    public void getMovieDetail(String id) {
        if(movieInteractor != null && viewMovies != null) {
            movieInteractor.getMovieDetail(id, new MovieInteractor.onRequestFinished() {

                @Override
                public void onFailureRequest(String error) { }

                @Override
                public <T> void onSuccessRequest(T movie) {
                    viewMovies.onSuccessRequest(movie);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        movieInteractor = null;
        viewMovies = null;
    }

    @Override
    public void onPause() {
        if(movieInteractor != null)
            movieInteractor.disposeCompositeDisposable();
    }

    @Override
    public void onDestroy() {
        if(movieInteractor != null)
            movieInteractor.clearCompositeDisposable();
    }

    @Override
    public void setActivity(Activity activity) { }

    @Override
    public void setStatusNetwork(boolean statusNetwork) {

        this.isNetworkEnabled = statusNetwork;
    }
}
