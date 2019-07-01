package com.abu.abupro.presenter;


import com.abu.abupro.contract.ArticleCategoryContract;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.model.Article;

import java.util.List;

import javax.inject.Inject;

public class ArticleCategoryPresenter implements ArticleCategoryContract.Presenter {

    private ArticleCategoryContract.View mView;

    private ArticleDataSource mArticleDataSource;

    @Inject
    ArticleCategoryPresenter(ArticleDataSource articleDataSource) {
        mArticleDataSource = articleDataSource;
    }

    @Override
    public void takeView(ArticleCategoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadArticleByTag(String tag) {
        mArticleDataSource.getArticlesByTag(tag, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                if (mView != null) {
                    mView.onLoadArticleSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadArticleFailed();
                }
            }
        });
    }

    @Override
    public void loadMoreArticleByTag(String tag) {
        mArticleDataSource.getMoreArticlesByTag(tag, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                if (mView != null) {
                    mView.onLoadMoreArticleSuccess();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreArticleFailed();
                }
            }
        });
    }
}
