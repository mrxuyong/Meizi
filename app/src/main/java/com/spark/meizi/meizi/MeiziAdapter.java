package com.spark.meizi.meizi;

import android.view.View;
import android.widget.ImageView;

import com.spark.meizi.R;
import com.spark.meizi.base.listener.BaseRecyclerAdapter;
import com.spark.meizi.meizi.entity.Meizi;
import com.spark.meizi.utils.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Spark on 8/2/2016.
 * Github: github/SparkYuan
 */
public class MeiziAdapter extends BaseRecyclerAdapter<Meizi.ResultsBean, MeiziViewHolder> {

    @Override
    protected int getLayoutId() {
        return R.layout.meizi_rv_item;
    }

    @Override
    protected MeiziViewHolder createViewHolder(View view) {
        return new MeiziViewHolder(view);
    }

}

class MeiziViewHolder extends BaseRecyclerAdapter.BaseViewHolder<Meizi.ResultsBean> {

    @BindView(R.id.iv_item)
    ImageView itemImageView;

    public MeiziViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindData(Meizi.ResultsBean data) {
        ImageLoader.loadImage(data.getUrl(), itemImageView, itemView.getContext());
    }

    @OnClick(R.id.iv_item)
    public void onClick() {
    }
}