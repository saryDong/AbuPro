package com.abu.abupro.adapter;

import android.content.Context;
import android.view.View;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.widget.UserAnswerItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:52
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class UserAnswerAdapter extends BaseListAdapter<Answer>{


    public UserAnswerAdapter(Context context) {
        super(context);
    }

    public UserAnswerAdapter(Context context, List<Answer> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserAnswerItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserAnswerItemView)itemView).bindView(getDataList().get(position), position);
    }
}
