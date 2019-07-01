package com.abu.abupro.presenter;


import com.abu.abupro.contract.HotQuestionContract;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.QuestionDataSource;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.di.ChildFragmentScope;

import java.util.List;

import javax.inject.Inject;

@ChildFragmentScope
public class JobInfoPresenter implements HotQuestionContract.Presenter {

    private QuestionDataSource mDataRepository;
    private HotQuestionContract.View mView;

    @Inject
    public JobInfoPresenter(QuestionDataSource dataRepository) {
        mDataRepository = dataRepository;
    }

    @Override
    public void takeView(HotQuestionContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadHotQuestions() {
        mDataRepository.getHotQuestionList(new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                if (mView != null&&list.size()>0) {
                    mView.onLoadHotQuestionSuccess(list);
                }else {
                    mView.onLoadHotQuestionFailed();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadHotQuestionFailed();
                }
            }
        },Question.JOB_INFO);
    }

    @Override
    public void loadMoreHotQuestions() {
        mDataRepository.getMoreHotQuestionList(new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                if (mView != null) {
                    mView.onLoadMoreHotQuestionSuccess();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreHotQuestionFailed();
                }
            }
        },Question.JOB_INFO);
    }
}
