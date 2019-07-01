package com.abu.abupro.ui.fragment;

import com.abu.abupro.adapter.BaseLoadingListAdapter;
import com.abu.abupro.adapter.DynamicListAdapter;
import com.abu.abupro.contract.DynamicContract;
import com.abu.abupro.data.model.Answer;
import com.abu.abupro.di.FragmentScoped;
import com.abu.abupro.presenter.DynamicPresenter;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class DynamicFragment extends BaseRefreshableListFragment<Answer> implements DynamicContract.View{

    @Inject
    DynamicPresenter mDynamicPresenter;

    @Override
    public BaseLoadingListAdapter<Answer> onCreateAdapter() {
        return new DynamicListAdapter(getContext());
    }

    @Override
    protected void init() {
        super.init();
        mDynamicPresenter.takeView(this);
        mDynamicPresenter.loadRecentAnswer();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDynamicPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mDynamicPresenter.loadRecentAnswer();
    }

    @Override
    protected void startLoadMoreData() {
        mDynamicPresenter.loadMoreRecentAnswer();
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
    public void onLoadMoreRecentAnswerSuccess(List<Answer> list) {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRecentAnswerFailed() {

    }
}
