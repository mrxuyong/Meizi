package com.spark.meizi.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Spark on 12/16/2015.
 */
public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {
    public RealmModelAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    // I don't want to define new RealmBaseAdapters all over the place
    // and stub out RealmBaseAdapter.getView() each time.
    // So, I made a parent class for my future RealmBaseAdapter instances.
    // They won't need getView() defined at creation time.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
