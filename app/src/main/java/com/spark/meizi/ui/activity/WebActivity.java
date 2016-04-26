package com.spark.meizi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.spark.meizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    private String url = "url";
    private String title = "title";
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
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
}