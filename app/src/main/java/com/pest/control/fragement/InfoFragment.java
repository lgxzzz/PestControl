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
import android.widget.Button;
import android.widget.ListView;


import com.pest.control.R;
import com.pest.control.adpater.MsgInfoAdapter;
import com.pest.control.bean.MsgInfo;
import com.pest.control.data.DBManger;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {


    List<MsgInfo> msgInfoList = new ArrayList<>();

    ListView mMsgListview;

    MsgInfoAdapter mAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_info, container, false);
        initView(view);

        return view;
    }

    public static InfoFragment getInstance() {
        return new InfoFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){
        mMsgListview = view.findViewById(R.id.search_info_list);
    };

    public void initData() {
        msgInfoList = DBManger.getInstance(getContext()).mDataBase.mMsgInfoList;
        mAdapter = new MsgInfoAdapter(getContext(),msgInfoList);
        mMsgListview.setAdapter(mAdapter);

    }
}
