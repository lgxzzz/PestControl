package com.pest.control.fragement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.pest.control.R;
import com.pest.control.adpater.TreeLesionInfoAdapter;
import com.pest.control.bean.TreeLesion;
import com.pest.control.data.DBManger;

import java.util.ArrayList;
import java.util.List;

public class TreeLesionFragment extends Fragment {


    List<TreeLesion> treeLesions = new ArrayList<>();

    ListView mMsgListview;

    TreeLesionInfoAdapter mAdapter;

    EditText mTreeLesionSearchEd;

    Button mTreeLesionSearchClearBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_treelesion, container, false);
        initView(view);

        return view;
    }

    public static TreeLesionFragment getInstance() {
        return new TreeLesionFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){
        mMsgListview = view.findViewById(R.id.search_info_list);

        mTreeLesionSearchEd = view.findViewById(R.id.treelesion_search_ed);

        mTreeLesionSearchClearBtn = view.findViewById(R.id.treelesion_search_clear_btn);

        mTreeLesionSearchClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTreeLesionSearchEd.setText("");
                searchData();
            }
        });

        mTreeLesionSearchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData();
            }
        });
    };

    public void initData() {
        treeLesions = DBManger.getInstance(getContext()).getAllTreeLesions();
        mAdapter = new TreeLesionInfoAdapter(getContext(),treeLesions);
        mMsgListview.setAdapter(mAdapter);
    }

    //根据查询条件查询
    public void searchData(){
        String value = mTreeLesionSearchEd.getEditableText().toString();
        if (value.length()==0){
            treeLesions = DBManger.getInstance(getContext()).getAllTreeLesions();
        }else{
            treeLesions = DBManger.getInstance(getContext()).getTreeLesionsByKey(value);
        }
        if (treeLesions.size()>0){
            mAdapter.setData(treeLesions);
        }
    }
}
