package com.zhuandian.bookreader.business;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.Utils.PictureSelectorUtils;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.UserEntity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UploadBookActivity extends BaseActivity {


    @BindView(R.id.blog_title)
    EditText blogTitle;
    @BindView(R.id.blog_content)
    EditText blogContent;
    @BindView(R.id.commit_content)
    LinearLayout commitContent;
    @BindView(R.id.iv_book)
    ImageView ivBook;
    private String bookImgUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_book;
    }

    @Override
    protected void setUpView() {
        this.setTitle("发布小说");

    }


    @OnClick({R.id.commit_content, R.id.iv_book, R.id.tv_upload_book})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_upload_book:
            case R.id.comment_content:
                String blogTitleStr = blogTitle.getText().toString();
                String blogContentStr = blogContent.getText().toString();
                if (TextUtils.isEmpty(blogTitleStr) || TextUtils.isEmpty(blogContentStr)) {
                    Toast.makeText(this, "标题跟内容都不允许为空...", Toast.LENGTH_SHORT).show();
                } else {
                    BookEntity bookEntity = new BookEntity();
                    bookEntity.setUserEntity(BmobUser.getCurrentUser(UserEntity.class));
                    bookEntity.setBookName(blogTitleStr);
                    bookEntity.setBookImgUrl(bookImgUrl);
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
                break;

            case R.id.iv_book:
                PictureSelectorUtils.selectPicture(this, 1);
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    ivBook.setImageURI(Uri.parse(selectList.get(i).getPath()));
                    final BmobFile bmobFile = new BmobFile(new File(selectList.get(i).getCompressPath()));
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
//                                System.out.println("--------------"+bmobFile.getUrl());
                                bookImgUrl = bmobFile.getUrl();
                            }
                        }
                    });
                }
            }

        }
    }
}
