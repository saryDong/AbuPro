package com.abu.abupro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abu.abupro.data.model.Message;
import com.abu.abupro.widget.AnswerItemView;
import com.abu.abupro.widget.MessageListItemView;

import java.util.List;

/**
 * @date: 2019/5/16 14:40
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
public class MessageListAdapter extends BaseLoadingListAdapter<Message> {
    public MessageListAdapter(Context context) {
        this(context,null);
    }

    public MessageListAdapter(Context context, List<Message> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new MessageItemViewHolder(new MessageListItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageListItemView)holder.itemView).bindView(getDataList().get(position));
    }

    private static class MessageItemViewHolder extends RecyclerView.ViewHolder{

        MessageItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
