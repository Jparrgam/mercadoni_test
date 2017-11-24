package com.co.test.presenter;

import android.app.Activity;

public interface MoviePresenter {

    void getMovies ();

    void getMovieDetail(String id);

    void onDestroyView ();

    void setActivity(Activity activity);

    void setStatusNetwork(boolean statusNetwork);

    void onPause();

    void onDestroy();
}
