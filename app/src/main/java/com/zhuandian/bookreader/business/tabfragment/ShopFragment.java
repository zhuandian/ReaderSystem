package com.zhuandian.bookreader.business.tabfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class ShopFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView() {

    }


}
