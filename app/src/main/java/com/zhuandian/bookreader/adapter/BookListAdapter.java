package com.zhuandian.bookreader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.entity.BookEntity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2019/2/28
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BooKViewHolder> {


    private List<BookEntity> mDatas;
    private Context context;

    public void setStateTextClickListener(OnStateTextClickListener stateTextClickListener) {
        this.stateTextClickListener = stateTextClickListener;
    }

    private OnStateTextClickListener stateTextClickListener;

    public BookListAdapter(List<BookEntity> mDatas, OnStateTextClickListener stateTextClickListener) {
        this.mDatas = mDatas;
        this.stateTextClickListener = stateTextClickListener;
    }

    @NonNull
    @Override
    public BookListAdapter.BooKViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new BooKViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BooKViewHolder viewHolder, final int i) {
        Glide.with(context).load(mDatas.get(i).getBookImgUrl()).into(viewHolder.ivBookImg);
        viewHolder.tvBookName.setText(mDatas.get(i).getBookName());
        viewHolder.tvBookDesc.setText(mDatas.get(i).getBookDesc());
        viewHolder.tvBookTime.setText(mDatas.get(i).getCreatedAt());
        viewHolder.tvBookPrice.setText(mDatas.get(i).getBookPrice()+" 积分");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateTextClickListener != null) {
                    stateTextClickListener.onClick(mDatas.get(i));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class BooKViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_book_img)
        ImageView ivBookImg;
        @BindView(R.id.tv_book_name)
        TextView tvBookName;
        @BindView(R.id.tv_book_desc)
        TextView tvBookDesc;
        @BindView(R.id.tv_book_time)
        TextView tvBookTime;
        @BindView(R.id.tv_book_price)
        TextView tvBookPrice;


        public BooKViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnStateTextClickListener {
        void onClick(BookEntity bookEntity);
    }
}
