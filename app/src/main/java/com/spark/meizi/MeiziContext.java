package com.spark.meizi;

import android.content.Context;

/**
 * Created by Spark on 6/9/2016 22:55.
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
