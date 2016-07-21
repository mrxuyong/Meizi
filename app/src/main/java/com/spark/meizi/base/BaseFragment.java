package com.spark.meizi.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Spark on 2016/7/10 23:21:24.
 */
public class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements IView, View.OnClickListener {

    protected IViewLifeCircleHelper mILifeCircle;
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        doAfter();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        mInflater = mInflater.cloneInContext(getContext());
        View containerView = mInflater.inflate(getContentViewId(), container, false);
        doInitPresenter();
        onBefore(savedInstanceState);
        __internal(containerView);
        initSubViews(containerView);
        return containerView;
    }

    private void doInitPresenter() {
        if (mPresenter == null)
            mPresenter = doGetPresenter();
           }

    protected T doGetPresenter() {
        return null;
    }

    private void __internal(View view) {
        if (null != mILifeCircle)
            mILifeCircle.onCreate();
    }

    @Override
    public void onBefore(Bundle savedInstanceState) {

    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void doAfter() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mILifeCircle)
            mILifeCircle.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mILifeCircle)
            mILifeCircle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mILifeCircle)
            mILifeCircle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mILifeCircle)
            mILifeCircle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachViewRef();
        }

        if (null != mILifeCircle)
            mILifeCircle.onDestory();
    }

    public void setmILifeCircle(IViewLifeCircleHelper mILifeCircle) {
        this.mILifeCircle = mILifeCircle;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mPresenter != null) {
            doLazyLoad();
        }
    }

    public void doLazyLoad() {

    }
}
