package com.zhuandian.bookreader.business;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class BookReaderActivity extends BaseActivity {


    @BindView(R.id.tv_book_title)
    TextView tvBookTitle;
    @BindView(R.id.tv_book_content)
    TextView tvBookContent;
    @BindView(R.id.tv_get_score)
    TextView tvGetScore;
    private BookEntity bookEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_reader;
    }

    @Override
    protected void setUpView() {
        this.setTitle("阅读详情");
        bookEntity = (BookEntity) getIntent().getSerializableExtra("entity");
        tvBookTitle.setText(bookEntity.getBookName());
        tvBookContent.setText(bookEntity.getBookContent());
    }


    @OnClick({R.id.tv_get_score, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_score:
                UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
                currentUser.setUserMoney(currentUser.getUserMoney() + 10);
                currentUser.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            new AlertDialog.Builder(BookReaderActivity.this)
                                    .setTitle("棒棒哒")
                                    .setMessage("读完本书，我们给您 10 积分作为奖励!!")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            BookReaderActivity.this.finish();
                                        }
                                    }).create().show();
                        }
                    }
                });
                break;
            case R.id.fab:
                Intent intent = new Intent(this, BookCommentActivity.class);
                intent.putExtra("entity", bookEntity);
                startActivity(intent);
                break;
        }


    }
}
