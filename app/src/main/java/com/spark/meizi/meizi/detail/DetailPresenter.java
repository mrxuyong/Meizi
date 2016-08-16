package com.spark.meizi.meizi.detail;

import android.support.v4.view.ViewPager;

import com.spark.meizi.base.BaseFragmentPagerAdapter;
import com.spark.meizi.base.BasePresenter;
import com.spark.meizi.meizi.entity.Meizi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SparkYuan on 8/11/2016.
 * Github: github.com/SparkYuan
 */
public class DetailPresenter extends BasePresenter<IDetail> {

    public DetailPresenter(IDetail view) {
        super(view);
    }

    public void initData(List<Meizi.ResultsBean> meizis,int index) {
        List<DetailFragment> list = new ArrayList<>();
        for (Meizi.ResultsBean result : meizis) {
            list.add(DetailFragment.newInstance(result));
        }
        getViewRef().getAdapter().setData(list);
        getViewRef().getViewPager().setCurrentItem(index);
    }
}

interface IDetail {
    BaseFragmentPagerAdapter getAdapter();
    ViewPager getViewPager();
}
