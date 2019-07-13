package com.zhuandian.bookreader.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2019/7/13
 */
public class WebUrlEntity extends BmobObject {
    private String url;
    private int type;// 1 跳转原生页 2.跳转web页

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
