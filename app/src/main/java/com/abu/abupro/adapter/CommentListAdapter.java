package com.abu.abupro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abu.abupro.data.model.Comment;
import com.abu.abupro.widget.CommentItemView;
import com.abu.abupro.widget.LoadingView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:49
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class CommentListAdapter extends BaseLoadingListAdapter<Comment> {


    public CommentListAdapter(Context context) {
        super(context);
    }

    public CommentListAdapter(Context context, List<Comment> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new CommentListItemViewHolder(new CommentItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommentItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class CommentListItemViewHolder extends RecyclerView.ViewHolder {

        CommentListItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    protected LoadingViewHolder onCreateLoadingViewHolder() {
        return new LoadingViewHolder(new LoadingView(getContext()));
    }
}
