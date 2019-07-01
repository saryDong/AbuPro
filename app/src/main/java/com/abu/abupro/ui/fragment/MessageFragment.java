package com.abu.abupro.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abu.abupro.adapter.BaseLoadingListAdapter;
import com.abu.abupro.adapter.MessageListAdapter;
import com.abu.abupro.contract.MessageContract;
import com.abu.abupro.data.model.Message;
import com.abu.abupro.di.ActivityScoped;
import com.abu.abupro.di.FragmentScoped;
import com.abu.abupro.presenter.MessagePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * @date: 2019/5/16 9:09
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
@ActivityScoped
public class MessageFragment extends BaseRefreshableListFragment<Message> implements MessageContract.View{

    private static final String TAG = "MessageFragment";

    @Inject
    MessagePresenter mMessagePresenter;

    @Inject
    public MessageFragment() {
    }

    @Override
    protected void init() {
        super.init();
        mToolbar.setVisibility(View.VISIBLE);
        title.setText("IT资讯");
        mMessagePresenter.takeView(this);
        mMessagePresenter.loadRecentMessages();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMessagePresenter.dropView();
    }

    @Override
    protected BaseLoadingListAdapter<Message> onCreateAdapter() {
        return new MessageListAdapter(getContext());
    }

    @Override
    protected void startRefresh() {
       // mMessagePresenter.loadRecentMessages();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void startLoadMoreData() {
        mMessagePresenter.loadMoreRecentMessages();
    }

    @Override
    public void onLoadMessageSuccess(List<Message> articleList) {
        mError.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(articleList);
    }

    @Override
    public void onLoadMessageFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreMessageSuccess() {
        // getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreMessageFailed() {
        // getAdapter().notifyDataSetChanged();
    }
}
