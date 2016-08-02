package com.spark.meizi.base.listener;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.spark.meizi.base.FooterRecyclerAdapter;
import com.spark.meizi.base.BaseRecyclerView;


public abstract class BaseRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    int visibleItemCount, totalItemCount;
    int[] staggerLastCompletelyVisibleItemPosition = new int[BaseRecyclerView.LIN_NUM];
    int lastCompletelyVisibleItemPosition = -1;

    private int currentPage = 1;

    private RecyclerView.LayoutManager layoutManager;

    public BaseRecyclerOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(staggerLastCompletelyVisibleItemPosition);
            lastCompletelyVisibleItemPosition = staggerLastCompletelyVisibleItemPosition[BaseRecyclerView.LIN_NUM - 1];
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (visibleItemCount > 0)
                && (lastCompletelyVisibleItemPosition >= totalItemCount - 1)) {
            currentPage++;
            ((FooterRecyclerAdapter) recyclerView.getAdapter()).addFooter();
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);

    public void clearPage() {
        currentPage = 1;
        loading = false;
        previousTotal = 0;
    }

    public void loadingError() {
        loading = false;
        currentPage--;
        previousTotal = 0;
    }

}