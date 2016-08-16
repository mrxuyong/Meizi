package com.spark.meizi;

import android.content.Context;

/**
 * Created by SparkYuan on 6/9/2016 22:55.
 * Github: github.com/SparkYuan
 */
public class MeiziContext {

    private Context context;

    private MeiziContext() {
    }

    private static class SingletonHolder {
        private static MeiziContext INSTANCE = new MeiziContext();
    }

    public static MeiziContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public MeiziContext init(Context context) {
        this.context = context;
        return this;
    }

    public Context getContext() {
        return context;
    }

}
