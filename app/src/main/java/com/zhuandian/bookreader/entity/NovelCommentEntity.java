package com.zhuandian.bookreader.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2019/5/16
 */
public class NovelCommentEntity extends BmobObject {
    private String content;
    private UserEntity userEntity;
    private BookEntity bookEntity;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }
}
