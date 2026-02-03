package com.ivancarrillo.carrillovela_ivn_examenev2.app;

import android.app.Application;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Item;

public class MyApp extends Application {

    public static AtomicInteger StoreID = new AtomicInteger();
    public static AtomicInteger ItemID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();
        
        Realm realm = Realm.getDefaultInstance();
        StoreID = getIdByTable(realm, Store.class);
        ItemID = getIdByTable(realm, Item.class);

        if (realm.where(Store.class).count() == 0) {
            realm.executeTransaction(r -> {
                List<Store> stores = Utils.getSampleData();
                r.insertOrUpdate(stores);
            });
        }
        realm.close();
    }

    private void setUpRealmConfig() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        if (results.size() > 0) {
            return new AtomicInteger(results.max("id").intValue());
        } else {
            return new AtomicInteger(0);
        }
    }

}

