package com.co.test;

import android.content.Context;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.util.Log;

import com.co.test.interactor.MovieInteractor;
import com.co.test.interactor.MovieInteractorImpl;
import com.co.test.model.Movie;
import com.co.test.presenter.MoviePresenter;
import com.co.test.presenter.MoviePresenterImpl;
import com.co.test.service.ProviderService;
import com.co.test.view.ViewMovies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import io.realm.Realm;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MoviePresenterTest {

    @Mock MovieInteractor movieInteractor;
    @Mock ViewMovies viewMovies;
    @Mock MovieInteractor.onRequestFinished finished;
    @Mock Context fakeContext;

    private MoviePresenter presenter;
    private TestScheduler mTestScheduler;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        MockApplication mockApplication = new MockApplication(){
            @Override
            public Context getApplicationContext() {
                Log.d("tests", "Im here");
                return fakeContext;
            }
        };

        Realm.init(mockApplication);

        mTestScheduler = new TestScheduler();
        presenter = new MoviePresenterImpl(viewMovies);
    }

    @Test
    public void testResponseMoviesApi() {
        Movie movie = new Movie();

        doReturn(Observable.just(movie))
                .when(movieInteractor)
                .getMovies(finished);

        viewMovies.showProgress();

        mTestScheduler.triggerActions();
    }


    @After
    public void tearDown() throws Exception {
        presenter.onDestroy();
    }

}
