package com.abu.abupro.ui.fragment;

import android.os.Bundle;
import android.util.Log;


import com.abu.abupro.app.Constant;
import com.abu.abupro.contract.RecentAnswerContract;
import com.abu.abupro.data.model.Answer;
import com.abu.abupro.presenter.RecentAnswerPresenter;

import java.util.List;

import javax.inject.Inject;

public class RecentAnswerFragment extends BaseAnswerFragment implements RecentAnswerContract.View{

    private static final String TAG = "RecentAnswerFragment";

    @Inject
    RecentAnswerPresenter mRecentAnswerPresenter;

    private String mQuestionId;

    public static RecentAnswerFragment newInstance(String questionId) {
        RecentAnswerFragment recentAnswerFragment = new RecentAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_QUESTION_ID, questionId);
        recentAnswerFragment.setArguments(bundle);
        return recentAnswerFragment;

    }


    @Override
    protected void init() {
        super.init();
        mRecentAnswerPresenter.takeView(this);
        mQuestionId = getArguments().getString(Constant.EXTRA_QUESTION_ID);
        mRecentAnswerPresenter.loadRecentAnswerByQuestion(mQuestionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecentAnswerPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mRecentAnswerPresenter.loadRecentAnswerByQuestion(mQuestionId);
    }

    @Override
    protected void startLoadMoreData() {
        Log.d(TAG, "startLoadMoreData: ");
        mRecentAnswerPresenter.loadMoreRecentAnswerByQuestion(mQuestionId);
    }

    @Override
    public void onLoadRecentAnswerSuccess(List<Answer> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(list);
    }

    @Override
    public void onLoadRecentAnswerFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRecentAnswerSuccess(List<Answer> answers) {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRecentAnswerFailed() {
        Log.d(TAG, "onLoadMoreRecentAnswerFailed: ");
    }
}
