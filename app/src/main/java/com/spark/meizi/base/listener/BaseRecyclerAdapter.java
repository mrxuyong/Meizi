package com.spark.meizi.base.listener;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Spark on 8/2/2016.
 * Github: github/SparkYuan
 */
public abstract class BaseRecyclerAdapter<D, VH extends BaseRecyclerAdapter.BaseViewHolder<D>>
        extends RecyclerView.Adapter<VH> {

    List<D> data;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent);
        return createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    protected abstract int getLayoutId();

    protected abstract VH createViewHolder(View view);

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

    public abstract static class BaseViewHolder<D> extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void bindData(D data);
    }
}
