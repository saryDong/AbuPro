package com.abu.abupro.contract;


import com.abu.abupro.data.model.Answer;

import java.util.List;

public interface HotAnswerContract {

    interface View extends BaseView {

        void onLoadHotAnswerSuccess(List<Answer> list);

        void onLoadHotAnswerFailed();

        void onLoadMoreHotAnswerSuccess(List<Answer> list);

        void onLoadMoreHotAnswerFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadHotAnswerByQuestion(String questionId);

        void loadMoreHotAnswerByQuestion(String questionId);
    }
}

