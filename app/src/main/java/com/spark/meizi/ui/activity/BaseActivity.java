package com.spark.meizi.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Spark on 12/13/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected void log(String s) {
        Log.d(getClass().getName(), s);
    }
}
