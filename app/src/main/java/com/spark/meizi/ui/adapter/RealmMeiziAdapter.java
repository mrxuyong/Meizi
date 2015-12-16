package com.spark.meizi.ui.adapter;

import android.content.Context;

import com.spark.meizi.data.model.Meizi;

import io.realm.RealmResults;

/**
 * Created by Spark on 12/16/2015.
 */
public class RealmMeiziAdapter extends RealmModelAdapter<Meizi> {
    public RealmMeiziAdapter(Context context, RealmResults<Meizi> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
