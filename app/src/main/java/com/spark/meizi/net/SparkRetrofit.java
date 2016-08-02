package com.spark.meizi.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spark.meizi.R;
import com.spark.meizi.blog.AndroidBlog;
import com.spark.meizi.blog.AndroidBlogDAO;
import com.spark.meizi.entity.Meizi;
import com.spark.meizi.meizi.MeiziDAO;
import com.spark.meizi.net.api.GankApi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Spark on 12/13/2015.
 */
public class SparkRetrofit {
    private static final String TAG = "SparkRetrofit";
    private Retrofit retrofit;
    private GankApi gankApi;
    private Context context;
    public static final int REQUEST_MEIZI_COUNT = 10;
    public static final int REQUEST_BLOG_COUNT = 10;

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

    public SparkRetrofit(Context context) {
        this.context = context;
        OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
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
            Bitmap bitmap;
            for (Meizi meizi : result.results) {
                try {
                    bitmap = Glide.with(context)
                            .load(meizi.getUrl())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    meizi.setWidth(bitmap.getWidth());
                    meizi.setHeight(bitmap.getHeight());
                } else {
                    meizi.setWidth(0);
                    meizi.setHeight(0);
                }
            }
            MeiziDAO.bulkInsert(result.results);
            return result.results;
        }
    }

    public List<AndroidBlog> getLatestAndroid(int page) {
        GankApi.Result<List<AndroidBlog>> result = null;
        try {
            result = gankApi.getAndroid(REQUEST_BLOG_COUNT, page).execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null || result.results.size() == 0) {
            return null;
        }
        if (result.error) {
            return null;
        } else {
            AndroidBlogDAO.bulkInsert(result.results);
            return result.results;
        }
    }
}
