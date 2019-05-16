package com.zhuandian.bookreader.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 */
public class BookEntity extends BmobObject {
    private long bookId;
    private int bookState;  //1 .在读   2.未读
    private String bookName;
    private String bookDesc;
    private String bookImgUrl;
    private String bookType;


    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookImgUrl() {
        return bookImgUrl;
    }

    public void setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getBookState() {
        return bookState;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }
}
