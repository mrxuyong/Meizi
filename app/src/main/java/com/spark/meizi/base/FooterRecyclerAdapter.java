package com.spark.meizi.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spark.meizi.base.listener.BaseRecyclerAdapter;

/**
 * Created by Spark on 8/2/2016 16:29.
 */
public class FooterRecyclerAdapter extends RecyclerView.Adapter {

    private static  String TAG = FooterRecyclerAdapter.class.getSimpleName();
    private static  int VIEW_TYPE_FOOTER = Integer.MAX_VALUE - 1;
    private boolean needFooter = false;
    private BaseRecyclerAdapter mWrapped;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeRemoved( int positionStart,  int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            Log.v(TAG, "onItemRangeRemoved() start=" + positionStart + " count=" + itemCount);
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved( int fromPosition,  int toPosition,  int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            Log.v(TAG, "onItemRangeMoved() start=" + fromPosition + " count=" + itemCount);
            notifyItemRangeChanged(fromPosition, itemCount);
        }

        @Override
        public void onItemRangeInserted( int positionStart,  int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            Log.v(TAG, "onItemRangeInserted() start=" + positionStart + " count=" + itemCount);
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged( int positionStart,  int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            Log.v(TAG, "onItemRangeChanged() start=" + positionStart + " count=" + itemCount);
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }
    };

    public FooterRecyclerAdapter(BaseRecyclerAdapter adapter) {
        mWrapped = adapter;
        mWrapped.registerAdapterDataObserver(mAdapterDataObserver);
    }

    public RecyclerView.Adapter getWrapped() {
        return mWrapped;
    }

    public void updateState() {
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mWrapped.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView( RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mWrapped.onDetachedFromRecyclerView(recyclerView);
        mWrapped.unregisterAdapterDataObserver(mAdapterDataObserver);
    }

    @Override
    public void onViewAttachedToWindow( RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mWrapped.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow( RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        mWrapped.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled( RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        mWrapped.onViewRecycled(holder);
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
            mWrapped.onBindViewHolder((BaseRecyclerAdapter.BaseViewHolder) holder, position);
        }
    }

    private int getFooterCount() {
        return needFooter ? 1 : 0;
    }

    private RecyclerView.ViewHolder createFooterViewHolder( ViewGroup parent,  int viewType) {
        ProgressBar view = new ProgressBar(parent.getContext());
        view.setIndeterminate(true);
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