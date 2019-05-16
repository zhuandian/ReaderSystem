package com.zhuandian.bookreader.business;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UploadBookActivity extends BaseActivity {


    @BindView(R.id.blog_title)
    EditText blogTitle;
    @BindView(R.id.blog_content)
    EditText blogContent;
    @BindView(R.id.commit_content)
    LinearLayout commitContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_book;
    }

    @Override
    protected void setUpView() {
        this.setTitle("发布小说");

    }


    @OnClick(R.id.commit_content)
    public void onClick() {
        String blogTitleStr = blogTitle.getText().toString();
        String blogContentStr = blogContent.getText().toString();
        if (TextUtils.isEmpty(blogTitleStr) || TextUtils.isEmpty(blogContentStr)) {
            Toast.makeText(this, "标题跟内容都不允许为空...", Toast.LENGTH_SHORT).show();
        } else {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setUserEntity(BmobUser.getCurrentUser(UserEntity.class));
            bookEntity.setBookName(blogTitleStr);
            bookEntity.setBookImgUrl("http://img2.imgtn.bdimg.com/it/u=170829079,3580970245&fm=26&gp=0.jpg"); //默认书籍图片，可在数据库修改
            bookEntity.setBookContent(blogContentStr);
            bookEntity.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(UploadBookActivity.this, "发布成功...", Toast.LENGTH_SHORT).show();
                        UploadBookActivity.this.finish();
                    }
                }
            });
        }
    }
}
