package com.zhuandian.bookreader.business.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.BookDetailActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.Utils.GlideImageLoader;
import com.zhuandian.bookreader.adapter.BookListAdapter;
import com.zhuandian.bookreader.entity.BookEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        initBanner();
        initHotBook();
    }

    private void initHotBook() {
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(final List<BookEntity> list, BmobException e) {

                rvList.setLayoutManager(new LinearLayoutManager(actitity));
                rvList.setAdapter(new BookListAdapter(list, new BookListAdapter.OnStateTextClickListener() {
                    @Override
                    public void onClick(BookEntity bookEntity) {
                        Intent intent = new Intent(actitity, BookDetailActivity.class);
                        intent.putExtra("entity", bookEntity);
                        startActivity(intent);
                    }
                }));

            }
        });
    }

    private void initBanner() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.user_1_1);
        images.add(R.drawable.user_1_2);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

}
