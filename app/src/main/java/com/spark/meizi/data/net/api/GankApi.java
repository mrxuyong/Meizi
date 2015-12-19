package com.spark.meizi.data.net.api;

import com.spark.meizi.data.model.Meizi;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Spark on 12/13/2015.
 */
public interface GankApi {

    @GET("data/%E7%A6%8F%E5%88%A9/{count}/{page}")
    Call<Result<List<Meizi>>> latest(@Path("count") int count,@Path("page") int page);

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

    class Result<T> {
        public boolean error;
        public T results;
    }

    class Article {
//        public List<Meizi> meizis;
    }

}
