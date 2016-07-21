package com.spark.meizi.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Spark on 2016/7/10 23:20:19.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements
        IView, View.OnClickListener {

    protected IViewLifeCircleHelper mILifeCircleHelper;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onBefore(savedInstanceState);
        super.onCreate(savedInstanceState);
        View containerView = getLayoutInflater().inflate(getContentViewId(), getViewGroupRoot());
        setContentView(containerView);
        initPresenter();
        initData();
        initSubViews(containerView);
        doAfter();
    }

    private void initPresenter() {
        mPresenter = getPresenter();
    }

    /**
     * new a Presenter
     *
     * @return Presenter
     */
    protected T getPresenter() {
        return null;
    }

    @Override
    public void onBefore(Bundle savedInstanceState) {

    }

    @Override
    public abstract int getContentViewId();

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public void initSubViews(View view) {
        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onCreate();
    }

    @Override
    public void initData() {

    }

    @Override
    public void doAfter() {

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachViewRef();

        if (null != mILifeCircleHelper)
            mILifeCircleHelper.onDestory();
    }

    public void setmILifeCircleHelper(IViewLifeCircleHelper mILifeCircleHelper) {
        this.mILifeCircleHelper = mILifeCircleHelper;
    }

    @Override
    public void onClick(View view) {

    }
}
