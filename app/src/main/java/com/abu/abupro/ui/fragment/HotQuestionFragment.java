package com.abu.abupro.ui.fragment;

import android.view.View;

import com.abu.abupro.adapter.BaseLoadingListAdapter;
import com.abu.abupro.adapter.QuestionListAdapter;
import com.abu.abupro.contract.HotQuestionContract;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.di.FragmentScoped;
import com.abu.abupro.presenter.HotQuestionPresenter;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class HotQuestionFragment extends BaseRefreshableListFragment<Question> implements HotQuestionContract.View{

    @Inject
    HotQuestionPresenter mHotQuestionPresenter;

    @Override
    public BaseLoadingListAdapter onCreateAdapter() {
        return new QuestionListAdapter(getContext(), null);
    }

    @Override
    protected void init() {
        super.init();
        mHotQuestionPresenter.takeView(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mHotQuestionPresenter.loadHotQuestions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHotQuestionPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mHotQuestionPresenter.loadHotQuestions();
    }

    @Override
    protected void startLoadMoreData() {
        mHotQuestionPresenter.loadMoreHotQuestions();
    }

    @Override
    public void onLoadHotQuestionSuccess(List<Question> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(list);
        mError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadHotQuestionFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreHotQuestionSuccess() {

    }

    @Override
    public void onLoadMoreHotQuestionFailed() {

    }
}
