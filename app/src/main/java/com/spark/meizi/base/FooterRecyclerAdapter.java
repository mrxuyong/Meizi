package com.spark.meizi.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spark.meizi.base.listener.BaseRecyclerAdapter;

/**
 * Created by SparkYuan on 8/2/2016 16:29.
 * Github: github.com/SparkYuan
 */
public class FooterRecyclerAdapter<D,VH extends BaseRecyclerAdapter.BaseViewHolder<D>>
        extends RecyclerView.Adapter {

    private static  String TAG = FooterRecyclerAdapter.class.getSimpleName();
    private static  int VIEW_TYPE_FOOTER = Integer.MAX_VALUE - 1;
    private boolean needFooter = false;
    private BaseRecyclerAdapter<D,VH> mWrapped;

    public FooterRecyclerAdapter(BaseRecyclerAdapter<D,VH> adapter) {
        mWrapped = adapter;
    }

    public BaseRecyclerAdapter getWrapped() {
        return mWrapped;
    }

    public void updateState() {
        notifyDataSetChanged();
    }

    @Override
    public void setHasStableIds( boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        mWrapped.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId( int position) {
        if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
            return position;
        } else {
            return mWrapped.getItemId(position);
        }
    }

    @Override
    public int getItemCount() {
        return mWrapped.getItemCount() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mWrapped.getItemCount()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return mWrapped.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return createFooterViewHolder(parent, viewType);
        } else {
            return mWrapped.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder,  int position) {
         int type = getItemViewType(position);
        if (type == VIEW_TYPE_FOOTER) {
            bindFooterViewHolder(holder, position);
        } else {
            mWrapped.onBindViewHolder((VH) holder, position);
        }
    }

    public int getFooterCount() {
        return needFooter ? 1 : 0;
    }

    private RecyclerView.ViewHolder createFooterViewHolder( ViewGroup parent,  int viewType) {
        ProgressBar view = new ProgressBar(parent.getContext());
        view.setIndeterminate(true);
        parent.addView(view);
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.setFullSpan(true);
        return new SimpleViewHolder(view);
    }

    private void bindFooterViewHolder( RecyclerView.ViewHolder holder,  int position) {
    }

    public void addFooter(){
        needFooter = true;
        notifyItemInserted(mWrapped.getItemCount());
    }

    public void removeFooter(){
        needFooter = false;
        notifyItemRemoved(mWrapped.getItemCount());
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder( View itemView) {
            super(itemView);
        }
    }
}