package com.spark.meizi.data.dao;

import com.spark.meizi.data.model.AndroidBlog;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Spark on 4/25/2016 19:18 21:52 21:52.
 */
public class AndroidBlogDAO {
    static Realm realm;

    public static void bulkInsert(List<AndroidBlog> androidList) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (AndroidBlog androidBlog : androidList) {
            realm.copyToRealmOrUpdate(androidBlog);
        }
        realm.commitTransaction();
        realm.close();
    }
}
