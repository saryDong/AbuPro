package com.abu.abupro.data;

import com.abu.abupro.data.model.Message;

/**
 * @date: 2019/5/16 10:46
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
public interface MessageDataSource {
       void getRencentMessages(LoadCallback<Message> callback);
       void getMoreRecentMessages(LoadCallback<Message> callback);
}
