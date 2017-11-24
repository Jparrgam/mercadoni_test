package com.co.test.view;

import com.co.test.model.Result;
import com.co.test.ui.BaseView;

@SuppressWarnings("JavaDoc")
public interface ViewMovies extends BaseView {

    void showProgress();

    void onItemSelected (Result detailMovie);
}
