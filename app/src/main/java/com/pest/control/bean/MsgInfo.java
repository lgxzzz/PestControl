package com.pest.control.bean;

import android.graphics.drawable.Drawable;

public class MsgInfo {
    String title;
    Drawable mIcon;
    String url = "";
    int mPicId;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getmPicId() {
        return mPicId;
    }

    public void setmPicId(int mPicId) {
        this.mPicId = mPicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
