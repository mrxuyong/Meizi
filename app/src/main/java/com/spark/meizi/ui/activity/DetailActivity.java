package com.spark.meizi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.util.Log;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.ui.fragment.DetailFragment;
import com.spark.meizi.ui.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.pager)
    PhotoViewPager pager;
    private ArrayList<String> meiziUrls;
    private int index;
    private DetailPagerAdapter detailPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate invoked!");
        super.onCreate(savedInstanceState);
        supportStartPostponedEnterTransition();
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        meiziUrls = getIntent().getStringArrayListExtra("meiziUrls");
        index = getIntent().getIntExtra("index", 0);
        Log.d(getClass().getName(), "index url " + index + ": " + meiziUrls.get(index));
        detailPagerAdapter = new DetailPagerAdapter();
        pager.setAdapter(detailPagerAdapter);
        pager.setCurrentItem(index);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                DetailFragment fragment = (DetailFragment) detailPagerAdapter.instantiateItem(pager, pager.getCurrentItem());
                sharedElements.clear();
                Log.d(getClass().getName(),"SharedElements width "+fragment.getImage().getWidth());
                sharedElements.put(meiziUrls.get(pager.getCurrentItem()), fragment.getImage());
            }
        });
    }

    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("index", pager.getCurrentItem());
        setResult(RESULT_OK, data);

        super.supportFinishAfterTransition();
    }

    private class DetailPagerAdapter extends FragmentStatePagerAdapter {
        public DetailPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(getClass().getName(), "getItem position " + position);
            return DetailFragment.newInstance(meiziUrls.get(position));
        }

        @Override
        public int getCount() {
            return meiziUrls.size();
        }
    }


}
