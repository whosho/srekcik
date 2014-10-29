package com.placeholder.kickers.application;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by Grzegorz.Barski on 2014-10-14.
 */
public class KickersApplication extends Application {

    static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new BaseModule(getApplicationContext()));
    }

    public static void inject(Object container) {
        objectGraph.inject(container);
    }

}
