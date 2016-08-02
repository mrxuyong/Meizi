package com.spark.meizi.blog;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.spark.meizi.R;
import com.spark.meizi.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    private String url = "url";
    private String title = "title";
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void initSubViews(View view) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        url = getIntent().getStringExtra(url);
        title = getIntent().getStringExtra("title");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.loadUrl(url);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web;
    }
}