package com.co.test;

import android.app.Application;

import com.mindorks.nybus.NYBus;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        NYBus.get().enableLogging();
    }
}
