package com.abu.abupro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.abu.abupro.data.model.Question;
import com.abu.abupro.widget.QuestionItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:52
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class QuestionListAdapter extends BaseLoadingListAdapter<Question> {


    public QuestionListAdapter(Context context) {
        super(context);
    }

    public QuestionListAdapter(Context context, List<Question> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new QuestionItemViewHolder(new QuestionItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((QuestionItemView)holder.itemView).bindView(getDataList().get(position));
    }

    private static class QuestionItemViewHolder extends RecyclerView.ViewHolder{

        QuestionItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
