package com.wurq.dex.mrandroid;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.wurq.dex.mrandroid.mainpage.MainPageActivity;

public class MainActivity extends AppCompatActivity {

    private static final int WAIT_TIME = 2000;
    private long mStartTime;

    private volatile boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartTime = System.currentTimeMillis();
        initData( );
    }


    public void initData() {

        long endTime = System.currentTimeMillis();
        long initTime = endTime - mStartTime;
        long delayMillis;

        if (initTime < 0) {
            delayMillis = WAIT_TIME;
        } else if (initTime >= WAIT_TIME) {
            delayMillis = 0;
        } else {
            delayMillis = WAIT_TIME - initTime;
        }

        // x秒后强行关闭
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinished) {
                    isFinished = !isFinished;
                        MainPageActivity.start(MainActivity.this);
                }
            }
        }, delayMillis);


    }
}
