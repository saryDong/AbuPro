package com.abu.abupro.presenter;


import com.abu.abupro.contract.AddQuestionContract;
import com.abu.abupro.data.QuestionDataSource;
import com.abu.abupro.data.SaveCallback;
import com.abu.abupro.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class AddQuestionPresenter implements AddQuestionContract.Presenter {

    private AddQuestionContract.View mView;
    private QuestionDataSource mQuestionDataSource;


    @Inject
    public AddQuestionPresenter(QuestionDataSource questionDataSource) {
        mQuestionDataSource = questionDataSource;
    }

    @Override
    public void takeView(AddQuestionContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void publishQuestion(String title, String des,int addQuestion) {
        mQuestionDataSource.addQuestion(title, des,addQuestion ,new SaveCallback() {
            @Override
            public void onSaveSuccess() {
                mView.onPublishSuccess();
            }

            @Override
            public void onSaveFailed(String errorMsg) {
                mView.onPublishFailed();
            }
        });
    }
}
