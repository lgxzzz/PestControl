package com.pest.control.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pest.control.bean.Pest;
import com.pest.control.bean.TreeLesion;
import com.pest.control.bean.User;
import com.pest.control.util.SharedPreferenceUtil;


import java.util.ArrayList;
import java.util.List;

public class DBManger {
    private Context mContext;
    private SQLiteDbHelper mDBHelper;
    public User mUser;
    public DataBase mDataBase;
    public static  DBManger instance;
    public static DBManger getInstance(Context mContext){
        if (instance == null){
            instance = new DBManger(mContext);
        }
        return instance;
    };

    public DBManger(final Context mContext){
        this.mContext = mContext;
        mDBHelper = new SQLiteDbHelper(mContext);
        mDataBase = new DataBase();
//        if (SharedPreferenceUtil.getFirstTimeUse(mContext)){
            initDefaultData();
            SharedPreferenceUtil.setFirstTimeUse(false,mContext);
//        }
    }


    //用户登陆
    public void login(String name, String password, IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME =? and USER_PASSWORD=?",new String[]{name,password});
            if (cursor.moveToFirst()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String USER_TEL = cursor.getString(cursor.getColumnIndex("USER_TEL"));
                String USER_ROLE = cursor.getString(cursor.getColumnIndex("USER_ROLE"));

                mUser = new User();
                mUser.setUserId(USER_ID);
                mUser.setUserName(USER_NAME);
                mUser.setTelephone(USER_TEL);
                listener.onSuccess();
            }else{
                listener.onError("未查询到该用户");
            }
            db.close();
            return;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        listener.onError("未查询到该用户");
    }

    //修改用户信息
    public void updateUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                ContentValues values = new ContentValues();
                values.put("USER_NAME",user.getUserName());
                values.put("USER_TEL",user.getTelephone());
                values.put("USER_PASSWORD",user.getPassword());

