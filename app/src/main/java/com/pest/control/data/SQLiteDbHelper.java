package com.pest.control.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pest.control.util.SharedPreferenceUtil;


public class SQLiteDbHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME = "StoreSelection.db";
    //数据库版本号
    public static int DB_VERSION = 36;
    //用户表
    public static final String TAB_USER = "UserInfo";
    //树木病害表
    public static final String TAB_TREELESION = "TreeLesions";
    //害虫表
    public static final String TAB_PEST = "Pest";

    Context context;
    public SQLiteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createTableTreeLesion(db);
        createTablePest(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        SharedPreferenceUtil.setFirstTimeUse(true,context);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TREELESION);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_PEST);

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

    //创建树木病害表
    public void createTableTreeLesion(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_TREELESION +
                "(TREELESION_ID varchar(20) primary key, " +
                "TREELESION_TYPE varchar(20), " +  //种类
                "TREELESION_CONTEX varchar(20), " + //病害内容
                "TREELESION_URL varchar(20), " +  // 文章链接
                "TREELESION_PIC_ID varchar(20))");//图片id
    }

    //创建害虫表
    public void createTablePest(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_PEST +
                "(PEST_ID varchar(20) primary key, " +
                "PEST_TYPE varchar(20), " +
                "PEST_CONTEX varchar(20), " +
                "PEST_URL varchar(20), " +
                "PEST_PIC_ID varchar(20))");
    }
}
