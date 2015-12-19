package com.spark.meizi.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.data.model.Meizi;
import com.spark.meizi.data.net.SparkRetrofit;
import com.spark.meizi.ui.adapter.MeiziRecyclerAdapter;
import com.spark.meizi.ui.adapter.OnMeiziClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnMeiziClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_main)
    RecyclerView mainRecyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.srl_main)
    SwipeRefreshLayout swipeRefreshLayout;
    private Realm realm;
    private MeiziRecyclerAdapter meiziAdapter;
    public List<Meizi> meizis;
    private static int page = 2;
    //we can't get RealmObject's data in a different thread
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        meizis = new ArrayList<>();
        initRealm();
        loadDataFromDB();
        initRecyclerView();
        loadDataFromServer(LoadImageAsyncTask.GET_LATEST);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    private void initRecyclerView() {
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
//        RealmResults<Meizi> results = realm.where(Meizi.class)
//                .findAllSorted("publishedAt", false);
//        if (results.size() >= 10) {
//            meizis.addAll(results.subList(0, 10));
//        } else {
//            meizis.addAll(results);
//        }
        meizis = realm.where(Meizi.class)
                .findAllSorted("publishedAt", false);
        return meizis.size();
    }

    private void loadDataFromServer(int i) {
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
        Meizi meizi = meizis.get(position);
        intent.putExtra("url", meizi.getUrl());
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, itemView, meizi.getUrl());
        startActivity(intent, optionsCompat.toBundle());
    }

    class LoadImageAsyncTask extends AsyncTask<Integer, Void, Integer> {
        public static final int GET_LATEST = 1;
        public static final int GET_MORE = 2;

        @Override
        protected Integer doInBackground(Integer... params) {
            SparkRetrofit sparkRetrofit = new SparkRetrofit(getApplicationContext());
            switch (params[0]) {
                case GET_LATEST: {
                    List<Meizi> temp = sparkRetrofit.getLatest(1);
                    if (temp != null) {
                        if (isFirst) {
                            meizis = temp;
                            isFirst = false;
                        } else {
                            meizis = mergeList(meizis, temp);
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
            super.onPostExecute(result);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            meiziAdapter.notifyDataSetChanged();
            meiziAdapter.setDataset(meizis);
        }
    }
}

