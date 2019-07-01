package com.abu.abupro.contract;

import com.abu.abupro.data.model.Article;

import java.util.List;

public interface ArticleCategoryContract {

    interface View extends BaseView {
        void onLoadArticleSuccess(List<Article> articleList);
        void onLoadArticleFailed();
        void onLoadMoreArticleSuccess();
        void onLoadMoreArticleFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadArticleByTag(String tag);
        void loadMoreArticleByTag(String tag);
    }

}
