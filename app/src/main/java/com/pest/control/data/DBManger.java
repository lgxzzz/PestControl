package com.pest.control.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.store.selection.bean.Evaluate;
import com.store.selection.bean.Report;
import com.store.selection.bean.Store;
import com.store.selection.bean.User;
import com.store.selection.bean.Village;
import com.store.selection.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        if (SharedPreferenceUtil.getFirstTimeUse(mContext)){
            initDefaultData();
            SharedPreferenceUtil.setFirstTimeUse(false,mContext);
        }
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
                mUser.setMail(USER_MAIL);
                mUser.setRole(USER_ROLE);
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
                values.put("USER_MAIL",user.getMail());
                values.put("USER_TEL",user.getTelephone());
                values.put("USER_ROLE",user.getRole());
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
                values.put("USER_TEL",user.getMail());
                values.put("USER_ROLE",user.getRole());
                mUser = user;
                mUser.setUserId(userid);
                mUser.setUserName(user.getUserName());
                mUser.setTelephone(user.getTelephone());
                mUser.setMail(user.getMail());
                mUser.setRole(user.getRole());
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
                values.put("USER_MAIL",user.getMail());
                values.put("USER_ROLE",user.getRole());
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

    //获取所有小区
    public List<Village> getAllVillges(){
        List<Village> mVillages = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_VILLAGE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String Village_ID = cursor.getString(cursor.getColumnIndex("Village_ID"));
                String Village_Name = cursor.getString(cursor.getColumnIndex("Village_Name"));
                String Village_Evalute = cursor.getString(cursor.getColumnIndex("Village_Evalute"));
                String Village_Position = cursor.getString(cursor.getColumnIndex("Village_Position"));
                String Village_Address = cursor.getString(cursor.getColumnIndex("Village_Address"));


                Village village = new Village();
                village.setVillage_ID(Village_ID);
                village.setVillage_Name(Village_Name);
                village.setVillage_Evalute(Village_Evalute);
                village.setVillage_Position(Village_Position);
                village.setVillage_Address(Village_Address);
                village.setmEvalutes(parseIDStrToEvalute(Village_Evalute));

                mVillages.add(village);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mVillages;
    }


    public void deleteVillage(Village village){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long code = db.delete(SQLiteDbHelper.TAB_VILLAGE,"Village_ID =?",new String[]{village.getVillage_ID()});
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
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
                user.setMail(USER_MAIL);
                user.setRole(USER_ROLE);
                mUsers.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mUsers;
    }

    //获取所有用户
    public List<User> getUsersNameByRole(String role){
        List<User> mUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_CHARCTER=?",new String[]{role});
            while (cursor.moveToNext()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_PASSWORD = cursor.getString(cursor.getColumnIndex("USER_PASSWORD"));
                String LIFT_PROCESSORPHONE = cursor.getString(cursor.getColumnIndex("LIFT_PROCESSORPHONE"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String USER_CHARCTER = cursor.getString(cursor.getColumnIndex("USER_CHARCTER"));

                User user = new User();
                user.setUserId(USER_ID);
                user.setUserName(USER_NAME);
                user.setPassword(USER_PASSWORD);
                user.setTelephone(LIFT_PROCESSORPHONE);
                user.setMail(USER_MAIL);
                user.setRole(USER_CHARCTER);
                mUsers.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mUsers;
    }

    //获取所有用户
    public List<String> getUsersNameNameByRole(String role){
        List<String> mUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_CHARCTER=?",new String[]{role});
            while (cursor.moveToNext()){
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));

                mUsers.add(USER_NAME);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mUsers;
    }

    //删除用户
    public void deleteUser(User user){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long code = db.delete(SQLiteDbHelper.TAB_USER,"USER_ID =?",new String[]{user.getUserId()});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取所有用户
    public List<User> QueryUsersByNameKey(String key){
        List<User> mUsers = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM UserInfo WHERE USER_NAME LIKE '%" + key + "%'", null);
            while (cursor.moveToNext()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_PASSWORD = cursor.getString(cursor.getColumnIndex("USER_PASSWORD"));
                String LIFT_PROCESSORPHONE = cursor.getString(cursor.getColumnIndex("LIFT_PROCESSORPHONE"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String USER_CHARCTER = cursor.getString(cursor.getColumnIndex("USER_CHARCTER"));

                User user = new User();
                user.setUserId(USER_ID);
                user.setUserName(USER_NAME);
                user.setPassword(USER_PASSWORD);
                user.setTelephone(LIFT_PROCESSORPHONE);
                user.setMail(USER_MAIL);
                user.setRole(USER_CHARCTER);
                mUsers.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mUsers;
    }

    //根据id获取店铺信息
    public Store getStoreByID(String id){
        Store store = null;
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Store where STORE_ID=?",new String[]{id});
            while (cursor.moveToNext()){
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String STORE_LEVLE_1 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_1"));
                String STORE_LEVLE_2 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_2"));
                String STORE_LEVLE_3 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_3"));
                store = new Store();
                store.setSTORE_ID(STORE_ID);
                store.setLevel_First(STORE_LEVLE_1);
                store.setLevel_Sec(STORE_LEVLE_2);
                store.setLevel_Third(STORE_LEVLE_3);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return store;
    }

    public void insertStore(Store store,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Store where STORE_LEVLE_1=? and STORE_LEVLE_2=? and STORE_LEVLE_3=?",new String[]{store.getLevel_First(),store.getLevel_Sec(),store.getLevel_Third()});
            while (cursor.moveToNext()){
                listener.onError("已经存在该类型店铺！");
                return;
            }
            Store temp = mDataBase.createStore(store.getLevel_First(),store.getLevel_Sec(),store.getLevel_Third());
            insertStore(temp);
            listener.onSuccess();
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
        listener.onError("添加失败！");
    }

    public void insertEvalute(Evaluate evaluate,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Evalute where EVA_LEVLE_1=? and EVA_LEVLE_2=? and EVA_LEVLE_3=?",new String[]{evaluate.getLevel_First(),evaluate.getLevel_Sec(),evaluate.getLevel_Third()});
            while (cursor.moveToNext()){
                listener.onError("已经存在该评价！");
                return;
            }
            Evaluate temp = mDataBase.createEvaluate(evaluate.getLevel_First(),evaluate.getLevel_Sec(),evaluate.getLevel_Third(),evaluate.getWeight()+"");
            insertEvalute(temp);
            listener.onSuccess();
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
        listener.onError("添加失败！");
    }


    //根据id获取店铺信息
    public Store getStoreByLv3(String lv3){
        Store store = null;
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Store where STORE_LEVLE_3=?",new String[]{lv3});
            while (cursor.moveToNext()){
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String STORE_LEVLE_1 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_1"));
                String STORE_LEVLE_2 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_2"));
                String STORE_LEVLE_3 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_3"));
                store = new Store();
                store.setSTORE_ID(STORE_ID);
                store.setLevel_First(STORE_LEVLE_1);
                store.setLevel_Sec(STORE_LEVLE_2);
                store.setLevel_Third(STORE_LEVLE_3);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return store;
    }


    //获取所有店铺数据
    public List<Store> getAllStore(){
        List<Store> mStores = new ArrayList<>();
        HashMap<String, List<Store>> mTempMap = new HashMap<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_STORE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String STORE_LEVLE_1 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_1"));
                String STORE_LEVLE_2 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_2"));
                String STORE_LEVLE_3 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_3"));
                Store store = new Store();
                store.setSTORE_ID(STORE_ID);
                store.setLevel_First(STORE_LEVLE_1);
                store.setLevel_Sec(STORE_LEVLE_2);
                store.setLevel_Third(STORE_LEVLE_3);
                mStores.add(store);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mStores;
    }

    //根据lv2获取店铺信息
    public List<String> getStoresByLv2(String lv2){
        List<String> mStores = new ArrayList<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Store where STORE_LEVLE_2=?",new String[]{lv2});
            while (cursor.moveToNext()){
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String STORE_LEVLE_1 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_1"));
                String STORE_LEVLE_2 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_2"));
                String STORE_LEVLE_3 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_3"));

                mStores.add(STORE_LEVLE_3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mStores;
    }

    //获取店铺一级
    public List<Store> getStoreByLevelFirst(){
        List<Store> mStores = new ArrayList<>();
        HashMap<String, List<Store>> mTempMap = new HashMap<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_STORE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String STORE_LEVLE_1 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_1"));
                String STORE_LEVLE_2 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_2"));
                String STORE_LEVLE_3 = cursor.getString(cursor.getColumnIndex("STORE_LEVLE_3"));
                Store store = new Store();
                store.setSTORE_ID(STORE_ID);
                store.setLevel_First(STORE_LEVLE_1);
                store.setLevel_Sec(STORE_LEVLE_2);
                store.setLevel_Third(STORE_LEVLE_3);
                if (!mTempMap.containsKey(STORE_LEVLE_1)){
                    List<Store> stores = new ArrayList<>();
                    stores.add(store);
                    mTempMap.put(STORE_LEVLE_1,stores);
                }else{
                    List<Store> stores = mTempMap.get(STORE_LEVLE_1);
                    stores.add(store);
                    mTempMap.put(STORE_LEVLE_1,stores);
                }
            }

            Iterator<Map.Entry<String, List<Store>>> iter = mTempMap.entrySet()
                    .iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String lv1 = (String)entry.getKey();
                List<Store> temps= (List<Store>)entry.getValue();
                Store store = new Store();
                store.setLevel_First(lv1);
                for (int i=0;i<temps.size();i++){
                    Store temp = temps.get(i);
                    store.setLevel_Sec(temp.getLevel_Sec());
                    store.setLevel_Third(temp.getLevel_Third());
                    if (!store.getLevelSecTitle().contains(temp.getLevel_Sec()))
                    store.getLevelSecTitle().add(temp.getLevel_Sec());
                    if (!store.getLevelThirdTitle().contains(temp.getLevel_Third()))
                    store.getLevelThirdTitle().add(temp.getLevel_Third());
                }
                mStores.add(store);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mStores;
    }

    //获取一级评价标准
    public List<Evaluate> getEvaluteByLevelFirst(){
        List<Evaluate> mEvalutes = new ArrayList<>();
        HashMap<String, List<Evaluate>> mTempMap = new HashMap<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_EVALUTE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String WEIGHT = cursor.getString(cursor.getColumnIndex("WEIGHT"));
                String EVA_LEVLE_1 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_1"));
                String EVA_LEVLE_2 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_2"));
                String EVA_LEVLE_3 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_3"));
                Evaluate evaluate = new Evaluate();
                evaluate.setWeight(WEIGHT);
                evaluate.setLevel_First(EVA_LEVLE_1);
                evaluate.setLevel_Sec(EVA_LEVLE_2);
                evaluate.setLevel_Third(EVA_LEVLE_3);
                if (!mTempMap.containsKey(EVA_LEVLE_1)){
                    List<Evaluate> evaluates = new ArrayList<>();
                    evaluates.add(evaluate);
                    mTempMap.put(EVA_LEVLE_1,evaluates);
                }else{
                    List<Evaluate> evaluates = mTempMap.get(EVA_LEVLE_1);
                    evaluates.add(evaluate);
                    mTempMap.put(EVA_LEVLE_1,evaluates);
                }
            }

            Iterator<Map.Entry<String, List<Evaluate>>> iter = mTempMap.entrySet()
                    .iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String lv1 = (String)entry.getKey();
                List<Evaluate> temps= (List<Evaluate>)entry.getValue();
                Evaluate evaluate = new Evaluate();
                evaluate.setLevel_First(lv1);
                for (int i=0;i<temps.size();i++){
                    Evaluate temp = temps.get(i);

                    if (!evaluate.getLevelSecTitle().contains(temp.getLevel_Sec()))
                        evaluate.getLevelSecTitle().add(temp.getLevel_Sec());
                    if (!evaluate.getLevelThirdTitle().contains(temp.getLevel_Third()))
                        evaluate.getLevelThirdTitle().add(temp.getLevel_Third());



                }
                mEvalutes.add(evaluate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mEvalutes;
    }

    //根据lv2获取评价信息
    public List<String> getEvluatesByLv2(String lv2){
        List<String> mStores = new ArrayList<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Evalute where EVA_LEVLE_2=?",new String[]{lv2});
            while (cursor.moveToNext()){
                String WEIGHT = cursor.getString(cursor.getColumnIndex("WEIGHT"));
                String EVA_LEVLE_1 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_1"));
                String EVA_LEVLE_2 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_2"));
                String EVA_LEVLE_3 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_3"));

                mStores.add(EVA_LEVLE_3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mStores;
    }

    //根据id获取评价
    public Evaluate getEvaluateByLv3(String lv3){
        Evaluate evaluate = null;
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Evalute where EVA_LEVLE_3=?",new String[]{lv3});
            while (cursor.moveToNext()){
                String EVA_ID = cursor.getString(cursor.getColumnIndex("EVA_ID"));
                String WEIGHT = cursor.getString(cursor.getColumnIndex("WEIGHT"));
                String EVA_LEVLE_1 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_1"));
                String EVA_LEVLE_2 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_2"));
                String EVA_LEVLE_3 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_3"));
                evaluate = new Evaluate();
                evaluate.setEvalute_id(EVA_ID);
                evaluate.setWeight(WEIGHT);
                evaluate.setLevel_First(EVA_LEVLE_1);
                evaluate.setLevel_Sec(EVA_LEVLE_2);
                evaluate.setLevel_Third(EVA_LEVLE_3);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return evaluate;
    }


    //根据id获取评价
    public Evaluate getEvaluateByKey(String id){
        Evaluate evaluate = null;
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Evalute where EVA_ID=?",new String[]{id});
            while (cursor.moveToNext()){
                String EVA_ID = cursor.getString(cursor.getColumnIndex("EVA_ID"));
                String WEIGHT = cursor.getString(cursor.getColumnIndex("WEIGHT"));
                String EVA_LEVLE_1 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_1"));
                String EVA_LEVLE_2 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_2"));
                String EVA_LEVLE_3 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_3"));
                evaluate = new Evaluate();
                evaluate.setEvalute_id(EVA_ID);
                evaluate.setWeight(WEIGHT);
                evaluate.setLevel_First(EVA_LEVLE_1);
                evaluate.setLevel_Sec(EVA_LEVLE_2);
                evaluate.setLevel_Third(EVA_LEVLE_3);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return evaluate;
    }

    //获取所有评价标准
    public List<Evaluate> getAllEvalute(){
        List<Evaluate> mEvalutes = new ArrayList<>();
        HashMap<String, List<Evaluate>> mTempMap = new HashMap<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_EVALUTE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String EVA_ID = cursor.getString(cursor.getColumnIndex("EVA_ID"));
                String WEIGHT = cursor.getString(cursor.getColumnIndex("WEIGHT"));
                String EVA_LEVLE_1 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_1"));
                String EVA_LEVLE_2 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_2"));
                String EVA_LEVLE_3 = cursor.getString(cursor.getColumnIndex("EVA_LEVLE_3"));
                Evaluate evaluate = new Evaluate();
                evaluate.setWeight(WEIGHT);
                evaluate.setEvalute_id(EVA_ID);
                evaluate.setLevel_First(EVA_LEVLE_1);
                evaluate.setLevel_Sec(EVA_LEVLE_2);
                evaluate.setLevel_Third(EVA_LEVLE_3);
                mEvalutes.add(evaluate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mEvalutes;
    }

    //添加店铺信息
    public void insertStore(Store store){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("STORE_ID",store.getSTORE_ID());
            values.put("STORE_LEVLE_1",store.getLevel_First());
            values.put("STORE_LEVLE_2",store.getLevel_Sec());
            values.put("STORE_LEVLE_3",store.getLevel_Third());
            long code = db.insert(SQLiteDbHelper.TAB_STORE,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加评价标准
    public void insertEvalute(Evaluate evaluate){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("EVA_ID",evaluate.getEvalute_id());
            values.put("WEIGHT",evaluate.getWeight());
            values.put("EVA_LEVLE_1",evaluate.getLevel_First());
            values.put("EVA_LEVLE_2",evaluate.getLevel_Sec());
            values.put("EVA_LEVLE_3",evaluate.getLevel_Third());
            long code = db.insert(SQLiteDbHelper.TAB_EVALUTE,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //添加小区数据
    public void insertVillage(Village village){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Village_ID",village.getVillage_ID());
            values.put("Village_Name",village.getVillage_Name());
            values.put("Village_Position",village.getVillage_Position());
            values.put("Village_Address",village.getVillage_Address());
            String evluatesStr = "";
            List<Evaluate> evaluates = village.getmEvalutes();
            for (int i = 0;i<evaluates.size();i++){
                evluatesStr = evluatesStr+","+evaluates.get(i).getEvalute_id();
            }
            values.put("Village_Evalute",evluatesStr);
            long code = db.insert(SQLiteDbHelper.TAB_VILLAGE,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //查询小区
    public List<Village> getVillagesByKey(String address){
        List<Village> mVillages = new ArrayList<>();
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Village where Village_Address=?",new String[]{address});
            while (cursor.moveToNext()){
                String Village_ID = cursor.getString(cursor.getColumnIndex("Village_ID"));
                String Village_Name = cursor.getString(cursor.getColumnIndex("Village_Name"));
                String Village_Evalute = cursor.getString(cursor.getColumnIndex("Village_Evalute"));
                String Village_Position = cursor.getString(cursor.getColumnIndex("Village_Position"));
                String Village_Address = cursor.getString(cursor.getColumnIndex("Village_Address"));


                Village village = new Village();
                village.setVillage_ID(Village_ID);
                village.setVillage_Name(Village_Name);
                village.setVillage_Evalute(Village_Evalute);
                village.setVillage_Position(Village_Position);
                village.setVillage_Address(Village_Address);
                village.setmEvalutes(parseIDStrToEvalute(Village_Evalute));

                mVillages.add(village);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mVillages;
    }

    //查询小区
    public Village getVillagesByID(String ID){
        Village village = null;
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Village where Village_ID=?",new String[]{ID});
            while (cursor.moveToNext()){
                String Village_ID = cursor.getString(cursor.getColumnIndex("Village_ID"));
                String Village_Name = cursor.getString(cursor.getColumnIndex("Village_Name"));
                String Village_Evalute = cursor.getString(cursor.getColumnIndex("Village_Evalute"));
                String Village_Position = cursor.getString(cursor.getColumnIndex("Village_Position"));
                String Village_Address = cursor.getString(cursor.getColumnIndex("Village_Address"));

                village = new Village();
                village.setVillage_ID(Village_ID);
                village.setVillage_Name(Village_Name);
                village.setVillage_Evalute(Village_Evalute);
                village.setVillage_Position(Village_Position);
                village.setVillage_Address(Village_Address);
                village.setmEvalutes(parseIDStrToEvalute(Village_Evalute));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return village;
    }

    public void setVillageToCreateReport(Village village){
        mReportMgr.setVillageToCreateReport(village);
    }

    public void createReport(IListener listener){
        Report report = mReportMgr.createReport(listener);
        insertReport(report);
    }

    //创建报告
    public void insertReport(Report report){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Report_ID",report.getReport_ID());
            values.put("Village_ID",report.getVillage().getVillage_ID());
            values.put("Report_Time",report.getReport_Time());
            values.put("STORE_ID",report.getStore().getSTORE_ID());
            String evluatesStr = "";
            List<Evaluate> evaluates = report.getmEvalutes();
            for (int i = 0;i<evaluates.size();i++){
                evluatesStr = evluatesStr+","+evaluates.get(i).getEvalute_id();
            }
            values.put("Report_Evalute",evluatesStr);
            long code = db.insert(SQLiteDbHelper.TAB_REPORT,null,values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //创建报告
    public void deleteReport(Report report){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long code = db.delete(SQLiteDbHelper.TAB_REPORT,"Report_ID =?",new String[]{report.getReport_ID()});
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //查询所有报告
    public List<Report> getAllReports(){
        List<Report> mReports = new ArrayList<>();
        try{

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.query(SQLiteDbHelper.TAB_REPORT,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String Report_ID = cursor.getString(cursor.getColumnIndex("Report_ID"));
                String Village_ID = cursor.getString(cursor.getColumnIndex("Village_ID"));
                String STORE_ID = cursor.getString(cursor.getColumnIndex("STORE_ID"));
                String Report_Time = cursor.getString(cursor.getColumnIndex("Report_Time"));
                String Report_Evalute = cursor.getString(cursor.getColumnIndex("Report_Evalute"));

                Village village = getVillagesByID(Village_ID);
                Store store = getStoreByID(STORE_ID);
                Report report = new Report();
                report.setReport_ID(Report_ID);
                report.setVillage(village);
                report.setReport_Evalute(Report_Evalute);
                report.setStore(store);
                report.setmEvalutes(parseIDStrToEvalute(Report_Evalute));
                report.setReport_Time(Report_Time);
                mReports.add(report);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mReports;
    }

    public List<Evaluate> parseIDStrToEvalute(String str){
        List<Evaluate> mEvalutes = new ArrayList<>();
        String[] IDs = str.split(",");
        for (int i =0 ;i<IDs.length;i++){
            Evaluate evaluate = getEvaluateByKey(IDs[i]);
            if (evaluate!=null){
                mEvalutes.add(evaluate);
            }
        }
        return mEvalutes;
    }

    public void initDefaultData(){
        List<Store> mStores = mDataBase.getDefaultAllStore();
        for (int i=0;i<mStores.size();i++){
            insertStore(mStores.get(i));
        }
        List<Evaluate> mEvalutes = mDataBase.getDefaultEvalute();
        for (int i=0;i<mEvalutes.size();i++){
            insertEvalute(mEvalutes.get(i));
        }
        List<Village> mVillages = mDataBase.getDefaultVillages();
        for (int i=0;i<mVillages.size();i++){
            insertVillage(mVillages.get(i));
        }
    }

    public interface IListener{
        public void onSuccess();
        public void onError(String error);
    };


}
