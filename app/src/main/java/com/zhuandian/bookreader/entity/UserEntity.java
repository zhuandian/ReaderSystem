package com.zhuandian.bookreader.entity;

import cn.bmob.v3.BmobUser;

/**
 * desc :用户实体
 * author：xiedong
 */
public class UserEntity extends BmobUser {
    private String nikeName;
    private String userInfo;
    private int userMoney; //用户积分

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
