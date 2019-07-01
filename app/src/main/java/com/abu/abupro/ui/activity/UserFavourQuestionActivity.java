package com.abu.abupro.ui.activity;

import android.view.View;

import com.abu.abupro.R;
import com.abu.abupro.adapter.BaseListAdapter;
import com.abu.abupro.adapter.UserQuestionListAdapter;
import com.abu.abupro.app.Constant;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.QuestionDataSource;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.data.model.User;
import com.abu.abupro.utils.TransitionUtils;
import com.avos.avoscloud.AVUser;
import java.util.List;

import javax.inject.Inject;

public class UserFavourQuestionActivity extends BaseListViewActivity<Question> {

    @Inject
    QuestionDataSource mQuestionDataSource;
    private String mUserId;

    private void initUser() {
        mUserId = getIntent().getStringExtra(Constant.EXTRA_USER_ID);
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User.class).getObjectId();
        }
        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            setTitle(R.string.my_favour_questions);
        } else {
            setTitle(R.string.his_favour_questions);
        }
    }

    @Override
    protected void startRefresh() {
        mQuestionDataSource.refreshUserFavouredQuestions(mUserId, new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                getAdapter().replaceData(list);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void startLoadMoreData() {
        mQuestionDataSource.getMoreUserFavouredQuestions(mUserId, new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errorMsg) {

            }
        });
    }

    @Override
    protected void startLoadData() {
        initUser();
        mQuestionDataSource.getUserFavouredQuestions(mUserId, new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                getAdapter().replaceData(list);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    BaseListAdapter<Question> onCreateAdapter() {
        return new UserQuestionListAdapter(this, null);
    }

    @Override
    protected void onListItemClick(View view, int position) {
        View title = view.findViewById(R.id.question_title);
        View desc = view.findViewById(R.id.question_description);
        Question question = getAdapter().getItem(position);
        TransitionUtils.transitionToQuestionDetail(this, question, title, desc);
    }
}
