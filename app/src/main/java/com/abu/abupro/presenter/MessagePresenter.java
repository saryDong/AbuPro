package com.abu.abupro.presenter;

import com.abu.abupro.contract.MessageContract;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.MessageDataSource;
import com.abu.abupro.data.model.Message;

import java.util.List;

import javax.inject.Inject;

/**
 * @date: 2019/5/16 10:32
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
public class MessagePresenter implements MessageContract.Presenter {

    private static final String TAG = "RecentQuestionPresenter";

    private MessageContract.View mView;

    private MessageDataSource mDataRepository;

    @Inject
    public MessagePresenter(MessageDataSource dataRepository) {
        mDataRepository = dataRepository;
    }

    @Override
    public void loadRecentMessages() {
        mDataRepository.getRencentMessages(new LoadCallback<Message>() {
            @Override
            public void onLoadSuccess(List<Message> list) {
                if (list!=null&&list.size()>0){
                    mView.onLoadMessageSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                   mView.onLoadMessageFailed();
            }
        });
    }

    @Override
    public void loadMoreRecentMessages() {
        mDataRepository.getMoreRecentMessages(new LoadCallback<Message>() {
            @Override
            public void onLoadSuccess(List<Message> list) {
                mView.onLoadMoreMessageSuccess();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mView.onLoadMoreMessageFailed();
            }
        });
    }

    @Override
    public void takeView(MessageContract.View view) {
          mView=view;
    }

    @Override
    public void dropView() {
          mView=null;
    }
}
