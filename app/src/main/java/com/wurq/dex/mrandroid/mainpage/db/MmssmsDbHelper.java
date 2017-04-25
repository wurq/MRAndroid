package com.wurq.dex.mrandroid.mainpage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wurongqiu on 17/1/14.
 */
public class MmssmsDbHelper  extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "poctdb009.db";
    private static final String DB_MSG = "/data/data/com.android.providers.telephony/databases/mmssms.db";
//    public static final String TABLE_NAME = "POCTS";
    /* 一般情况下，对手机短信数据库的操作，主要涉及到三个表：
    Canonical_addresses表 Threads表  Sms表  */
    public static final String TABLE_CANONICAL_ADDRESS = "Canonical_addresses";
    public static final String TABLE_THREADS = "Threads";
    public static final String TABLE_SMS = "Sms";

    public static final String CREAT_SQLMSG =  " (" +
            "index" +
            " integer primary key, "
           ;

//    public static final String CREAT_SQLITE =  " (" +
//            "Id" +
//            " integer primary key, " +
//            "PoctType " +
//            "integer," +
//            " DeviceId text,Time text,RoleId real," +
//            "State integer,DetectId integer,  Version text,Qrcode text," +
//            "CalData text,FwVersion text," +
//            "HwVersion text,Filename text,Data text)";

    public MmssmsDbHelper(Context context) {
        super(context, DB_MSG, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TABLE_SMS + CREAT_SQLMSG;
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_SMS;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
