package com.ivancarrillo.carrillovela_ivn_examenev2.app;

import android.app.Application;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

public class MyApp extends Application {

    public void onCreate(){
        super.onCreate();
        setUpRealmConfig();
    }
    private void setUpRealmConfig() {

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();
        if (realm.where(Store.class).count() == 0) {
            realm.executeTransaction(r -> {
                List<Store> stores = Utils.getSampleData();
                r.insertOrUpdate(stores);
            });
        }
        realm.close();

    }

}

