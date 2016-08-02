package com.spark.meizi.blog;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spark.meizi.R;
import com.spark.meizi.base.BaseFragment;
import com.spark.meizi.net.SparkRetrofit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class AndroidBlogFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static int page = 2;
    public List<AndroidBlog> blogs;
    @BindView(R.id.rv_android_blog)
    RecyclerView recyclerView;
    @BindView(R.id.srl_android_blog)
    SwipeRefreshLayout swipeRefreshLayout;
    SparkRetrofit sparkRetrofit;
    private Realm realm;
    private boolean isFirst = true;
    private LinearLayoutManager linearLayoutManager;
    private AndroidBlogRecAdapter androidBlogRecAdapter;

    public AndroidBlogFragment() {
        // Required empty public constructor
    }

    public static AndroidBlogFragment newInstance() {
        return new AndroidBlogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android_blog, container, false);
        ButterKnife.bind(this, view);
        blogs = new ArrayList<>();
        sparkRetrofit = new SparkRetrofit(getContext());
        swipeRefreshLayout.setOnRefreshListener(this);
        initRealm();
        loadDataFromDB();
        initRecyclerView();
        loadDataFromServer(LoadAndroidBlogAsyTask.GET_LATEST);
        return view;
    }

    @Override
    public void onRefresh() {
        loadDataFromServer(LoadAndroidBlogAsyTask.GET_LATEST);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        androidBlogRecAdapter = new AndroidBlogRecAdapter(blogs,getContext());
        recyclerView.setAdapter(androidBlogRecAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int maxPos = linearLayoutManager.findLastVisibleItemPosition();
                    if (maxPos + 1 >= androidBlogRecAdapter.getItemCount()) {

                        loadDataFromServer(LoadAndroidBlogAsyTask.GET_MORE);
                    }
                }
            }
        });
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    private int loadDataFromDB() {
        RealmResults<AndroidBlog> results = realm.where(AndroidBlog.class)
                .findAllSorted("publishedAt", false);
        blogs.clear();
        blogs.addAll(results);
        return blogs.size();
    }

    private void loadDataFromServer(int i) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        new LoadAndroidBlogAsyTask().execute(i);
    }

    private List<AndroidBlog> mergeList(List<AndroidBlog> blogs, List<AndroidBlog> netBlogs) {
        AndroidBlog lastInNetBlgs = netBlogs.get(netBlogs.size() - 1);

        // when netBlogs is earlier than the first item in blogs, the blogs is too old
        // so return netBlogs
        for (int i = 0; i < blogs.size(); i++) {
            if (lastInNetBlgs.getPublishedAt().equals(blogs.get(i).getPublishedAt()) && i != blogs.size() - 1) { //in case two lists are same
                netBlogs.addAll(blogs.subList(i + 1, blogs.size() - 1));
            }
        }
        return netBlogs;
    }

    class LoadAndroidBlogAsyTask extends AsyncTask<Integer, Void, Integer> {

        public static final int GET_LATEST = 1;
        public static final int GET_MORE = 2;
        public static final int FIR_PAGE = 1;
        private static final int ERROR = -1;
        private boolean needMerge = false;
        private List<AndroidBlog> temp;

        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case GET_LATEST: {
                    temp = sparkRetrofit.getLatestAndroid(FIR_PAGE);
                    if (temp != null) {
                        if (isFirst) {
                            blogs.clear();
                            blogs.addAll(temp);
                            isFirst = false;
                        } else {
                            needMerge = true;
                        }
                        return GET_LATEST;
                    } else return ERROR;
                }
                case GET_MORE: {
                    temp = sparkRetrofit.getLatestAndroid(page);
                    if (temp != null) {
                        blogs.addAll(temp);
                        page++;
                        return GET_MORE;
                    } else return ERROR;
                }
                default:
                    return ERROR;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (needMerge) {
                mergeList(blogs, temp);
                needMerge = false;
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            androidBlogRecAdapter.notifyDataSetChanged();
        }

    }

}
