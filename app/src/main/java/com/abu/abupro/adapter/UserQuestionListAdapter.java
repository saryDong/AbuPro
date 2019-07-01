package com.abu.abupro.adapter;

import android.content.Context;
import android.view.View;

import com.abu.abupro.data.model.Question;
import com.abu.abupro.widget.UserQuestionItemView;

import java.util.List;
/**
 *  @date: 2018/11/19 10:53
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class UserQuestionListAdapter extends BaseListAdapter<Question>{

    public UserQuestionListAdapter(Context context) {
        super(context);
    }

    public UserQuestionListAdapter(Context context, List<Question> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserQuestionItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserQuestionItemView)itemView).bindView(getDataList().get(position), position);
    }
}
