package com.co.test.service;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderService {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    private static ProviderService instance;
    private static Retrofit.Builder builder;
    private static Gson gson;
    private static CompositeDisposable compositeDisposable;


    private ProviderService() { }

    public static ProviderService getInstance() {
        if(instance == null) {
            builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            gson = new Gson();

            compositeDisposable = new CompositeDisposable();

            instance = new ProviderService();
        }
        return instance;
    }

    public Gson getGson() {

        return gson;
    }

    public CompositeDisposable getCompositeDisposable() {

        return compositeDisposable;
    }

    public Realm getRealm() {

        return Realm.getDefaultInstance();
    }

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
