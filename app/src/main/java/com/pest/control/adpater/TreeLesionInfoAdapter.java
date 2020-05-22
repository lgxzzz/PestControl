package com.pest.control.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pest.control.R;
import com.pest.control.WebViewActivity;
import com.pest.control.bean.Pest;
import com.pest.control.bean.TreeLesion;

import java.util.ArrayList;
import java.util.List;

public class TreeLesionInfoAdapter extends BaseAdapter {

    Context mContext;
    List<TreeLesion> mMsgInfos = new ArrayList<>();

    public TreeLesionInfoAdapter(Context mContext, List<TreeLesion> mMsgInfos){
        this.mContext = mContext;
        this.mMsgInfos = mMsgInfos;
    }

    public void setData(List<TreeLesion> mMsgInfos){
        this.mMsgInfos = mMsgInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMsgInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mMsgInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final TreeLesion msgInfo = mMsgInfos.get(i);
        TreeLesionInfoAdapter.ViewHoler holer = null;
        if (view == null){
            holer = new TreeLesionInfoAdapter.ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.msg_item,null);
            holer.mPic = (ImageView) view.findViewById(R.id.msg_pic);
            holer.mTitle = (TextView) view.findViewById(R.id.msg_title);
            holer.mContent = (TextView) view.findViewById(R.id.msg_content);
            view.setTag(holer);
        }else{
            holer = (TreeLesionInfoAdapter.ViewHoler) view.getTag();
        }
        holer.mPic.setBackgroundResource(msgInfo.getTREELESION_PIC_ID());
        holer.mTitle.setText(msgInfo.getTREELESION_TYPE());
        holer.mContent.setText(msgInfo.getTREELESION_CONTEX());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,WebViewActivity.class);
                intent.putExtra("url",msgInfo.getTREELESION_URL());
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHoler{
        ImageView mPic;
        TextView mTitle;
        TextView mContent;
    }
}
