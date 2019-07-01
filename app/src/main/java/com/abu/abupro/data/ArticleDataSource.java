package com.abu.abupro.data;


import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.ArticleCollect;

/**
 *  文章数据源
 */
public interface ArticleDataSource {

    void saveArticle(Article article, SaveCallback callback);

    void getArticlesByTag(String tagId, LoadCallback<Article> callback);

    void getMoreArticlesByTag(String tagId, LoadCallback<Article> callback);

    void getUserSharedArticles(String userId, LoadCallback<Article> callback);

    void getMoreUserSharedArticles(String userId, LoadCallback<Article> callback);

    void refreshUserShareArticles(String userId, LoadCallback<Article> callback);

    void getUserFavouredArticles(String userId, LoadCallback<Article> callback);

    void getMoreUserFavouredArticles(String userId, LoadCallback<Article> callback);

    void refreshUserFavouredArticles(String userId, LoadCallback<Article> callback);

    void getArticleCollectes(LoadCallback<ArticleCollect> callback);

    void saveArticleCollectes(String article_collect,LoadCallback<ArticleCollect> callback);
}
