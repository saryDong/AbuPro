package com.abu.abupro.contract;


import com.abu.abupro.data.model.Answer;

import java.util.List;

public interface DynamicContract {

    interface View extends BaseView {

        void onLoadRecentAnswerSuccess(List<Answer> list);

        void onLoadRecentAnswerFailed();

        void onLoadMoreRecentAnswerSuccess(List<Answer> list);

        void onLoadMoreRecentAnswerFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadRecentAnswer();

        void loadMoreRecentAnswer();
    }
}

