package com.spark.meizi.net.api;

import com.spark.meizi.blog.AndroidBlog;
import com.spark.meizi.meizi.Meizi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Spark on 12/13/2015.
 */
public interface GankApi {

    @GET("data/%E7%A6%8F%E5%88%A9/{count}/{page}")
    Call<Result<List<Meizi>>> latest(@Path("count") int count, @Path("page") int page);

    @GET("get/{count}/since/{year}/{month}/{day}")
    Call<Result<List<String>>> since(@Path("count") int count,
                                     @Path("year") String year,
                                     @Path("month") String month,
                                     @Path("day") String day);

    @GET("get/{count}/before/{year}/{month}/{day}")
    Call<Result<List<Meizi>>> before(@Path("count") int count,
                                     @Path("year") String year,
                                     @Path("month") String month,
                                     @Path("day") String day);

    @GET("data/Android/{count}/{page}")
    Call<Result<List<AndroidBlog>>> getAndroid(@Path("count") int count, @Path("page") int page);

    class Result<T> {
        public boolean error;
        public T results;
    }

    class Article {
//        public List<Meizi> meizis;
    }

}
