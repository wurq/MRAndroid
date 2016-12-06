package com.wurq.dex.mrandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by wurongqiu on 16/12/6.
 */
public class MRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        //崩溃捕获以及日志输出配置
        if (BuildConfig.DEBUG) {
            CrashHandlerL.getInstance().init(context);//debug状态下使用本地崩溃捕获

        } else {
            // crash

        }


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
