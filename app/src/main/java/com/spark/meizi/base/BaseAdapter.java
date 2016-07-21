package com.spark.meizi.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spark.meizi.utils.NoDoubleClickUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spark on 6/9/2016 22:53.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_FOOTER = 2;


    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    /**
     * @param context
     * @param data
     */
    public BaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mDatas = data;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * @param context
     */
    public BaseAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = createItemView(mInflater, parent, viewType);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return createHolder(v, viewType);
    }

    /**
     * 创建View holder
     *
     * @param v
     * @param viewType
     * @return
     */
    protected abstract RecyclerView.ViewHolder createHolder(View v, int viewType);

    /***
     * 创建视图
     *
     * @param mInflater
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View createItemView(LayoutInflater mInflater, ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (isHeaderView(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterView(position)) {
            viewType = TYPE_FOOTER;
        } else {
            viewType = TYPE_ITEM;
        }
        return viewType;
    }

    public boolean isHeaderView(int position) {
        return position == 0;
    }

    public boolean isFooterView(int position) {
        return position == getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 向后一个集合数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     */
    public void removeData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (NoDoubleClickUtil.isFastDoubleClick()) {
            return;
        }
        if (clickListener != null) {
            clickListener.onItemClick(v, this, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null) {
            longClickListener.onItemLongClick(v, this, (Integer) v.getTag());
        }
        return true;
    }

    /**
     * 设置item点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * 设置item长按事件
     *
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, BaseAdapter adapter, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, BaseAdapter adapter, int position);
    }

}
