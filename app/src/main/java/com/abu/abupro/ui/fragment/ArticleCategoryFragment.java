package com.abu.abupro.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.abu.abupro.adapter.ArticleListAdapter;
import com.abu.abupro.adapter.BaseLoadingListAdapter;
import com.abu.abupro.app.Constant;
import com.abu.abupro.contract.ArticleCategoryContract;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.di.FragmentScoped;
import com.abu.abupro.presenter.ArticleCategoryPresenter;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class ArticleCategoryFragment extends BaseRefreshableListFragment<Article>
        implements ArticleCategoryContract.View{

    @Inject
    ArticleCategoryPresenter mArticleCategoryPresenter;

    private String mTag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getString(Constant.ARGUMENT_TYPE);
    }

    @Override
    protected BaseLoadingListAdapter<Article> onCreateAdapter() {
        return new ArticleListAdapter(getContext());
    }

    @Override
    protected void init() {
        super.init();
        mArticleCategoryPresenter.takeView(this);
        mArticleCategoryPresenter.loadArticleByTag(mTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mArticleCategoryPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mArticleCategoryPresenter.loadArticleByTag(mTag);
    }

    @Override
    protected void startLoadMoreData() {
        mArticleCategoryPresenter.loadMoreArticleByTag(mTag);
    }

    public static ArticleCategoryFragment newInstance(String tag) {
        ArticleCategoryFragment articleCategoryFragment = new ArticleCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARGUMENT_TYPE, tag);
        articleCategoryFragment.setArguments(bundle);
        return articleCategoryFragment;
    }


    @Override
    public void onLoadArticleSuccess(List<Article> articleList) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (articleList!=null&&articleList.size()>0){
            getAdapter().replaceData(articleList);
        }else {
            mRefreshableRoot.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadArticleFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreArticleSuccess() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreArticleFailed() {

    }
}
