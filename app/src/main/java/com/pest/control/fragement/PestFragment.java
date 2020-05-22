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
import com.pest.control.adpater.PestInfoAdapter;
import com.pest.control.adpater.TreeLesionInfoAdapter;
import com.pest.control.bean.Pest;
import com.pest.control.data.DBManger;

import java.util.ArrayList;
import java.util.List;


public class PestFragment extends Fragment {
    List<Pest> pestInfos = new ArrayList<>();

    ListView mMsgListview;

    PestInfoAdapter mAdapter;

    EditText mPestSearchEd;

    Button mPestSearchClearBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_pest, container, false);
        initView(view);

        return view;
    }

    public static PestFragment getInstance() {
        return new PestFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){
        mMsgListview = view.findViewById(R.id.search_info_list);

        mPestSearchEd = view.findViewById(R.id.pest_search_ed);

        mPestSearchClearBtn = view.findViewById(R.id.pest_search_clear_btn);

        mPestSearchClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPestSearchEd.setText("");
                searchData();
            }
        });

        mPestSearchEd.addTextChangedListener(new TextWatcher() {
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
        pestInfos = DBManger.getInstance(getContext()).getPestsByKey("");
        mAdapter = new PestInfoAdapter(getContext(),pestInfos);
        mMsgListview.setAdapter(mAdapter);

    }

    //根据查询条件查询
    public void searchData(){
        String value = mPestSearchEd.getEditableText().toString();
        if (value.length()==0){
            pestInfos = DBManger.getInstance(getContext()).getAllPests();
        }else{
            pestInfos = DBManger.getInstance(getContext()).getPestsByKey(value);
        }

        if (pestInfos.size()>0){
            mAdapter.setData(pestInfos);
        }
    }



}
