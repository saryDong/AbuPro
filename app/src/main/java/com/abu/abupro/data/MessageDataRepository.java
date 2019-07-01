package com.abu.abupro.data;

import com.abu.abupro.app.Constant;
import com.abu.abupro.data.model.Message;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @date: 2019/5/16 10:46
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
@Singleton
public class MessageDataRepository implements MessageDataSource{

    private List<Message> mRecentMessagesCache;

    private List<Message> mRecentMoreMessagesCache;

    @Inject
    public MessageDataRepository() {
    }

    @Override
    public void getRencentMessages(LoadCallback<Message> callback) {
        getRecentQuestionListFromServer(callback,false);
    }

    @Override
    public void getMoreRecentMessages(LoadCallback<Message> callback) {
        getRecentQuestionListFromServer(callback,false);
    }

    private void getRecentQuestionListFromServer(final LoadCallback<Message> callback, final boolean b) {
        //获取查询对象
        final AVQuery<Message> questionAVQuery = AVObject.getQuery(Message.class);
        questionAVQuery.limit(Constant.DEFAULT_PAGE_SIZE)//限制结果个数，默认10条数据
                .orderByDescending(AVObject.CREATED_AT);//按创建时间降序

        //查询更多时的处理
        if (b) {
            //查询数据的创建时间小于已有数据列表最后一条数据的创建时间
            questionAVQuery.whereLessThan(AVObject.CREATED_AT, getLastQuestionCreateAt());
        }
        //开始查询
        questionAVQuery.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> list, AVException e) {
                if (e == null) {
                    if (b) {
                        mRecentMoreMessagesCache.addAll(list);
                    } else {
                        mRecentMessagesCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    private Date getLastQuestionCreateAt() {
        return mRecentMoreMessagesCache.get(mRecentMoreMessagesCache.size() - 1).getCreatedAt();
    }
}
