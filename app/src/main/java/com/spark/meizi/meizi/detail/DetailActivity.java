package com.spark.meizi.meizi.detail;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spark.meizi.R;
import com.spark.meizi.base.BaseActivity;
import com.spark.meizi.base.BaseFragmentPagerAdapter;
import com.spark.meizi.meizi.MeiziPresenter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.widget.PhotoViewPager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailActivity extends BaseActivity<DetailPresenter> implements IDetail {
    @BindView(R.id.vp_detail)
    PhotoViewPager detailViewpager;
    BaseFragmentPagerAdapter adapter;

    @Override
    public void initSubViews(View view) {
        super.initSubViews(view);
        hideStatusBar();
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        detailViewpager.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(MeiziPresenter.MEIZIS);
        int index = bundle.getInt(MeiziPresenter.INDEX);
        Type listType = new TypeToken<ArrayList<Meizi.ResultsBean>>() {
        }.getType();
        List<Meizi.ResultsBean> list = new Gson().fromJson(json, listType);
        if (list != null) {
            mPresenter.initData(list, index);
        }
    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) { // old method
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else { // Jellybean and up, new hotness
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @Override
    protected DetailPresenter getPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    public BaseFragmentPagerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public ViewPager getViewPager() {
        return detailViewpager;
    }
}
