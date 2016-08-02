package com.spark.meizi.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Spark on 2016/7/10 23:21:12.
 */
public class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public BasePresenter(T view) {
        this.mViewRef = new WeakReference<>(view);
    }

    protected T getViewRef() {
        if (null != mViewRef)
            return mViewRef.get();
        return null;
    }

    public boolean isViewRefAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachViewRef() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
