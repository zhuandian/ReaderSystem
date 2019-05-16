package com.zhuandian.bookreader.business.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.MainActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.business.UploadBookActivity;
import com.zhuandian.bookreader.business.login.LoginActivity;
import com.zhuandian.utils.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.Bmob;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_upload_book)
    TextView tvUploadBook;
    @BindView(R.id.tv_my_book)
    TextView tvMyBook;
    @BindView(R.id.tv_book_shop)
    TextView tvBookShop;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.tv_upload_book, R.id.tv_my_book, R.id.tv_book_shop, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_upload_book:
                startActivity(new Intent(actitity,UploadBookActivity.class));
                break;
            case R.id.tv_my_book:
                ((MainActivity) actitity).setCurrentPage(2);
                ((MainActivity) actitity).setAappTitle("我的书单");
                break;
            case R.id.tv_book_shop:
                ((MainActivity) actitity).setCurrentPage(1);
                ((MainActivity) actitity).setAappTitle("小说商城");
                break;
            case R.id.tv_logout:
                startActivity(new Intent(actitity, LoginActivity.class));
                actitity.finish();
                break;
        }
    }
}
