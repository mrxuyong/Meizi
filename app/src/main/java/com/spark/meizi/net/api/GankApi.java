package com.spark.meizi.net.api;


import com.spark.meizi.meizi.entity.Meizi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by SparkYuan on 12/13/2015.
 * Github: github.com/SparkYuan
 */
public interface GankApi {

    @GET("data/%E7%A6%8F%E5%88%A9/{count}/{page}")
    Observable<Meizi> latest(@Path("count") int count, @Path("page") int page);
}
