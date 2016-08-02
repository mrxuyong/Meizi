package com.spark.meizi.net.api;

import com.spark.meizi.blog.AndroidBlog;
import com.spark.meizi.entity.Meizi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by Spark on 12/13/2015.
 */
public interface GankApi {

    @GET("data/%E7%A6%8F%E5%88%A9/{count}/{page}")
    Observable<Meizi> latest(@Path("count") int count, @Path("page") int page);

    @GET("data/Android/{count}/{page}")
    Observable<AndroidBlog> getAndroid(@Path("count") int count, @Path("page") int page);
}
