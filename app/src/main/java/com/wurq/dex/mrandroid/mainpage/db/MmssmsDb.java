package com.wurq.dex.mrandroid.mainpage.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by wurongqiu on 17/1/14.
 */
public class MmssmsDb {
    private static final String TAG = "POCTDao";

    private static final String ID = "Id";

//    private final String COLUMNS = "Id", "PoctType","DeviceId","Time"
//            ,"RoleId", "State","Version","Qrcode","CalData", "FwVersion","HwVersion","Filename","Data";

    private final String[] MMSS_COLUMNS = new String[] {};

    private Context mContext;
    private MmssmsDbHelper mMmssmsDbHelper;

    public int mCurrentId = 0;

    private static MmssmsDb smMmssms = null;

    private MmssmsDb( )  {

    }

    private MmssmsDb(Context context) {
        this.mContext = context;
        mMmssmsDbHelper = new MmssmsDbHelper(context);
    }

    public static MmssmsDb getInstance(Context context){
        Log.d(TAG,"getInstance entering...");
        if(null == smMmssms ) {
            synchronized(MmssmsDb.class){
                if(null == smMmssms) {
                    smMmssms = new MmssmsDb(context);
//                    if(smMmssms.detectDataNum() == 0) {
//                        smMmssms.initTable();
//                    }
                }
                else {
                    smMmssms.mContext = context;
                }
            }
        }
        else {
            synchronized(MmssmsDb.class)  {
                smMmssms.mContext = context;
            }
        }

        return smMmssms;
    }

    public static MmssmsDb getInstance(){  // for test
        if(null == smMmssms ) {
            synchronized(MmssmsDb.class){
                if(null == smMmssms) {
                    smMmssms = new MmssmsDb();
                }
            }
        }

        return smMmssms;
    }

    /**
     * 计算db中的检测数量
     */
    private int detectDataNum(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = mMmssmsDbHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(MmssmsDbHelper.TABLE_SMS, new String[]{"COUNT(index)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
//            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }

//            if (count > 0) return true;
        }

        return count;
//        return false;
    }

    /**
     * 初始化数据
     */
    private void initTable(){
        SQLiteDatabase db = null;

        try {
            db = mMmssmsDbHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + MmssmsDbHelper.TABLE_SMS +" (index) values (0)");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG,  e.toString());
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 执行自定义SQL语句
     */
    public void execSQL(String sql) {
        SQLiteDatabase db = null;

        try {
            if (sql.contains("select")){
//                Toast.makeText(context, R.string.strUnableSql, Toast.LENGTH_SHORT).show();
            }else if (sql.contains("insert") || sql.contains("update") || sql.contains("delete")){
                db = mMmssmsDbHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
//                Toast.makeText(context, R.string.strSuccessSql, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
//            Toast.makeText(context, R.string.strErrorSql, Toast.LENGTH_SHORT).show();
            Log.d(TAG, e.toString());
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询数据库中所有数据
     */
   /* public List<POCTDbData> getAllData(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = mMmssmsDbHelper.getReadableDatabase();
            cursor = db.query(MmssmsDbHelper.TABLE_NAME, MMSS_COLUMNS, null, null, null, null, null);

//            if (cursor.getCount() > 0) {
//                List<POCTDbData> orderList = new ArrayList<POCTDbData>(cursor.getCount());
//                while (cursor.moveToNext()) {
//                    orderList.add(parsePOCTData(cursor));
//                }
//                return orderList;
//            }
        }
        catch (Exception e) {
            Log.d(TAG,  e.toString());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }*/

//    /**
//     * 收到设备端检测Data之后，新增一条数据
//     */
//    public boolean insertData(POCTDbData poctDbData, POCTData poctData){
//        SQLiteDatabase db = null;
//
//        int id = getMaxId();
//        try {
//            db = mMmssmsDbHelper.getWritableDatabase();
//            db.beginTransaction();
//
//            ContentValues contentValues = new ContentValues();
////            contentValues.put(DetectConsts.ID, id+1);
////            contentValues.put(DetectConsts.SERVERDATA, "");
////
////            db.insertOrThrow(POCTDBHelper.TABLE_NAME, null, contentValues);
//
//            db.setTransactionSuccessful();
//            return true;
//        }catch (SQLiteConstraintException e){
//        }catch (Exception e){
//            Log.e(TAG,  e.toString());
//        }finally {
//            if (db != null) {
//                db.endTransaction();
//                db.close();
//            }
//        }
//        return false;
//    }

    /**
     * 删除一条数据  此处删除Id为7的数据
     */
    public boolean deletePoct() {
        SQLiteDatabase db = null;

        try {
            db = mMmssmsDbHelper.getWritableDatabase();
            db.beginTransaction();

            // delete from Orders where Id = 7
            db.delete(MmssmsDbHelper.TABLE_SMS, "index = ?", new String[]{String.valueOf(7)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG,  e.toString());
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     * 统计查询  此处查询Country为China的用户总数
     */
    public int getIdCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = mMmssmsDbHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(MmssmsDbHelper.TABLE_SMS,
                    new String[]{"COUNT(Id)"},
                    "Id = ?",
                    new String[] {"2"},
                    null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG,  e.toString());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }

    /**
     * 比较查询  此处查询单笔数据中 high value
     */
    public int getMaxId(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = mMmssmsDbHelper.getReadableDatabase();
            // select Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders
            cursor = db.query(MmssmsDbHelper.TABLE_SMS, new String[]{"Max(index) as index"}, null, null, null, null, null);

            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()) {
//                    int id = (cursor.getInt(cursor.getColumnIndex(MmssmsConsts.ID)));
//                    return id;
                    return 0;
//                    return parsePOCTData(cursor);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG,  e.toString());
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return -1;
    }


}
