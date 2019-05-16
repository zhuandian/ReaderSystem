package com.zhuandian.bookreader.business;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.adapter.UserCommentAdapter;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.NovelCommentEntity;
import com.zhuandian.bookreader.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BookCommentActivity extends BaseActivity {

    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_price)
    TextView tvBookPrice;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.comment_listview)
    RecyclerView commentListview;
    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.submit_comment)
    TextView submitComment;
    @BindView(R.id.submit_comment_layout)
    LinearLayout submitCommentLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cl_root)
    CoordinatorLayout clRoot;
    private BookEntity bookEntity;
    private BookEntity mDatas;
    private UserCommentAdapter userCommentAdapter;
    private List<NovelCommentEntity> commentDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_comment;
    }

    @Override
    protected void setUpView() {
        this.setTitle("评论详情");
        bookEntity = (BookEntity) getIntent().getSerializableExtra("entity");
        Glide.with(BookCommentActivity.this).load(bookEntity.getBookImgUrl()).into(ivBook);
        tvBookName.setText("书名：" + bookEntity.getBookName());
        tvBookPrice.setText(bookEntity.getBookPrice() + " 积分");
        tvDesc.setText(String.format("详细信息：\n该书入馆时间：%s\n,该书讲述的故事为：%s", bookEntity.getCreatedAt(), bookEntity.getBookDesc()));
        getAllUserComment();
    }



    @SuppressLint("RestrictedApi")
    @OnClick({R.id.submit_comment, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_comment:
                submitUserComment();
                break;
            case R.id.fab:
                commentListview.setVisibility(View.INVISIBLE);
                submitCommentLayout.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 提交当前用户的评论，并且关联到该动态
     */
    private void submitUserComment() {
        String userComment = commentContent.getText().toString();  //得到用户输入框的评论内容
        if (!"".equals(userComment)) {
            UserEntity user = BmobUser.getCurrentUser(UserEntity.class);  //得到当前用户
            BookEntity post = new BookEntity();   //当前动态内容
            post.setObjectId(bookEntity.getObjectId());  //得到当前的动态的id，与评论建立关联
            final NovelCommentEntity commentEntity = new NovelCommentEntity();
            commentEntity.setContent(userComment);
            commentEntity.setUserEntity(user);
            commentEntity.setBookEntity(post);
//            commentEntity.setImgUrl(user.getHeadImgUrl());   //头像从user实体中取，保证更换头像之后，动态更新
            commentEntity.save(new SaveListener<String>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Toast.makeText(BookCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        commentContent.setText(""); //清空输入框，防止用户二次评论时影响用户体验
                        commentListview.setVisibility(View.VISIBLE);
                        submitCommentLayout.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        getAllUserComment();  //重新加载一遍数据

                    } else {
                        Log.e("评论失败", "xiedongdong");
                    }
                }

            });
        } else {
            Toast.makeText(BookCommentActivity.this, "评论内容不允许为空", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 绑定当前动态下的所有评论信息
     */
    private void getAllUserComment() {
        BmobQuery<NovelCommentEntity> query = new BmobQuery<NovelCommentEntity>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        BookEntity post = new BookEntity();
        post.setObjectId(bookEntity.getObjectId());   //得到当前动态的Id号，
        query.order("updatedAt");
        query.addWhereEqualTo("bookEntity", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("userEntity,BookEntity.userEntity");
        query.findObjects(new FindListener<NovelCommentEntity>() {
            @Override
            public void done(List<NovelCommentEntity> objects, final BmobException e) {
                if (e == null) {
                    //插入数据，通知列表更新
//                    commentDatas = objects;  直接赋值，因为前后绑定的apapter对应的list地址不是同一个，所以 notifyDataSetChanged无效，
                    commentDatas.clear();
                    commentDatas.addAll(objects); //把数据添加进集合
                    commentListview.setLayoutManager(new LinearLayoutManager(BookCommentActivity.this));
                    userCommentAdapter = new UserCommentAdapter(BookCommentActivity.this, commentDatas);
                    commentListview.setAdapter(userCommentAdapter);
                    userCommentAdapter.notifyDataSetChanged();
                    userCommentAdapter.setOnItemLongClickListener(new UserCommentAdapter.OnItemLongClickListener() {
                        @Override
                        public void onLongClick(NovelCommentEntity blogComment) {
                            if (bookEntity.getUserEntity().getObjectId().equals(BmobUser.getCurrentUser(UserEntity.class).getObjectId())) {
                                blogComment.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(BookCommentActivity.this, "删除评论成功...", Toast.LENGTH_SHORT).show();
                                            getAllUserComment();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(BookCommentActivity.this, "只有发布者才能删除评论...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.e("查询数据失败", "xiedongdong");
                }
            }
        });


    }
}
