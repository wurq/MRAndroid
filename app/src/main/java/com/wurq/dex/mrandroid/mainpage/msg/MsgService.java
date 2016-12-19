package com.wurq.dex.mrandroid.mainpage.msg;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MsgService extends Service {
    private MsgBinder mMsgBinder = new MsgBinder();
    private Callback mCallback;

    public MsgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mMsgBinder;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public class MsgBinder extends Binder {
        public MsgService getService () {
            return MsgService.this;
        }
    }

    public interface Callback  {
        void onOperationProgress(int progress);
        void onOperationCompletion();
    }
}
