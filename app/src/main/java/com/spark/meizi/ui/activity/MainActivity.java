package com.spark.meizi.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.spark.meizi.R;
import com.spark.meizi.data.model.Meizi;
import com.spark.meizi.data.net.SparkRetrofit;
import com.spark.meizi.ui.adapter.MeiziRecyclerAdapter;
import com.spark.meizi.ui.adapter.OnMeiziClickListener;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnMeiziClickListener {

    private static int page = 2;
    public List<Meizi> meizis;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_main)
    RecyclerView mainRecyclerView;
    @Bind(R.id.srl_main)
    SwipeRefreshLayout swipeRefreshLayout;
    SparkRetrofit sparkRetrofit;
    private Realm realm;
    private MeiziRecyclerAdapter meiziAdapter;
    //we can't get RealmObject's data in a different thread
    private boolean isFirst = true;
    private Bundle reenterState;
    private StaggeredGridLayoutManager staggeredGridLayoutManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        ButterKnife.bind(this);
        initToolbar();
        meizis = new ArrayList<>();
        sparkRetrofit = new SparkRetrofit(getApplicationContext());
        initRealm();
        loadDataFromDB();
        initRecyclerView();
        loadDataFromServer(LoadImageAsyncTask.GET_LATEST);

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (reenterState != null) {
                    int i = reenterState.getInt("index", 0);
                    log("reenter from " + i);
                    sharedElements.clear();
                    sharedElements.put(meizis.get(i).getUrl(), staggeredGridLayoutManager.findViewByPosition(i));
                    reenterState = null;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    private void initRecyclerView() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        meiziAdapter = new MeiziRecyclerAdapter(getApplicationContext(), meizis);
        meiziAdapter.setOnMeiziClickListener(this);
        mainRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainRecyclerView.setAdapter(meiziAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int[] maxPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                    int position = Math.max(maxPositions[0], maxPositions[1]);
                    if (position + 1 >= meiziAdapter.getItemCount()) {
                        loadDataFromServer(LoadImageAsyncTask.GET_MORE);
                    }
                }
            }
        });
    }

    private int loadDataFromDB() {
        RealmResults<Meizi> results = realm.where(Meizi.class)
                .findAllSorted("publishedAt", false);
        meizis.clear();
        meizis.addAll(results);
        return meizis.size();
    }

    private void loadDataFromServer(int i) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        new LoadImageAsyncTask().execute(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onRefresh() {
        loadDataFromServer(LoadImageAsyncTask.GET_LATEST);
    }

    private List<Meizi> mergeList(List<Meizi> mzs, List<Meizi> netMzs) {
        Meizi lastInNetMzs = netMzs.get(netMzs.size() - 1);

        // when lastInNetMzs is earlier than the first item in mzx, we think the mzs is too old
        // so return netMzs
        for (int i = 0; i < mzs.size(); i++) {
            if (lastInNetMzs.getPublishedAt().equals(mzs.get(i).getPublishedAt()) && i != mzs.size() - 1) { //in case two lists are same
                netMzs.addAll(mzs.subList(i + 1, mzs.size() - 1));
            }
        }
        return netMzs;
    }

    @Override
    public void onMeiziClick(View itemView, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        ArrayList<String> meiziUrls = new ArrayList<>();
        for (Meizi meizi : meizis) {
            meiziUrls.add(meizi.getUrl());
        }
        intent.putStringArrayListExtra("meiziUrls", meiziUrls);
        intent.putExtra("index", position);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, itemView, meiziUrls.get(position));
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        reenterState = data.getExtras();
        int index = reenterState.getInt("index", 0);
        mainRecyclerView.smoothScrollToPosition(index);
        mainRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mainRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                supportStartPostponedEnterTransition();
                return true;
            }
        });
    }

    class LoadImageAsyncTask extends AsyncTask<Integer, Void, Integer> {
        public static final int GET_LATEST = 1;
        public static final int GET_MORE = 2;
        public static final int FIR_PAGE = 1;

        @Override
        protected Integer doInBackground(Integer... params) {

            switch (params[0]) {
                case GET_LATEST: {
                    loge("Before temp");
                    List<Meizi> temp = sparkRetrofit.getLatest(FIR_PAGE);
                    loge("After temp");
                    if (temp != null) {
                        if (isFirst) {
                            meizis.clear();
                            meizis.addAll(temp);
                            isFirst = false;
                        } else {
                            mergeList(meizis, temp);
                        }
                        return GET_LATEST;
                    } else return -1;
                }
                case GET_MORE: {
                    List<Meizi> temp = sparkRetrofit.getLatest(page);
                    if (temp != null) {
                        meizis.addAll(temp);
                        page++;
                        return GET_MORE;
                    } else return -1;
                }
                default:
                    return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            meiziAdapter.notifyDataSetChanged();
        }
    }
}

