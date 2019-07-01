package com.abu.abupro.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;


import com.abu.abupro.data.model.Article;
import com.abu.abupro.widget.UserShareArticleItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:52
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class UserArticleListAdapter extends BaseListAdapter<Article>{



    public UserArticleListAdapter(Context context) {
        super(context);
    }

    public UserArticleListAdapter(Context context, List<Article> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserShareArticleItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserShareArticleItemView)itemView).bindView(getDataList().get(position));
    }
}
