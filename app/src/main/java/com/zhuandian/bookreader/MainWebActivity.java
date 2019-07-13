package com.zhuandian.bookreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.base.WebPageActivity;
import com.zhuandian.bookreader.Utils.Constant;

import butterknife.BindView;

public class MainWebActivity extends BaseActivity {

    @BindView(R.id.wbPage)
    WebView wvPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_web;
    }

    @Override
    protected void setUpView() {
        wvPage.getSettings().setJavaScriptEnabled(true);
        wvPage.getSettings().setDomStorageEnabled(true);
        wvPage.setWebViewClient(new WebViewClient());
        wvPage.setWebChromeClient(new WebChromeClient());
        wvPage.loadUrl(Constant.URL);
    }



    @Override
    public void onBackPressed() {
        if (wvPage.canGoBack())
            wvPage.goBack();
        else
            super.onBackPressed();
    }

}
