package com.spark.meizi.net;

import java.io.IOException;
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
 * Created by Spark on 7/24/2016 22:08.
 */
public class RequestFactory {
    /**
     * @param clazz     API 类型
     * @param baseUrl   基础URL
     * @param headerMap Http头信息
     * @param <T>
     * @return
     */
    public static <T> T createRetrofitService(final Class clazz, String baseUrl, final Map<String, String> headerMap) {

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

                        Response response = chain.proceed(request);

                        return response;
                    }
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();


        Retrofit client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = (T) client.create(clazz);
        return service;
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
}
