package com.co.test.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.co.test.R;
import com.co.test.adapter.AdapterMovies;
import com.co.test.model.Event;
import com.co.test.model.Movie;
import com.co.test.model.Result;
import com.co.test.presenter.MoviePresenterImpl;
import com.co.test.ui.BaseFragment;
import com.co.test.ui.onFragmentListener;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import butterknife.BindView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class MovieListFragment extends BaseFragment {

    @BindView(R.id.rcvmovies)
    RecyclerView rcvMovies;

    @BindView(R.id.pbMovies)
    MaterialProgressBar progressBar;

    private Alert alert;
    private AdapterMovies adapterMovies;
    private onFragmentListener listener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rcvMovies.setHasFixedSize(true);
        rcvMovies.setLayoutManager(new CardSliderLayoutManager(getActivity()));
        new CardSnapHelper().attachToRecyclerView(rcvMovies);

        presenter = new MoviePresenterImpl(this);
        presenter.getMovies();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentListener)
            listener = ((onFragmentListener) context);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_movies;
    }

    @Override
    public void onFailureRequest(String failure) {
        Alerter alerter = Alerter.create(getActivity());
        alerter.setBackgroundColorInt(getResources().getColor(R.color.colorAlert));
        alerter.setText(failure);
        alerter.enableSwipeToDismiss();
        alerter.setDuration(10000);
        alert = alerter.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void onSuccessRequest(T response) {
        List<Result> results = null;
        if(response instanceof Movie) {
            results = ((Movie) response).getResults();
        }

        if(response instanceof List) {
            results = ((List<Result>) response);
        }

        adapterMovies = new AdapterMovies(results, this, getActivity());
        rcvMovies.setAdapter(adapterMovies);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(Result detailMovie) {
        if(listener != null)
            listener.onDetailsFragment(String.valueOf(detailMovie.getId()));
    }

    @Override
    public void onPause() {
        super.onPause();
        if(presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void changedStatusNetwork(Event isAvaliable) {
        super.changedStatusNetwork(isAvaliable);
        presenter.setStatusNetwork(isAvaliable.isNetworkConnect());
    }
}