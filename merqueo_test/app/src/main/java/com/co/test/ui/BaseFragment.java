package com.co.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.co.test.R;
import com.co.test.model.Event;
import com.co.test.presenter.MoviePresenter;
import com.co.test.view.ViewMovies;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.mindorks.nybus.event.Channel;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import butterknife.ButterKnife;

import static com.mindorks.nybus.event.Channel.ONE;

public abstract class BaseFragment extends Fragment implements ViewMovies {

    protected MoviePresenter presenter;
    protected Alert alert;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutView(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutView();

    @Override
    public void onStart() {
        super.onStart();
        NYBus.get().register(this, Channel.ONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        NYBus.get().unregister(this, Channel.ONE);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Subscribe(channelId = ONE)
    public void changedStatusNetwork(Event isAvaliable) {
        if(isAvaliable.isNetworkConnect()) {
            alert.hide();
        } else {
            Alerter alerter = Alerter.create(getActivity());
            alerter.setBackgroundColorInt(getResources().getColor(R.color.colorAlert));
            alerter.setText(getResources().getString(R.string.message_alert));
            alerter.enableSwipeToDismiss();
            alerter.setDuration(10000);
            alert = alerter.show();
        }
    }
}
