package com.pest.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.pest.control.bean.User;
import com.pest.control.data.DBManger;
import com.pest.control.fragement.DecodeFragment;
import com.pest.control.fragement.InfoFragment;
import com.pest.control.fragement.PestFragment;
import com.pest.control.util.FragmentUtils;


public class MainActivity extends BaseActivtiy {

    private BottomNavigationView mBottomMenu;

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
        mBottomMenu = findViewById(R.id.bottom_menu);

        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
        showFragment(R.id.bottom_menu_info);
    }


    /**
     * 根据id显示相应的页面
     * @param menu_id
     */
    private void showFragment(int menu_id) {
        switch (menu_id){
            case R.id.bottom_menu_info:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, InfoFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_decode:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, DecodeFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_search:
                FragmentUtils.replaceFragmentToActivity(fragmentManager, PestFragment.getInstance(),R.id.main_frame);
                break;
        }
    }

}
