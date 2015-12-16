package com.spark.meizi.data.net;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spark.meizi.api.GankApi;
import com.spark.meizi.data.dao.MeiziDAO;
import com.spark.meizi.data.model.Meizi;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Spark on 12/13/2015.
 */
public class SparkRetrofit {
    public Retrofit retrofit;
    private GankApi gankApi;
    public static final int REQUEST_MEIZI_COUNT = 10;

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setExclusionStrategies(new ExclusionStrategy() {   //把RealmObject排除在外,不然会报错。
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    public SparkRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.avosapps.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    public List<Meizi> getLatest(int page) {

        GankApi.Result<List<Meizi>> result = null;
        try {
            result = gankApi.latest(REQUEST_MEIZI_COUNT, page).execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null || result.results.size() == 0) {
            return null;
        }
        if (result.error) {
            return null;
        } else {
            MeiziDAO.bulkInsert(result.results);
            return result.results;
        }
    }

    public List<Meizi> getMore() {
        GankApi.Result<List<Meizi>> result = null;
        String[] date;
        date = MeiziDAO.getLatestPicDate();
        try {
            result = gankApi.before(REQUEST_MEIZI_COUNT, date[0], date[1], date[2]).execute().body();
            MeiziDAO.bulkInsert(result.results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.results;
    }
}
