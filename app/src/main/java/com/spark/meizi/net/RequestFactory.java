package com.spark.meizi.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spark.meizi.MeiziContext;
import com.spark.meizi.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SparkYuan on 7/24/2016 22:08.
 * Github: github.com/SparkYuan
 */
public class RequestFactory {

    public static String getBaseUrl() {
        return MeiziContext.getInstance().getContext().getString(R.string.base_url);
    }

    public static <T> T createApi(final Class<T> clazz) {
        return createApi(clazz, getBaseUrl());
    }

    public static <T> T createApi(final Class<T> clazz, Map<String, String> headerMap) {
        return createRetrofitService(clazz, getBaseUrl(), headerMap);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        String token = "";
        if (TextUtils.isEmpty(token)) {
            return createApi(clazz, baseUrl, null);
        } else {
            Map<String, String> headerMap = new HashMap<>();
//            headerMap.put("Token", token);
            return createApi(clazz, baseUrl, headerMap);
        }
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl, Map<String, String> headerMap) {
        return createRetrofitService(clazz, baseUrl, headerMap);
    }

    /**
     * @param clazz     API 类型
     * @param baseUrl   基础URL
     * @param headerMap Http头信息
     * @param <T>   Service
     * @return Service
     */
    public static <T> T createRetrofitService(final Class<T> clazz, String baseUrl, final Map<String, String> headerMap){

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder;

                        if (headerMap == null) {
                            requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());
                        } else {
                            requestBuilder = original.newBuilder()
                                    .headers(addHeaders(headerMap))
                                    .method(original.method(), original.body());
                        }
                        Request request = requestBuilder.build();

                        return chain.proceed(request);
                    }
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return client.create(clazz);
    }

    private static Headers addHeaders(Map<String, String> header) {
        Headers.Builder builder = new Headers.Builder();
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();
}
