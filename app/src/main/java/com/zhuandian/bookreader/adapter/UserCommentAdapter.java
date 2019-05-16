package com.zhuandian.bookreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.bookreader.R;
import com.zhuandian.bookreader.entity.BookEntity;
import com.zhuandian.bookreader.entity.NovelCommentEntity;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2019/4/22
 */
public class UserCommentAdapter extends BaseAdapter<NovelCommentEntity, BaseViewHolder> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public UserCommentAdapter(Context context, List<NovelCommentEntity> mDatas) {
        super(mDatas, context);
    }


    @Override
    protected void converData(BaseViewHolder holder, final NovelCommentEntity comment, int position) {
        ButterKnife.bind(this, holder.itemView);
        String createtTime[] = comment.getCreatedAt().split(" ");
        String currentTime[] = currentTime().split(" ");

        //判断创建时间跟当前时间是否同一天，是，只显示时间，不是，显示创建的日期，不显示时间
        if (createtTime[0].equals(currentTime[0])) {
            String createtTime1[] = createtTime[1].split(":");
            time.setText("今天 " + createtTime1[0] + ":" + createtTime1[1]);
        } else {
            String createtTime1[] = createtTime[0].split("-");  //正则切割月份
            String createtTime2[] = createtTime[1].split(":");  //正则切割时间
            time.setText(createtTime1[1] + "/" + createtTime1[2] + " " + createtTime2[0] + ":" + createtTime2[1]);

        }
        name.setText(comment.getUserEntity().getUsername());   //设置评论者信息
        content.setText(comment.getContent());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(comment);
                }

                return true;
            }
        });

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.comment_listview_item;
    }


    public interface OnItemLongClickListener {
        void onLongClick(NovelCommentEntity blogComment);
    }


    public  String currentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }
}
