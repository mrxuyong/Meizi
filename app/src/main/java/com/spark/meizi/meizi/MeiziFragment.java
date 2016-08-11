package com.spark.meizi.meizi;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.base.BaseFragment;
import com.spark.meizi.base.BaseRecyclerView;
import com.spark.meizi.base.FooterRecyclerAdapter;
import com.spark.meizi.base.listener.BaseRecyclerOnScrollListener;
import com.spark.meizi.meizi.entity.Meizi;

import java.util.List;

import butterknife.BindView;


public class MeiziFragment extends BaseFragment<MeiziPresenter> implements IMeizi,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_meizi)
    BaseRecyclerView recyclerView;
    @BindView(R.id.srl_meizi)
    SwipeRefreshLayout swipeRefreshLayout;

    private FooterRecyclerAdapter<Meizi.ResultsBean, MeiziAdapter.MeiziViewHolder> meiziAdapter;
    private LinearLayoutManager linearLayoutManager = null;
    private StaggeredGridLayoutManager staggeredGridLayoutManager = null;

    BaseRecyclerOnScrollListener scrollListener;

    public MeiziFragment() {
    }

    public static MeiziFragment newInstance() {
        return new MeiziFragment();
    }

    @Override
    protected MeiziPresenter getPresenter() {
        return new MeiziPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_meizi;
    }

    @Override
    public void initSubViews(View view) {
        super.initSubViews(view);

        //TODO SGML item move when scrolling
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        linearLayoutManager = new LinearLayoutManager(getContext());
        meiziAdapter = new FooterRecyclerAdapter<>(new MeiziAdapter());
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(meiziAdapter);
        scrollListener = new BaseRecyclerOnScrollListener(staggeredGridLayoutManager) {
            //        scrollListener = new BaseRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.requestMeizi(currentPage);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        List<Meizi.ResultsBean> list = mPresenter.loadDataFromDB();
        if (!list.isEmpty()) {
            meiziAdapter.getWrapped().setData(list);
            meiziAdapter.getWrapped().notifyDataSetChanged();
        } else {
            mPresenter.requestMeizi(1);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.getRealm().close();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        List list = meiziAdapter.getWrapped().getData();
        if (list != null) {
            list.clear();
            meiziAdapter.notifyDataSetChanged();
        }
        scrollListener.clearPage();
        scrollListener.onLoadMore(1);
    }

    @Override
    public FooterRecyclerAdapter getAdapter() {
        return meiziAdapter;
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }
}
