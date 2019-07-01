package com.abu.abupro.contract;

import com.abu.abupro.data.model.Message;

import java.util.List;

/**
 * @date: 2019/5/16 10:27
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
public interface MessageContract {

    interface View extends BaseView {
        void onLoadMessageSuccess(List<Message> articleList);
        void onLoadMessageFailed();
        void onLoadMoreMessageSuccess();
        void onLoadMoreMessageFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadRecentMessages();
        void loadMoreRecentMessages();
    }
}
