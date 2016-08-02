package com.spark.meizi.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by Spark on 2016/7/10.
 * Github: github/SparkYuan
 */
public class BaseFragment<T extends BasePresenter> extends Fragment implements IView{

    protected T mPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        mInflater = mInflater.cloneInContext(getContext());
        View containerView = mInflater.inflate(getContentViewId(), container, false);
        initPresenter();
        initSubViews(containerView);
        return containerView;
    }

    private void initPresenter() {
        if (mPresenter == null)
            mPresenter = getPresenter();
    }

    protected T getPresenter() {
        return null;
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachViewRef();
        }
    }

}
