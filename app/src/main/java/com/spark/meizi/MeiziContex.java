package com.spark.meizi;

import android.content.Context;

/**
 * Created by Spark on 6/9/2016 22:55.
 */
public class MeiziContex {

    private Context context;

    private MeiziContex() {
    }

    private static MeiziContex INSTANCE = new MeiziContex();

    public static MeiziContex getInstance() {
        return INSTANCE;
    }

    public MeiziContex init(Context context) {
        this.context = context;
        return this;
    }

}
