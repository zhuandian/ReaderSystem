package com.zhuandian.bookreader.business.tabfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.adapter.BookListAdapter;
import com.zhuandian.bookreader.business.BookReaderActivity;
import com.zhuandian.bookreader.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class MyBookFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void onResume() {
        super.onResume();
        initBookList();
    }

    @Override
    protected void initView() {
        initBookList();
    }

    private void initBookList() {
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereEqualTo("bookState", 1).
                findObjects(new FindListener<BookEntity>() {
                    @Override
                    public void done(final List<BookEntity> list, BmobException e) {
                        rvList.setLayoutManager(new LinearLayoutManager(actitity));
                        rvList.setAdapter(new BookListAdapter(list, new BookListAdapter.OnStateTextClickListener() {
                            @Override
                            public void onClick(BookEntity bookEntity) {
                                Intent intent = new Intent(actitity, BookReaderActivity.class);
                                intent.putExtra("entity", bookEntity);
                                startActivity(intent);
                            }
                        }));

                    }
                });
    }

}
