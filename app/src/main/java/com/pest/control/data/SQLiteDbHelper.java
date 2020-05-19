package com.pest.control.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.store.selection.util.SharedPreferenceUtil;


public class SQLiteDbHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME = "StoreSelection.db";
    //数据库版本号
    public static int DB_VERSION = 36;
    //用户表
    public static final String TAB_USER = "UserInfo";
    //商店表
    public static final String TAB_STORE = "Store";
    //评价表
    public static final String TAB_EVALUTE = "Evalute";
    //小区表
    public static final String TAB_VILLAGE = "Village";
    //报告表
    public static final String TAB_REPORT = "Report";

    Context context;
    public SQLiteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createTableStore(db);
        createTableEvalute(db);
        createTableVillage(db);
        createTableReport(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        SharedPreferenceUtil.setFirstTimeUse(true,context);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_STORE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_EVALUTE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_VILLAGE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_REPORT);
        onCreate(db);
    }

    //创建人员表
    public void createTableUser(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_USER +
                "(USER_ID varchar(20) primary key, " +
                "USER_NAME varchar(20), " +
                "USER_PASSWORD varchar(20), " +
                "USER_TEL varchar(20), " +
                "USER_MAIL varchar(20), " +
                "USER_ROLE varchar(20))");
    }

    //创建商店表
    public void createTableStore(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_STORE +
                "(STORE_ID varchar(20) primary key, " +
                "STORE_LEVLE_1 varchar(20), " +
                "STORE_LEVLE_2 varchar(20), " +
                "STORE_LEVLE_3 varchar(20))");
    }

    //创建评价表
    public void createTableEvalute(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_EVALUTE +
                "(EVA_ID varchar(20) primary key, " +
                "WEIGHT varchar(20), " +
                "LV_2_WEIGHT varchar(20), " +
                "LV_1_WEIGHT varchar(20), " +
                "EVA_LEVLE_1 varchar(20), " +
                "EVA_LEVLE_2 varchar(20), " +
                "EVA_LEVLE_3 varchar(20))");
    }

    //创建小区表
    public void createTableVillage(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_VILLAGE +
                "(Village_ID varchar(20) primary key, " +
                "Village_Name varchar(20), " +
                "Village_Evalute text, " +
                "Village_Position varchar(20), " +
                "Village_Address varchar(20))");
    }

    //创建报告表
    public void createTableReport(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_REPORT +
                "(Report_ID varchar(20) primary key, " +
                "Village_ID varchar(20), " +
                "STORE_ID varchar(20), " +
                "Report_Evalute text, " +
                "Report_Time varchar(20))");
    }
}
