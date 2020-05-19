package com.pest.control;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pest.control.data.DBManger;


public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mMgrBottomMenu;
    private BottomNavigationView mUserBottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow ();
        WindowManager.LayoutParams params = win.getAttributes ();
        win.setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        setContentView(R.layout.activity_main);

        init();

    }

    public void init(){
        User mUser = DBManger.getInstance(this).mUser;
        mMgrBottomMenu = findViewById(R.id.bottom_menu_manager);
        mUserBottomMenu = findViewById(R.id.bottom_menu_user);


        mMgrBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });


        mUserBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });

        if (mUser!=null){
            String role = mUser.getRole();
            if (role.equals("管理员")){
                mMgrBottomMenu.setVisibility(View.VISIBLE);
                mUserBottomMenu.setVisibility(View.GONE);
                mMgrBottomMenu.setSelectedItemId(R.id.bottom_menu_store);
            }else if(role.equals("普通用户")){
                mMgrBottomMenu.setVisibility(View.GONE);
                mUserBottomMenu.setVisibility(View.VISIBLE);
                mUserBottomMenu.setSelectedItemId(R.id.bottom_menu_address);
            }
        }
        //fix me lgx
//        mMgrBottomMenu.setVisibility(View.VISIBLE);
//        mUserBottomMenu.setVisibility(View.GONE);
//        mMgrBottomMenu.setSelectedItemId(R.id.bottom_menu_store);
//
//        mMgrBottomMenu.setVisibility(View.GONE);
//        mUserBottomMenu.setVisibility(View.VISIBLE);
//        mUserBottomMenu.setSelectedItemId(R.id.bottom_menu_address);
    }


    /**
     * 根据id显示相应的页面
     * @param menu_id
     */
    private void showFragment(int menu_id) {
        switch (menu_id){
            case R.id.bottom_menu_history:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, ReportHistoryFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_address:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AddressFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_user_about:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_store:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, StoreFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_score:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, EvaluteFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_mgr_about:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
        }
    }

}
