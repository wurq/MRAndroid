package com.wurq.dex.mrandroid.mainpage.msg;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MsgService extends Service {
    private static final String TAG = "MsgService";
    private MsgBinder mMsgBinder = new MsgBinder();
    private Callback mCallback;

    public MsgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        new MsgAsyncTask().execute();
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

    private final class MsgAsyncTask extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String result) {
            onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
//            getSmsFromPhone();
            getSmsInPhone();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mCallback.onOperationCompletion();
        }

        /**
         * 获取手机内所以短消息
         */
        @TargetApi(Build.VERSION_CODES.N)
        private void getSmsInPhone(){
            final String SMS_URI_ALL   = "content://sms/";
        /*final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND  = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";  */

            try{
                ContentResolver cr = getContentResolver();
                String[] projection = new String[]{"_id", "address", "person",
                        "body", "date", "type"};
                Uri uri = Uri.parse(SMS_URI_ALL);
                Cursor cur = cr.query(uri, projection, null, null, "date desc");

                if (cur.moveToFirst()) {
                    String name;
                    String phoneNumber;
                    String smsbody;
                    String date;
                    String type;

                    //    int nameColumn = cur.getColumnIndex("person");
                    int phoneNumberColumn = cur.getColumnIndex("address");
                    int smsbodyColumn = cur.getColumnIndex("body");
                    int dateColumn = cur.getColumnIndex("date");
                    int typeColumn = cur.getColumnIndex("type");

                    do{
                        phoneNumber = cur.getString(phoneNumberColumn);
                        //    name = cur.getString(nameColumn);    这样获取的联系认为空，所以我改用下面的方法获取
//                        name = getPeopleNameFromPerson(phoneNumber);
                        smsbody = cur.getString(smsbodyColumn);

//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
//                        date = dateFormat.format(d);

                        int typeId = cur.getInt(typeColumn);
                        if(typeId == 1){
                            type = "接收";
                        } else if(typeId == 2){
                            type = "发送";
                        } else {
                            type = "草稿";
                        }

//                        title.add(type+" "+date+'\n'+phoneNumber);
//                        text.add(name+'\n'+smsbody);

                        if(smsbody == null) smsbody = "";
                        MsgData.ITEMS.add(new MsgData.DataItem(smsbody,smsbody,smsbody));
                    }   while(cur.moveToNext());
                }
                cur.close();
                cur = null;
            } catch(SQLiteException ex) {
                Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
            }

        }

        private Uri SMS_INBOX = Uri.parse("content://sms/");
        public void getSmsFromPhone() {
            ContentResolver cr = getContentResolver();
            String[] projection = new String[] { "body" };//"_id", "address", "person",, "date", "type
            String where = " date >  "
                    + (System.currentTimeMillis() - 10 * 60 * 1000);
            Cursor cur = null;
            try {
                cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
            }
            catch (Exception e) {
                Log.d(TAG,"not find query failed");
            }
            if (null == cur)
                return;

            if(cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String number = cur.getString(cur.getColumnIndex("address"));//手机号
                    String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
                    String body = cur.getString(cur.getColumnIndex("body"));
                    //这里我是要获取自己短信服务号码中的验证码~~
                    Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        String res = matcher.group().substring(1, 11);
                        MsgData.ITEMS.add(new MsgData.DataItem(res,res,res));
    //                    mobileText.setText(res);
                    }
                }
            }
        }
        ///////////


        public void getDeleteSms( )  {

        }

    }
}
