package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.telephony.SmsMessage;
import android.util.Log;

import com.wurq.dex.mrandroid.mainpage.msglistner.msgHandle.SmsHandler;
import com.wurq.dex.mrandroid.mainpage.msglistner.msgHandle.SmsObserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MsgListnerService extends Service {

    private static final String TAG = "MsgListnerService";

    private CopyOnWriteArrayList<MsgRemote> mMsgRemotelist = new CopyOnWriteArrayList<MsgRemote>();

    private RemoteCallbackList<OnNewMsgArriveListener> mListenerList =
            new RemoteCallbackList<OnNewMsgArriveListener>();

    private Handler mSMShandler = new SmsHandler(this);

    private MsgIPCAidlInterface.Stub mMsgBinder = new MsgIPCAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
                               String aString) throws RemoteException {

        }

        @Override
        public List<MsgRemote> getMsgList() throws RemoteException {
            return mMsgRemotelist;
        }

        @Override
        public void registerListener(OnNewMsgArriveListener listener) throws RemoteException{
            mListenerList.register(listener);
//            if(!mListenerList.contains(listener))  {
//                mListenerList.add(listener);
//            }  else {
//                //
//            }
        }

        @Override
        public void unRegisterListener(OnNewMsgArriveListener listener)  throws RemoteException{
            mListenerList.unregister(listener);
//            if(mListenerList.contains(listener))  {
//                mListenerList.remove(listener);
//            }  else {
//                //
//            }
        }
    };

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
    }

    private SmsObserver mObserver;
    @Override
    public void onCreate( )  {
        super.onCreate();

        mMsgRemotelist.add(new MsgRemote(1,"servicetest1"));
        mMsgRemotelist.add(new MsgRemote(2,"servicetest2"));

//        monitorBroadcastReceiverMsg( );

        // 在这里启动
        Log.d(TAG,"Start SmsObserver ContentResolver");
        ContentResolver resolver = getContentResolver();
        Log.d(TAG,"SmsObserver get ContentResolver");

        mObserver = new SmsObserver(resolver, mSMShandler );
        resolver.registerContentObserver(Uri.parse("content://sms"), true,mObserver);
    }


    private void onNewMsgArrive(MsgRemote msgRemote)  throws RemoteException {
        final int N = mListenerList.beginBroadcast();
        for(int i = 0; i < N; i++)  {
            OnNewMsgArriveListener l = mListenerList.getBroadcastItem(i);
            if(l != null ) {
                try {
                    l.OnNewMsgArrived(msgRemote);
                } catch (RemoteException e) {

                }
            }
        }

        mListenerList.finishBroadcast();
//        for(OnNewMsgArriveListener newMsgArriveListener:mListenerList)  {
//            newMsgArriveListener.OnNewMsgArrived(msgRemote);
//        }
    }

    /*
     * monitor msg before android 4.3
     * after that, we use another msg monitor
     */
    private void monitorBroadcastReceiverMsg( ) {

        Log.d(TAG,"monitorBroadcastReceiverMsg entering...");
        final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
        final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";

        BroadcastReceiver msgBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"monitorBroadcastReceiverMsg onReceive entering...");

//                Toast.makeText(context, "接收短信执行了.....", Toast.LENGTH_LONG).show();
                Log.e(TAG,"SMSReceiver, isOrderedBroadcast()="+ isOrderedBroadcast()+"");
                Log.e(TAG,"SMSReceiver onReceive..."+ "accept message excute......");
                String action = intent.getAction();
                if (SMS_RECEIVED_ACTION.equals(action) || SMS_DELIVER_ACTION.equals(action)) {
//                    Toast.makeText(context, "开始接收短信.....", Toast.LENGTH_LONG).show();

                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[])bundle.get("pdus");
                        if (pdus != null && pdus.length > 0) {
                            SmsMessage[] messages = new SmsMessage[pdus.length];
                            for (int i = 0; i < pdus.length; i++) {
                                byte[] pdu = (byte[]) pdus[i];
                                messages[i] = SmsMessage.createFromPdu(pdu);
                            }
                            int i = 0;
                            for (SmsMessage message : messages) {
                                String content = message.getMessageBody();// 得到短信内容
                                String sender = message.getOriginatingAddress();// 得到发信息的号码

                                MsgRemote msgRemote = new MsgRemote(i,content);
                                mMsgRemotelist.add(msgRemote);
                                try {
                                    onNewMsgArrive( msgRemote);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                i++;
//                                if (content.contains(InterceptKeyKeeper.getInterceptKey(mContext))) {
//                                    Toast.makeText(mContext, "内容为："+content, Toast.LENGTH_LONG).show();
//                                    //setResultData(null);
//                                    this.abortBroadcast();// 中止
//                                }else if (sender.equals("10010") || sender.equals("10086")) {
//                                    Toast.makeText(mContext, "内容为："+content, Toast.LENGTH_LONG).show();
//                                    this.abortBroadcast();// 中止
//                                }
//                                Date date = new Date(message.getTimestampMillis());
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//                                String sendContent = format.format(date) + ":" + sender + "--" + content;
//                                Log.e("SmsReceicer onReceive ",sendContent +" ");
                            }
                        }
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(SMS_RECEIVED_ACTION);
        intentFilter.setPriority(999);
        registerReceiver(msgBroadcastReceiver,intentFilter);
    }


}
