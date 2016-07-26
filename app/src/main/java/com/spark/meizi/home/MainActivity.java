package com.spark.meizi.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.about.AboutActivity;
import com.spark.meizi.base.BaseActivity;
import com.spark.meizi.base.BaseFragment;
import com.spark.meizi.base.BaseFragmentAdapter;
import com.spark.meizi.utils.ActivityUtil;
import com.umeng.update.UmengUpdateAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> {

    public static int NUM_PAGES = 2;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initSubViews(View view) {
        super.initSubViews(view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        PagerAdapter pagerAdapter = new MainSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setUpdateOnlyWifi(true);
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            ActivityUtil.startActivity(this, AboutActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MainSlidePagerAdapter extends BaseFragmentAdapter<BaseFragment> {
        public MainSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
    }
}

