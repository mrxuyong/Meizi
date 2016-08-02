package com.spark.meizi.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.spark.meizi.base.listener.BaseRecyclerOnScrollListener;

/**
 * Created by Spark on 8/2/2016
 * Github: github/SparkYuan
 */
public class BaseRecyclerView extends RecyclerView {

    BaseRecyclerOnScrollListener mOnScrollListener;
    public static final int LIN_NUM = 2;

    public BaseRecyclerView(Context context) {
        super(context);
        init();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (mOnScrollListener != null) {
            addOnScrollListener(mOnScrollListener);
        }
    }

    public void setmOnScrollListener(BaseRecyclerOnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }

    public void onRefreshComplete() {
        mOnScrollListener.clearPage();
    }

    public void onLoadingError() {
        mOnScrollListener.loadingError();
    }

}
