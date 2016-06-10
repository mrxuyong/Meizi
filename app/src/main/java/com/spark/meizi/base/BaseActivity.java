package com.spark.meizi.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Spark on 12/13/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected void log(String s) {
        Log.d(getClass().getName(), s);
    }
    protected void loge(String s) {
        Log.e(getClass().getName(), s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
