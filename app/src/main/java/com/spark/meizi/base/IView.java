package com.spark.meizi.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SparkYuan on 16/6/16.
 * Github: github.com/SparkYuan
 */
interface IView {

    ViewGroup getViewGroupRoot();

    int getContentViewId();

    void initSubViews(View view);

    void initData();
}
