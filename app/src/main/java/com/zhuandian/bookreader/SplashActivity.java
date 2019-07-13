package com.zhuandian.bookreader;

import android.content.Intent;
import android.os.Handler;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.Utils.Constant;
import com.zhuandian.bookreader.entity.WebUrlEntity;
import com.zhuandian.bookreader.business.login.LoginActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SplashActivity extends BaseActivity {
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {

        BmobQuery<WebUrlEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<WebUrlEntity>() {
            @Override
            public void done(List<WebUrlEntity> list, BmobException e) {
                WebUrlEntity webUrlEntity = list.get(0);
                Constant.URL = webUrlEntity.getUrl();
                type = webUrlEntity.getType();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, type == 2 ? MainWebActivity.class : LoginActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
    }

}
