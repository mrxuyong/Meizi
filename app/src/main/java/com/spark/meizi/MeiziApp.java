package com.spark.meizi;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Spark on 12/13/2015.
 */
public class MeiziApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build());
        MeiziContext.getInstance().init(getApplicationContext());
    }

}
