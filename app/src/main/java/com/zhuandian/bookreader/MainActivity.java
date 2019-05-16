package com.zhuandian.bookreader;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.adapter.HomePageAdapter;
import com.zhuandian.bookreader.business.tabfragment.MyBookFragment;
import com.zhuandian.bookreader.business.tabfragment.HomeFragment;
import com.zhuandian.bookreader.business.tabfragment.MyFragment;
import com.zhuandian.bookreader.business.tabfragment.BookShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_bottom)
    BottomNavigationView tabBottom;
    private static final int PAGE_HOME = 0;
    private static final int PAGE_SHOP = 1;
    private static final int PAGE_BOOK = 2;
    private static final int PAGE_MY = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new BookShopFragment());
        fragmentList.add(new MyBookFragment());
        fragmentList.add(new MyFragment());

        vpHome.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragmentList));
        vpHome.setOffscreenPageLimit(4);

        vpHome.setCurrentItem(PAGE_HOME);
        setAappTitle("热门推荐");
        initBottomTab();
    }

    private void initBottomTab() {
        vpHome.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabBottom.getMenu().getItem(position).setChecked(true);
            }
        });
        tabBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        vpHome.setCurrentItem(PAGE_HOME);
                        setAappTitle("热门推荐");
                        break;
                    case R.id.tab_shop:
                        vpHome.setCurrentItem(PAGE_SHOP);
                        setAappTitle("小说商城");
                        break;
                    case R.id.tab_book:
                        vpHome.setCurrentItem(PAGE_BOOK);
                        setAappTitle("我的书单");
                        break;
                    case R.id.tab_my:
                        vpHome.setCurrentItem(PAGE_MY);
                        setAappTitle("个人中心");
                        break;
                }
                return true;
            }
        });
    }

    public void setAappTitle(String title) {
        this.setTitle(title);
    }

    public void setCurrentPage(int index) {
        vpHome.setCurrentItem(index);
    }

}
