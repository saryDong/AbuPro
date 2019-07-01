package com.abu.abupro.ui.fragment;


import com.abu.abupro.adapter.AnswerListAdapter;
import com.abu.abupro.adapter.BaseLoadingListAdapter;
import com.abu.abupro.data.model.Answer;

public abstract class BaseAnswerFragment extends BaseRefreshableListFragment<Answer> {

    @Override
    protected BaseLoadingListAdapter<Answer> onCreateAdapter() {
        return new AnswerListAdapter(getContext());
    }

    @Override
    public boolean isEnableScrollEvent() {
        return false;
    }

}
