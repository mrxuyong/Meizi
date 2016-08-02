package com.spark.meizi.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by SparkYuan on 7/31/2016.
 * Github: github.com/SparkYuan
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        initView();
    }

    public abstract void initView();
}
