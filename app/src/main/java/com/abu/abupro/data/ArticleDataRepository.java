package com.abu.abupro.data;

import android.util.Log;
import android.util.SparseArray;

import com.abu.abupro.app.Constant;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.ArticleCollect;
import com.abu.abupro.data.model.User;
import com.abu.abupro.data.model.UserArticleFavourMap;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArticleDataRepository implements ArticleDataSource {


    //private SparseArray<List<Article>> mArticleByTagCache = new SparseArray<>();

    private Map<String,List<Article>> mArticleByTagCache=new HashMap<>();

    private List<Article> mArticlesCache;

    private List<Article> mUserFavouredArticlesCache;

    private List<UserArticleFavourMap> mUserArticleFavourMaps;

    @Inject
    public ArticleDataRepository() {
    }

    @Override
    public void saveArticle(Article article, final SaveCallback callback) {
        article.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.onSaveSuccess();
                } else {
                    callback.onSaveFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getArticlesByTag(String tagId, LoadCallback<Article> callback) {
        getArticlesFromServer(tagId, callback, false);
    }

    private void getArticlesFromServer(final String tagId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        final AVQuery<Article> articleAVQuery;
        Log.i("TAG",tagId);
        if ("java".equals(tagId)||"kotlin".equals(tagId)){
            Log.i("TAG","进来了");
            //获取文章查询对象
            articleAVQuery= AVQuery.getQuery(Article.class);
            User user= (User) AVUser.getCurrentUser();
            //配置查询条件
            articleAVQuery.include(Article.USER)
                    .selectKeys(Arrays.asList(Article.TITLE, Article.DESC,
                            Article.FAVOUR_COUNT, Article.TAG, Article.URL,Article.USER,
                            Article.USER_NAME, Article.USER_AVATAR))
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .orderByDescending(Article.CREATED_AT);
        }else {
            //获取文章查询对象
            articleAVQuery = AVQuery.getQuery(Article.class);
            User user= (User) AVUser.getCurrentUser();
            //配置查询条件
            articleAVQuery.include(Article.USER)
                    .selectKeys(Arrays.asList(Article.TITLE, Article.DESC,
                            Article.FAVOUR_COUNT, Article.TAG, Article.URL,
                            Article.USER_NAME, Article.USER_AVATAR))
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .whereEqualTo(Article.USER,user)
                    .orderByDescending(Article.CREATED_AT);
        }
        //如果是热门标签，则按文章点赞数排序，以创建时间为第二排序条件，并且点赞次数大于0
       /* if (tagId == Article.TAG_HOT) {
            articleAVQuery.orderByDescending(Article.FAVOUR_COUNT)
                    .addDescendingOrder(Article.CREATED_AT)
                    .whereGreaterThan(Article.FAVOUR_COUNT, 0);
        } else {//其他标签，以文章创建时间排序*/

       // }
        //加载更多数据的处理
        if (isLoadMore) {
            articleAVQuery.whereLessThan(Article.CREATED_AT, getLastTagArticleCreatedAt(tagId));

        }
        //开始查询
        articleAVQuery.findInBackground(new FindCallback<Article>() {
            @Override
            public void done(List<Article> list, AVException e) {
                if (e == null&&list!=null) {
                    for (int i=0;i<list.size();i++){
                        if(!mArticleByTagCache.containsKey(tagId)){
                            mArticleByTagCache.put(tagId,new ArrayList<Article>());
                        }
                        if(list.get(i).getTag().equals(tagId)&&mArticleByTagCache.get(tagId)!=null&&!mArticleByTagCache.get(tagId).contains(list.get(i))){
                            mArticleByTagCache.get(tagId).add(list.get(i));
                        }
                    }

                    callback.onLoadSuccess(mArticleByTagCache.get(tagId));
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }
    private void getArticlesFromServer(final String tagId, final LoadCallback<Article> callback) {
        //获取文章查询对象
        final AVQuery<Article> articleAVQuery = AVQuery.getQuery(Article.class);
        //配置查询条件
        articleAVQuery.include(Article.USER)
                .selectKeys(Arrays.asList(Article.TITLE, Article.DESC,
                        Article.FAVOUR_COUNT, Article.TAG, Article.URL,
                        Article.USER_NAME, Article.USER_AVATAR))
                .limit(Constant.DEFAULT_PAGE_SIZE)
                .whereEqualTo(Article.USER,"5ce0ee5917b54d006829bf32")
                .orderByDescending(Article.CREATED_AT);
        //如果是热门标签，则按文章点赞数排序，以创建时间为第二排序条件，并且点赞次数大于0
       /* if (tagId == Article.TAG_HOT) {
            articleAVQuery.orderByDescending(Article.FAVOUR_COUNT)
                    .addDescendingOrder(Article.CREATED_AT)
                    .whereGreaterThan(Article.FAVOUR_COUNT, 0);
        } else {//其他标签，以文章创建时间排序*/

        // }
        //加载更多数据的处理

        //开始查询
        articleAVQuery.findInBackground(new FindCallback<Article>() {
            @Override
            public void done(List<Article> list, AVException e) {
                if (e == null&&list!=null) {
                    for (int i=0;i<list.size();i++){
                        if(!mArticleByTagCache.containsKey(tagId)){
                            mArticleByTagCache.put(tagId,new ArrayList<Article>());
                        }
                        if(list.get(i).getTag().equals(tagId)&&mArticleByTagCache.get(tagId)!=null&&!mArticleByTagCache.get(tagId).contains(list.get(i))){
                            mArticleByTagCache.get(tagId).add(list.get(i));
                        }
                    }

                    callback.onLoadSuccess(mArticleByTagCache.get(tagId));
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreArticlesByTag(final String tagId, final LoadCallback<Article> callback) {
        getArticlesFromServer(tagId, callback, true);
    }

    private Date getLastTagArticleCreatedAt(String tagId) {
        List<Article> articles = mArticleByTagCache.get(tagId);
        return articles.get(articles.size() - 1).getCreatedAt();
    }


    @Override
    public void getUserSharedArticles(String userId, final LoadCallback<Article> callback) {
        getUserSharedArticlesFromServer(userId, callback, false);
    }

    private void getUserSharedArticlesFromServer(String userId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        AVQuery<Article> articleAVQuery = AVQuery.getQuery(Article.class);
        try {
            User user = AVObject.createWithoutData(User.class, userId);
            articleAVQuery.whereEqualTo(Article.USER, user)
                    .orderByDescending(Article.CREATED_AT)
                    .limit(Constant.DEFAULT_PAGE_SIZE);
            if (isLoadMore) {
                articleAVQuery.whereLessThan(Article.CREATED_AT, getLastArticleCreatedAt());
            }
            articleAVQuery.findInBackground(new FindCallback<Article>() {
                @Override
                public void done(List<Article> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mArticlesCache.addAll(list);
                        } else {
                            mArticlesCache = list;
                        }
                        callback.onLoadSuccess(list);
                    } else {
                        callback.onLoadFailed(e.getLocalizedMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMoreUserSharedArticles(String userId, final LoadCallback<Article> callback) {
        getUserSharedArticlesFromServer(userId, callback, true);
    }

    @Override
    public void refreshUserShareArticles(String userId, LoadCallback<Article> callback) {
        if (mArticlesCache != null) {
            mArticlesCache.clear();
        }
        getUserSharedArticles(userId, callback);
    }

    public Date getLastArticleCreatedAt() {
        return mArticlesCache.get(mArticlesCache.size() - 1).getCreatedAt();
    }

    @Override
    public void getUserFavouredArticles(String userId, final LoadCallback<Article> callback) {
        getUserFavouredArticlesFromServer(userId, callback, false);
    }

    private void getUserFavouredArticlesFromServer(String userId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        AVQuery<UserArticleFavourMap> query = AVQuery.getQuery(UserArticleFavourMap.class);
        try {
            AVObject avObject = AVObject.createWithoutData(User.class, userId);
            query.whereEqualTo(UserArticleFavourMap.USER, avObject)
                    .include(UserArticleFavourMap.ARTICLE)
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .orderByDescending(UserArticleFavourMap.CREATED_AT);
            if (isLoadMore) {
                query.whereLessThan(UserArticleFavourMap.CREATED_AT, getLastUserArticleFavourMapCreatedAt());
            }
            query.findInBackground(new FindCallback<UserArticleFavourMap>() {
                @Override
                public void done(List<UserArticleFavourMap> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserArticleFavourMaps.addAll(list);
                        } else {
                            mUserArticleFavourMaps = list;
                            mUserFavouredArticlesCache = new ArrayList<>();
                        }

                        for (int i = 0; i < list.size(); i++) {
                            try {
                                Article article = list.get(i).getAVObject("article", Article.class);
                                mUserFavouredArticlesCache.add(article);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                callback.onLoadFailed(e1.getLocalizedMessage());
                            }
                        }
                        callback.onLoadSuccess(mUserFavouredArticlesCache);
                    } else {
                        callback.onLoadFailed(e.getLocalizedMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
            callback.onLoadFailed(e.getLocalizedMessage());
        }
    }

    @Override
    public void refreshUserFavouredArticles(String userId, LoadCallback<Article> callback) {
        if (mUserFavouredArticlesCache != null) {
            mUserFavouredArticlesCache.clear();
        }
        getUserFavouredArticles(userId, callback);
    }

    @Override
    public void getArticleCollectes(final LoadCallback<ArticleCollect> callback) {
        com.abu.abupro.data.model.User user = AVUser.getCurrentUser(com.abu.abupro.data.model.User.class);
        final AVQuery<ArticleCollect> answerAVQuery = AVObject.getQuery(ArticleCollect.class);
        answerAVQuery.whereEqualTo(ArticleCollect.USER,user);
        answerAVQuery.include(ArticleCollect.USER)
                .selectKeys(Arrays.asList(ArticleCollect.ARTICLE_COLLECT))
                .limit(Constant.DEFAULT_PAGE_SIZE);
        answerAVQuery.findInBackground(new FindCallback<ArticleCollect>() {
            @Override
            public void done(List<ArticleCollect> list, AVException e) {
                if (list!=null&&list.size()>0){
                    callback.onLoadSuccess(list);
                }else {
                    callback.onLoadFailed("查询失败");
                }
            }
        });
    }

    @Override
    public void saveArticleCollectes(String article_collect, LoadCallback<ArticleCollect> callback) {

    }

    @Override
    public void getMoreUserFavouredArticles(String userId, LoadCallback<Article> callback) {
        getUserFavouredArticlesFromServer(userId, callback, true);
    }

    public Date getLastUserArticleFavourMapCreatedAt() {
        return mUserArticleFavourMaps.get(mUserArticleFavourMaps.size() - 1).getCreatedAt();
    }
}
