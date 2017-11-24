package com.co.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.co.test.model.Event;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.event.Channel;

import org.apache.commons.lang3.ObjectUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * receiver System events wifi state and mobile data
 *
 * @author Jaime Gamboa
 * @see android.content.BroadcastReceiver
 */
@SuppressWarnings({"JavadocReference", "JavaDoc"})
public class NetworkReceiver extends BroadcastReceiver {

    /**
     * Action settings WIFI_STATE_CHANGED and STATE_CHANGE, change status wifi network connect
     */
    String WIFI_STATE_CHANGED = "android.net.wifi.WIFI_STATE_CHANGED";
    String STATE_CHANGED = "android.net.wifi.STATE_CHANGE";
    String CONNECTIVITY_CHANGED = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * {@link Context#registerReceiver(BroadcastReceiver,
     * IntentFilter, String, Handler)}. When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     * <p/>
     * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.</b>  This means you should not perform any operations that
     * return a result to you asynchronously -- in particular, for interacting
     * with services, you should use
     * {@link Context#startService(Intent)} instead of
     * {@link Context#bindService(Intent, ServiceConnection, int)}.  If you wish
     * to interact with a service that is already running, you can use
     * {@link #peekService}.
     * <p/>
     * <p>The Intent filters used in {@link Context#registerReceiver}
     * and in application manifests are <em>not</em> guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ObjectUtils.notEqual(action, WIFI_STATE_CHANGED) || ObjectUtils.notEqual(action, STATE_CHANGED) || ObjectUtils.notEqual(action, CONNECTIVITY_CHANGED)) {
            isConnectInternet();
        }
    }

    private void isConnectInternet() {
        ReactiveNetwork.observeInternetConnectivity(new SocketInternetObservingStrategy(), "www.google.com")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectToHost -> {
                    NYBus.get().post(new Event(isConnectToHost), Channel.ONE);
                });
    }
}
