package com.abu.abupro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.widget.AnswerItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:43
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class AnswerListAdapter extends BaseLoadingListAdapter<Answer> {

    public AnswerListAdapter(Context context) {
        this(context, null);
    }

    private AnswerListAdapter(Context context, List<Answer> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new AnswerItemViewHolder(new AnswerItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AnswerItemView)holder.itemView).bindView(getDataList().get(position));
    }

    private static class AnswerItemViewHolder extends RecyclerView.ViewHolder{
        AnswerItemViewHolder(View itemView) {
            super(itemView);
        }
    }


}
