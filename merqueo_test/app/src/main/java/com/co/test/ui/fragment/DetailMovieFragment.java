package com.co.test.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.co.test.R;
import com.co.test.model.Result;
import com.co.test.presenter.MoviePresenterImpl;
import com.co.test.ui.BaseFragment;
import com.co.test.view.ViewMovies;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailMovieFragment extends BaseFragment implements ViewMovies {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.description_movie_detail)
    RobotoTextView descriptionMovieDetail;

    @BindView(R.id.votes_movie_detail)
    RobotoTextView votesMovieDetail;

    @BindView(R.id.poster_movie)
    ImageView posterMovie;

    public static final String EXTRA_ID = "com.co.test.extraid";

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_detail_movie;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().supportPostponeEnterTransition();

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);


        presenter = new MoviePresenterImpl(this);
        presenter.getMovieDetail(getArguments().getString(EXTRA_ID));
    }

    @Override
    public void onFailureRequest(String failure) { }

    @Override
    public <T> void onSuccessRequest(T response) {
        Result result = ((Result) response);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+ result.getPosterPath())
                .apply(bitmapTransform(new BlurTransformation(30)))
                .into(posterMovie);

        descriptionMovieDetail.setText(result.getOverview());
        votesMovieDetail.setText(String.valueOf(result.getVoteAverage()));
        toolbar.setTitle(result.getTitle());

    }

    @Override
    public void showProgress() { }

    @Override
    public void onItemSelected(Result detailMovie) { }
}
