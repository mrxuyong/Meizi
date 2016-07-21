package com.spark.meizi.base;

/**
 * Created by Spark on 7/10/2016.
 */
public interface IViewLifeCircleHelper {

    void onStart();

    void onStop();

    void onCreate();

    void onRestart();

    void onResume();

    void onPause();

    void onDestory();
}
