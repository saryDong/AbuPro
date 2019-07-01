package com.abu.abupro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.widget.DynamicItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:50
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class DynamicListAdapter extends BaseLoadingListAdapter<Answer> {

    public DynamicListAdapter(Context context) {
        this(context, null);
    }

    private DynamicListAdapter(Context context, List<Answer> list) {
        super(context);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new DynamicItemViewHolder(new DynamicItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DynamicItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class DynamicItemViewHolder extends RecyclerView.ViewHolder {

        DynamicItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
