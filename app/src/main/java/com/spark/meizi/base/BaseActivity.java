package com.spark.meizi.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by SparkYuan on 2016/7/10 23:20:19.
 * Github: github.com/SparkYuan
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements
        IView{

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View containerView = getLayoutInflater().inflate(getContentViewId(), getViewGroupRoot());
        setContentView(containerView);
        ButterKnife.bind(this);
        initPresenter();
        initSubViews(containerView);
        initData();
    }

    private void initPresenter() {
        mPresenter = getPresenter();
    }

    protected T getPresenter() {
        return null;
    }

    @Override
    public abstract int getContentViewId();

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public void initSubViews(View view) {
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachViewRef();
    }

}
