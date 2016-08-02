package com.spark.meizi.home;

import com.spark.meizi.base.BasePresenter;

import io.realm.Realm;

/**
 * Created by SparkYuan on 7/28/2016.
 * Github: github.com/SparkYuan
 */
public class MeiziPresenter extends BasePresenter<IMeizi> {
    private Realm realm;

    public MeiziPresenter(IMeizi view) {
        super(view);
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }
}

interface IMeizi {
}

