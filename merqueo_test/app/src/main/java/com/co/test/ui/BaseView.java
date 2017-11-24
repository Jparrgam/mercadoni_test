package com.co.test.ui;

/*
 * Created by jparrgam on 11/23/17.
 */

public interface BaseView {

    void onFailureRequest (String failure);

    <T> void onSuccessRequest (T response);
}
