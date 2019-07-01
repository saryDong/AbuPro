package com.abu.abupro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.abu.abupro.data.model.Article;
import com.abu.abupro.widget.ArticleItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:43
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class ArticleListAdapter extends BaseLoadingListAdapter<Article> {

    public ArticleListAdapter(Context context) {
        this(context, null);
    }

    private ArticleListAdapter(Context context, List<Article> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new ArticleItemViewHolder(new ArticleItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ArticleItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class ArticleItemViewHolder extends RecyclerView.ViewHolder{

        ArticleItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
