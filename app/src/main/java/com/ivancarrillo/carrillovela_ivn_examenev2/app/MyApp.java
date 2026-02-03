package com.ivancarrillo.carrillovela_ivn_examenev2.app;

import android.app.Application;

import com.ivancarrillo.carrillovela_ivn_examenev1.model.Book;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {

    public static AtomicInteger bookId = new AtomicInteger();

    public void onCreate(){

        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();

        bookId = getIdByTable(realm, Book.class);

        realm.close();

    }
    private void setUpRealmConfig() {

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){

        RealmResults<T> results = realm.where(anyClass).findAll();

        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealmOrUpdate(Utils.getSampleBooks());
        realm.commitTransaction();

        if (results.size()>0){
            return new AtomicInteger(results.max("id").intValue());
        }else{
            return  new AtomicInteger(0);
        }

    }

}

