package com.abu.abupro.presenter;

import com.abu.abupro.contract.RecentQuestionContract;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.QuestionDataSource;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.di.ChildFragmentScope;

import java.util.List;

import javax.inject.Inject;

@ChildFragmentScope
public class RecentQuestionPresenter implements RecentQuestionContract.Presenter {

    private static final String TAG = "RecentQuestionPresenter";

    private RecentQuestionContract.View mView;

    private QuestionDataSource mDataRepository;


    @Inject
    public RecentQuestionPresenter(QuestionDataSource dataRepository){
        mDataRepository = dataRepository;
    }

    @Override
    public void takeView(RecentQuestionContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadRecentQuestions() {
        mDataRepository.getRecentQuestionList(new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                if (mView != null&&list.size()>0) {
                    mView.onLoadRecentQuestionSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadRecentQuestionFailed();
                }
            }
        },Question.QUESTION);
    }

    @Override
    public void loadMoreRecentQuestions() {
       mDataRepository.getMoreRecentQuestionList(new LoadCallback<Question>() {
           @Override
           public void onLoadSuccess(List<Question> list) {
               mView.onLoadMoreRecentQuestionSuccess();
           }

           @Override
           public void onLoadFailed(String errorMsg) {
               mView.onLoadMoreRecentQuestionFailed();
           }
       },Question.QUESTION);
    }





}
