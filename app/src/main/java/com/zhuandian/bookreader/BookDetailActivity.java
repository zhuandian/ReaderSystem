package com.zhuandian.bookreader;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class BookDetailActivity extends BaseActivity {


    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_add_book)
    TextView tvAddBook;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_book_price)
    TextView tvBookPrice;
    private BookEntity bookEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void setUpView() {
        bookEntity = (BookEntity) getIntent().getSerializableExtra("entity");
        Glide.with(BookDetailActivity.this).load(bookEntity.getBookImgUrl()).into(ivBook);
        tvBookName.setText("书名：" + bookEntity.getBookName());
        tvBookPrice.setText(bookEntity.getBookPrice() + " 积分");
        tvDesc.setText(String.format("详细信息：\n该书入馆时间：%s\n,该书讲述的故事为：%s", bookEntity.getCreatedAt(), bookEntity.getBookDesc()));
    }


    @OnClick(R.id.tv_add_book)
    public void onClick() {
        if (bookEntity.getBookState() == 1) {
            Toast.makeText(this, "改书已在书单中...", Toast.LENGTH_SHORT).show();
        } else {
            final UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
            final int currentUserMoney = currentUser.getUserMoney();
            if (currentUserMoney >= bookEntity.getBookPrice()) {
                bookEntity.setBookState(1);
                bookEntity.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            currentUser.setUserMoney(currentUserMoney - bookEntity.getBookPrice());
                            currentUser.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(BookDetailActivity.this, "加入书单成功...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}
