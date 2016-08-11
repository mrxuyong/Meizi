package com.spark.meizi.meizi.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.spark.meizi.R;
import com.spark.meizi.base.BaseFragment;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.utils.ImageLoader;

import butterknife.BindView;

/**
 * Created by SparkYuan on 8/11/2016.
 * Github: github.com/SparkYuan
 */
public class DetailFragment extends BaseFragment {
    @BindView(R.id.iv_meizi)
    ImageView meiziImageView;
    private String url;

    public DetailFragment() {
        super();
    }

    public static DetailFragment newInstance(Meizi.ResultsBean meizi) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", meizi.getUrl());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initSubViews(View view) {
        super.initSubViews(view);
        ImageLoader.loadImage(getArguments().getString("url"),
                meiziImageView, getContext());
    }

}
