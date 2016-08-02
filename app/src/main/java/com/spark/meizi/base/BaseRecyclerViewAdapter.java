package com.spark.meizi.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SparkYuan on 6/9/2016 22:53.
 * Github: github.com/SparkYuan
 */
public abstract class BaseRecyclerViewAdapter<D, VH extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<VH> implements View.OnClickListener {


    private static final int TYPE_HEADER_START = 1000;
    private static final int TYPE_FOOTER = 2000;
    private static final int TYPE_EMPTY_START = 3000;

    private List<View> mHeaderViews = new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;
    protected List<D> mDatas;

    public BaseRecyclerViewAdapter() {
        super();
        setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_START) {
            View v = mHeaderView;
            return (VH) new HeaderHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = mFooterView;
            return (VH) new FooterHolder(v);
        } else {
            return (VH) createViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemCount() {
        int size = getListItemCount();
        if (size == 0 && null != mEmptyView) {
            return 1;
        } else {
            return getHeadViewSize() + size + getFooterViewSize();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int size = getListItemCount();
        if (size == 0 && null != mEmptyView) {
            return TYPE_EMPTY_START;
        } else if (position < getHeadViewSize()) {
            mHeaderView = mHeaderViews.get(position);
            return TYPE_HEADER_START;
        } else if (position >= getHeadViewSize() + getListItemCount()) {
            return TYPE_FOOTER;
        }
        return getListType(getListItemPosition(position));
    }

    public int getListType(int listItemPosition) {
        return 0;
    }

    public void setData(List<D> mDatas) {
        lastCount = 0;
        this.mDatas = mDatas;
    }

    private int lastCount = 0;

    public void notifyItemChanged() {
        if (mDatas.size() > lastCount) {
            if (mDatas.size() < 10) {
                notifyDataSetChanged();
            } else {
                notifyItemRangeChanged(lastCount + getHeadViewSize(), mDatas.size() - lastCount);
            }
        }
        lastCount = mDatas.size();
    }

    public D getItemAtPosition(int pos) {
        if (pos < getHeadViewSize())
            return null;
        if (pos >= getHeadViewSize() + getListItemCount())
            return null;
        return mDatas.get(getListItemPosition(pos));
    }

    public int getHeadViewSize() {
        return mHeaderViews.size();
    }

    public int getFooterViewSize() {
        return mFooterView == null ? 0 : 1;
    }

    public int getListItemCount() {
        return (null == mDatas) ? 0 : mDatas.size();
    }

    public int getListItemPosition(int pos) {
        return pos - getHeadViewSize();
    }

    public abstract void binderItemHolder(VH holder, int position);

    public VH createViewHolder(ViewGroup parent, int viewType){
        //TODO 抽象CreateViewHolder部分
    }

    static class HeaderHolder extends BaseRecyclerViewHolder<Object> {

        public HeaderHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void doBindData(Object content) {
        }
    }

    //add a header to the adapter
    public void addHeader(View header) {
        mHeaderViews.add(header);
        notifyItemInserted(mHeaderViews.size());
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        if (mHeaderViews.contains(header)) {
            notifyItemRemoved(mHeaderViews.indexOf(header));
            mHeaderViews.remove(header);
        }
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        mFooterView = footer;
        notifyItemInserted(getHeadViewSize() + getListItemCount());
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        notifyItemRemoved(getHeadViewSize() + getListItemCount());
        mFooterView = null;
    }

    static class FooterHolder extends BaseRecycleViewHolder<Object> {
        public FooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void doBindData(Object content) {
        }
    }

    static class EmptyHolder extends BaseRecycleViewHolder<Object> {
        public EmptyHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void doBindData(Object content) {
        }
    }

    protected AdapterItemClickListener<D> adapterItemClickListener;

    public void setAdapterItemClickListener(AdapterItemClickListener<D> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public interface AdapterItemClickListener<D> {
        void onViewClick(View view, D entity, int position);
    }
}
