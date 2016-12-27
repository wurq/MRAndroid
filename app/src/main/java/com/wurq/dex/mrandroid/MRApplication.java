package com.wurq.dex.mrandroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.wurq.dex.base.AppProfile;
import com.wurq.dex.mrandroid.mainpage.msglistner.MsgListnerService;

/**
 * Created by wurongqiu on 16/12/6.
 */
public class MRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        AppProfile.setContext(context);

        // start service
        Intent msgService = new Intent(context,MsgListnerService.class);
        startService(msgService);

        //崩溃捕获以及日志输出配置
        if (BuildConfig.DEBUG) {
//            CrashHandlerL.getInstance().init(context);//debug状态下使用本地崩溃捕获

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
