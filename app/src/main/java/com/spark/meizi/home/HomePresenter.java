package com.spark.meizi.home;

import com.spark.meizi.MeiziContext;
import com.spark.meizi.R;
import com.spark.meizi.base.BaseFragment;
import com.spark.meizi.base.BaseFragmentPagerAdapter;
import com.spark.meizi.base.BasePresenter;
import com.spark.meizi.meizi.MeiziFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SparkYuan on 7/26/2016.
 * Github: github.com/SparkYuan
 */
public class HomePresenter extends BasePresenter<IHome> {

    public HomePresenter(IHome view) {
        super(view);
    }

    public void initAdapterData(BaseFragmentPagerAdapter<BaseFragment> adapter) {
        List<String> titles = new ArrayList<>();
        titles.add(MeiziContext.getInstance().getContext().getString(R.string.meizi));
//        titles.add(MeiziContext.getInstance().getContext().getString(R.string.Android));
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MeiziFragment());
//        fragments.add(new MeiziFragment());
        adapter.setData(titles, fragments);
    }
}

interface IHome {

}


