package com.abu.abupro.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.abu.abupro.R;
import com.abu.abupro.adapter.BaseListAdapter;
import com.abu.abupro.adapter.UserArticleListAdapter;
import com.abu.abupro.app.Constant;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.User;
import com.avos.avoscloud.AVUser;

import java.util.List;

import javax.inject.Inject;

public class UserShareArticleActivity extends BaseListViewActivity<Article> {

    @Inject
    ArticleDataSource mArticleDataSource;
    private String mUserId;


    @Override
    protected void startLoadMoreData() {
        mArticleDataSource.getMoreUserSharedArticles(mUserId, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                if (list.size()<=0){
                   // mTextView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }else {
                   // mTextView.setVisibility(View.GONE);
                    mListView.setVisibility(View.INVISIBLE);
                    getAdapter().replaceData(list);
                    getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Log.i("TAGS","  defait"+"df");
            }
        });
    }

    @Override
    protected void startLoadData() {
        initUser();
        mArticleDataSource.getUserSharedArticles(mUserId, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (list!=null&&list.size()>0){
                    //mTextView.setVisibility(View.GONE);
                    mListView.setVisibility(View.INVISIBLE);
                    Log.i("TAG","INVISIBLE"+list.size()+list.get(0).getTag());
                    getAdapter().replaceData(list);
                    getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.i("TAGS","  defaitu"+"df");
            }
        });
    }

    private void initUser() {
        mUserId = AVUser.getCurrentUser(User.class).getObjectId();

        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            setTitle(R.string.my_share);
        }
    }

    @Override
    BaseListAdapter<Article> onCreateAdapter() {
        return new UserArticleListAdapter(this);
    }

    @Override
    protected void startRefresh() {
        mArticleDataSource.refreshUserShareArticles(mUserId, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
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
    protected void onListItemClick(View view, int position) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Article article = getAdapter().getItem(position);
        intent.putExtra(Constant.EXTRA_ARTICLE, article.toString());
        startActivity(intent);
    }
}
