package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.wurq.dex.mrandroid.mainpage.msglistner.MsgIPCAidlInterface;

import java.util.List;

public class MsgListnerService extends Service {

    private static final String TAG = "MsgService";

    private MsgIPCAidlInterface.Stub mMsgBinder = new MsgIPCAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
                               String aString) throws RemoteException {

        }

        @Override
        public List<MsgRemote> getMsgList()  {
            return null;
        }
    };
//    private Callback mCallback;

    public MsgListnerService() {
    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId) {
//        if (Build.VERSION.SDK_INT < 18) {
//            startForeground(1, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
//        } else {
//            Intent innerIntent = new Intent(this, MsgListnerService.class);
//            startService(innerIntent);
//            startForeground(1, new Notification());
//        }

//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMsgBinder;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
