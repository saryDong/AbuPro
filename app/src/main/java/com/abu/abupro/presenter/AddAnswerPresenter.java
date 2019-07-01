package com.abu.abupro.presenter;


import com.abu.abupro.contract.AddAnswerContract;
import com.abu.abupro.data.AnswerDataSource;
import com.abu.abupro.data.SaveCallback;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class AddAnswerPresenter implements AddAnswerContract.Presenter {

    AddAnswerContract.View mView;

    private final AnswerDataSource mAnswerDataRepository;

    @Inject
    public AddAnswerPresenter(AnswerDataSource answerDataSource) {
        mAnswerDataRepository = answerDataSource;
    }

    @Override
    public void takeView(AddAnswerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void publishAnswer(String answer, Question question) {
        mAnswerDataRepository.addAnswerToQuestion(answer, question, mSaveCallback);
    }


    private SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSaveSuccess() {
            mView.onPublishAnswerSuccess();
        }

        @Override
        public void onSaveFailed(String errorMsg) {
            mView.onPublishAnswerSuccess();
        }
    };
}
