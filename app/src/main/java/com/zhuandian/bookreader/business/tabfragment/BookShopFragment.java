package com.zhuandian.bookreader.business.tabfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.bookreader.business.BookDetailActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.adapter.BookListAdapter;
import com.zhuandian.bookreader.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class BookShopFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_categary)
    TextView tvCategary;
    private String bookType;
    private boolean isLimit = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView() {
        initBookList();
    }

    private void initBookList() {
        BmobQuery<BookEntity> query = new BmobQuery<>();
        if (isLimit)
            query.addWhereEqualTo("bookType", bookType);
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(final List<BookEntity> list, BmobException e) {
                rvList.setLayoutManager(new LinearLayoutManager(actitity));
                BookListAdapter bookListAdapter = new BookListAdapter(list, new BookListAdapter.OnStateTextClickListener() {
                    @Override
                    public void onClick(BookEntity bookEntity) {
                        Intent intent = new Intent(actitity, BookDetailActivity.class);
                        intent.putExtra("entity", bookEntity);
                        startActivity(intent);
                    }
                });
                rvList.setAdapter(bookListAdapter);
                bookListAdapter.notifyDataSetChanged();

            }
        });
    }


    @OnClick({R.id.tv_search, R.id.tv_categary})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String bookName = etBookName.getText().toString();
                if (TextUtils.isEmpty(bookName)) {
                    Toast.makeText(actitity, "请输入类别...", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(HomeActivity.this, BookListActivity.class);
//                    intent.putExtra("bookType", bookName);
//                    startActivity(intent);
                }
                break;
            case R.id.tv_categary:
                showBookCateGaryDialog();
                break;
        }
    }

    private void showBookCateGaryDialog() {
        final String bookCategoryArray[] = {"娱乐", "古典", "励志", "童话", "全部"};
        AlertDialog.Builder builder = new AlertDialog.Builder(actitity)
                .setTitle("选择书籍类型")
                .setItems(bookCategoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookType = bookCategoryArray[which];
                        isLimit = bookType.equals("全部") ? false : true;
                        initBookList();
                    }
                });
        builder.create().show();
    }
}