                int code = db.update(SQLiteDbHelper.TAB_USER,values,"USER_NAME =?",new String[]{user.getUserName()+""});
                listener.onSuccess();
            }else {
                insertUser(user,listener);
            }
            db.close();
        }catch (Exception e){

        }
    }

    //注册用户
    public void registerUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                listener.onError("用户名已经被注册！");
            }else{
                String userid = getRandomUSER_ID();
                ContentValues values = new ContentValues();
                values.put("USER_ID",userid);
                values.put("USER_NAME",user.getUserName());
                values.put("USER_PASSWORD",user.getPassword());
                mUser = user;
                mUser.setUserId(userid);
                mUser.setUserName(user.getUserName());
                mUser.setTelephone(user.getTelephone());
                long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
                listener.onSuccess();
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    };

    //注册用户
    public void insertUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                listener.onError("用户名已经被注册！");
            }else{
                String userid = getRandomUSER_ID();
                ContentValues values = new ContentValues();
                values.put("USER_ID",userid);
                values.put("USER_NAME",user.getUserName());
                values.put("USER_PASSWORD",user.getPassword());
                values.put("USER_TEL",user.getTelephone());
                long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
                listener.onSuccess();
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    };


    //生成随机userid
    public String getRandomUSER_ID(){
        String strRand="LF" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }






    //获取所有用户
    public List<User> getAllUsers(){
        List<User> mUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_USER,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_PASSWORD = cursor.getString(cursor.getColumnIndex("USER_PASSWORD"));
                String USER_TEL = cursor.getString(cursor.getColumnIndex("USER_TEL"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String USER_ROLE = cursor.getString(cursor.getColumnIndex("USER_ROLE"));

                User user = new User();
                user.setUserId(USER_ID);
                user.setUserName(USER_NAME);
                user.setPassword(USER_PASSWORD);
                user.setTelephone(USER_TEL);
                mUsers.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mUsers;
    }

    //添加树木病害
    public void insertTreeLesion(TreeLesion lesion){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("TREELESION_ID",lesion.getTREELESION_ID());
            values.put("TREELESION_TYPE",lesion.getTREELESION_TYPE());
            values.put("TREELESION_CONTEX",lesion.getTREELESION_CONTEX());
            values.put("TREELESION_URL",lesion.getTREELESION_URL());
            values.put("TREELESION_PIC_ID",lesion.getTREELESION_PIC_ID()+"");
            long code = db.insert(SQLiteDbHelper.TAB_TREELESION,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取所有害虫信息
    public List<Pest> getAllPests(){
        List<Pest> mPestsInfoList = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_PEST,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String PEST_ID = cursor.getString(cursor.getColumnIndex("PEST_ID"));
                String PEST_TYPE = cursor.getString(cursor.getColumnIndex("PEST_TYPE"));
                String PEST_CONTEX = cursor.getString(cursor.getColumnIndex("PEST_CONTEX"));
                String PEST_URL = cursor.getString(cursor.getColumnIndex("PEST_URL"));
                String PEST_PIC_ID = cursor.getString(cursor.getColumnIndex("PEST_PIC_ID"));

                Pest pest = new Pest();
                pest.setPEST_ID(PEST_ID);
                pest.setPEST_TYPE(PEST_TYPE);
                pest.setPEST_CONTEX(PEST_CONTEX);
                pest.setPEST_PIC_ID(Integer.parseInt(PEST_PIC_ID));
                pest.setPEST_URL(PEST_URL);

                mPestsInfoList.add(pest);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mPestsInfoList;
    }

    //根据条件查询树木病害信息
    public List<TreeLesion> getAllTreeLesions(){
        List<TreeLesion> mTreeLesionInfoList = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_TREELESION,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String TREELESION_ID = cursor.getString(cursor.getColumnIndex("TREELESION_ID"));
                String TREELESION_TYPE = cursor.getString(cursor.getColumnIndex("TREELESION_TYPE"));
                String TREELESION_CONTEX = cursor.getString(cursor.getColumnIndex("TREELESION_CONTEX"));
                String TREELESION_URL = cursor.getString(cursor.getColumnIndex("TREELESION_URL"));
                String TREELESION_PIC_ID = cursor.getString(cursor.getColumnIndex("TREELESION_PIC_ID"));

                TreeLesion treeLesion = new TreeLesion();
                treeLesion.setTREELESION_ID(TREELESION_ID);
                treeLesion.setTREELESION_TYPE(TREELESION_TYPE);
                treeLesion.setTREELESION_CONTEX(TREELESION_CONTEX);
                treeLesion.setTREELESION_PIC_ID(Integer.parseInt(TREELESION_PIC_ID));
                treeLesion.setTREELESION_URL(TREELESION_URL);

                mTreeLesionInfoList.add(treeLesion);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mTreeLesionInfoList;
    }

    //根据条件查询树木病害信息
    public List<TreeLesion> getTreeLesionsByKey(String key){
        List<TreeLesion> mTreeLesionInfoList = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM TreeLesions WHERE PEST_TYPE LIKE '%" + key + "%'", null);
            while (cursor.moveToNext()){
                String TREELESION_ID = cursor.getString(cursor.getColumnIndex("TREELESION_ID"));
                String TREELESION_TYPE = cursor.getString(cursor.getColumnIndex("TREELESION_TYPE"));
                String TREELESION_CONTEX = cursor.getString(cursor.getColumnIndex("TREELESION_CONTEX"));
                String TREELESION_URL = cursor.getString(cursor.getColumnIndex("TREELESION_URL"));
                String TREELESION_PIC_ID = cursor.getString(cursor.getColumnIndex("TREELESION_PIC_ID"));

                TreeLesion treeLesion = new TreeLesion();
                treeLesion.setTREELESION_ID(TREELESION_ID);
                treeLesion.setTREELESION_TYPE(TREELESION_TYPE);
                treeLesion.setTREELESION_CONTEX(TREELESION_CONTEX);
                treeLesion.setTREELESION_PIC_ID(Integer.parseInt(TREELESION_PIC_ID));
                treeLesion.setTREELESION_URL(TREELESION_URL);
                mTreeLesionInfoList.add(treeLesion);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mTreeLesionInfoList;
    }

    //根据条件查询害虫信息
    public List<Pest> getPestsByKey(String key){
        List<Pest> mPestsInfoList = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pest WHERE PEST_TYPE LIKE '%" + key + "%'", null);
            while (cursor.moveToNext()){
                String PEST_ID = cursor.getString(cursor.getColumnIndex("PEST_ID"));
                String PEST_TYPE = cursor.getString(cursor.getColumnIndex("PEST_TYPE"));
                String PEST_CONTEX = cursor.getString(cursor.getColumnIndex("PEST_CONTEX"));
                String PEST_URL = cursor.getString(cursor.getColumnIndex("PEST_URL"));
                String PEST_PIC_ID = cursor.getString(cursor.getColumnIndex("PEST_PIC_ID"));

                Pest pest = new Pest();
                pest.setPEST_ID(PEST_ID);
                pest.setPEST_TYPE(PEST_TYPE);
                pest.setPEST_CONTEX(PEST_CONTEX);
                pest.setPEST_PIC_ID(Integer.parseInt(PEST_PIC_ID));
                pest.setPEST_URL(PEST_URL);

                mPestsInfoList.add(pest);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mPestsInfoList;
    }

    //添加害虫
    public void insertPest(Pest pest){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("PEST_ID",pest.getPEST_ID());
            values.put("PEST_TYPE",pest.getPEST_TYPE());
            values.put("PEST_CONTEX",pest.getPEST_CONTEX());
            values.put("PEST_URL",pest.getPEST_URL());
            values.put("PEST_PIC_ID",pest.getPEST_PIC_ID()+"");
            long code = db.insert(SQLiteDbHelper.TAB_PEST,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initDefaultData(){
        List<TreeLesion> mTreeLesionInfoList = mDataBase.mTreeLesionInfoList;
        for (int i =0;i<mTreeLesionInfoList.size();i++){
            TreeLesion treeLesion = mTreeLesionInfoList.get(i);
            insertTreeLesion(treeLesion);
        }

        List<Pest> mPestInfoList = mDataBase.mPestInfoList;
        for (int i =0;i<mPestInfoList.size();i++){
            Pest pest = mPestInfoList.get(i);
            insertPest(pest);
        }
    }

    public interface IListener{
        public void onSuccess();
        public void onError(String error);
    };


}
