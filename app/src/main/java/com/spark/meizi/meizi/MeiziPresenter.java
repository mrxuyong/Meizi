package com.spark.meizi.meizi;

import com.spark.meizi.base.BasePresenter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.meizi.entity.MeiziRealmEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

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

    public void requestMeizi(int page) {

    }

    private List<Meizi.ResultsBean> loadDataFromDB() {
        List<Meizi.ResultsBean> list = new ArrayList<>();
        RealmResults<MeiziRealmEntity> results = realm.where(MeiziRealmEntity.class)
                .findAllSorted("publishedAt", Sort.DESCENDING);
        for (MeiziRealmEntity entity : results) {
            Meizi.ResultsBean temp = new Meizi.ResultsBean();
            temp.setUrl(entity.getUrl());
            list.add(temp);
        }
        return list;
    }
}

interface IMeizi {
}

