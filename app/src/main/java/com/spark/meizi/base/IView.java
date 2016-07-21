package com.spark.meizi.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Spark on 16/6/16.
 */
interface IView {

    /***
     * 创建事前处理
     *
     * @param savedInstanceState savedInstanceState
     */
    void onBefore(Bundle savedInstanceState);

    /***
     * 视图组
     *
     * @return
     */
    ViewGroup getViewGroupRoot();

    /***
     * 获取创建视图
     *
     * @return
     */
    int getContentViewId();

    /***
     * 初始化子视图，在此做控件、事件的绑定操作
     *
     * @param view
     */
    void initSubViews(View view);

    /***
     * 对视图中的控件进行数据初始化
     */
    void initData();

    /***
     * 做事件的最终处理
     */
    void doAfter();

}
