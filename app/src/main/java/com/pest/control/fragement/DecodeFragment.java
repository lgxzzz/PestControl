package com.pest.control.fragement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.pest.control.R;

import java.util.ArrayList;
import java.util.List;

/***
 * 管理员电梯信息管理界面
 *
 * */
public class DecodeFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_decode, container, false);
        initView(view);
        return view;
    }

    public static DecodeFragment getInstance() {
        return new DecodeFragment();
    }

    public void initView(View view){


    };

    public void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


}
