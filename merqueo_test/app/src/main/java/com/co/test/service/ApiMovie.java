package com.co.test.service;
/*
 * Created by jparrgam on 11/23/17.
 */

import com.co.test.model.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiMovie {

    @GET("discover/movie?api_key=536bb37eb9549e2b6c7d4f7e5466ef66")
    Observable<Movie> getMovies();
}
