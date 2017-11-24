package com.co.test.model;
/*
 * Created by jparrgam on 11/24/17.
 */

public class Event {

    private boolean isNetworkConnect;

    public Event(boolean isNetworkConnect) {
        this.isNetworkConnect = isNetworkConnect;
    }

    public boolean isNetworkConnect() {
        return isNetworkConnect;
    }

    public void setNetworkConnect(boolean networkConnect) {
        isNetworkConnect = networkConnect;
    }
}
