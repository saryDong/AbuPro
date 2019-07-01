package com.abu.abupro.ui.fragment;

import android.os.Bundle;


import com.abu.abupro.app.Constant;
import com.abu.abupro.contract.HotAnswerContract;
import com.abu.abupro.data.model.Answer;
import com.abu.abupro.presenter.HotAnswerPresenter;

import java.util.List;

import javax.inject.Inject;

public class HotAnswerFragment extends BaseAnswerFragment implements HotAnswerContract.View{

    @Inject
    HotAnswerPresenter mHotAnswerPresenter;
    private String mQuestionId;

    public static HotAnswerFragment newInstance(String questionId) {
        HotAnswerFragment hotAnswerFragment = new HotAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_QUESTION_ID, questionId);
        hotAnswerFragment.setArguments(bundle);
        return hotAnswerFragment;

    }

    @Override
    protected void init() {
        super.init();
        mHotAnswerPresenter.takeView(this);
        mQuestionId = getArguments().getString(Constant.EXTRA_QUESTION_ID);
        mHotAnswerPresenter.loadHotAnswerByQuestion(mQuestionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHotAnswerPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mHotAnswerPresenter.loadHotAnswerByQuestion(mQuestionId);
    }

    @Override
    protected void startLoadMoreData() {
        mHotAnswerPresenter.loadMoreHotAnswerByQuestion(mQuestionId);
    }

    @Override
    public void onLoadHotAnswerSuccess(List<Answer> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(list);
    }

    @Override
    public void onLoadHotAnswerFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreHotAnswerSuccess(List<Answer> list) {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreHotAnswerFailed() {

    }
}
