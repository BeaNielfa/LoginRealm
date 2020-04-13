package com.example.loginrealm.app;

import android.app.Application;

import com.example.loginrealm.models.Animales;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {
    public static AtomicLong ANIMALID = new AtomicLong();


    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        Realm realm = Realm.getDefaultInstance();
        ANIMALID = getIdByTable(realm, Animales.class);

        realm.close();
    }

    private void initRealm() {

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private <T extends RealmObject> AtomicLong getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicLong(results.max("id").intValue()) : new AtomicLong();
    }

}

