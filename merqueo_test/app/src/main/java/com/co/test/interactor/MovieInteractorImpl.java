package com.co.test.interactor;

import com.co.test.model.Result;
import com.co.test.service.ApiMovie;
import com.co.test.service.ProviderService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

@SuppressWarnings("ALL")
public class MovieInteractorImpl implements MovieInteractor {

    ProviderService providerService = ProviderService.getInstance();
    CompositeDisposable disposable = providerService.getCompositeDisposable();
    Realm realm = providerService.getRealm();

    @Override
    public void getMovies(onRequestFinished finished) {
        ApiMovie apiMovie = providerService.createService(ApiMovie.class);

        disposable.add(apiMovie.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movie -> {
                    finished.onSuccessRequest(movie);
                    realm.executeTransaction(transaction -> {
                        transaction.copyToRealmOrUpdate(movie.getResults());
                    });
                } , throwable -> {
                    finished.onFailureRequest(throwable.getMessage());
                }));
    }

    @Override
    public void getMoviesStore(onRequestFinished finished) {
        RealmResults<Result> results = realm.where(Result.class).findAllAsync();
        List<Result> movies = realm.copyFromRealm(results);
        finished.onSuccessRequest(movies);
    }

    @Override
    public void getMovieDetail(String id, onRequestFinished finished) {
        Result query = realm.where(Result.class).equalTo("id", id).findFirst();
        finished.onSuccessRequest(realm.copyFromRealm(query));
    }

    @Override
    public void clearCompositeDisposable() {
        disposable.clear();
    }

    @Override
    public void disposeCompositeDisposable() {
        disposable.dispose();
    }
}
