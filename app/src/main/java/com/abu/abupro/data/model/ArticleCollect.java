package com.abu.abupro.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * @date: 2019/5/19 15:13
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
@AVClassName("ArticleCollect")
public class ArticleCollect extends AVObject{
    public static final String ARTICLE_COLLECT="article_collect";
    public static final String USER = "user";

    public String getArticleCollect(){
        return getString(ARTICLE_COLLECT);
    }

    public void setArticleCollect(String string){
        put(ARTICLE_COLLECT,string);
        saveInBackground();
    }

    public User getUser() {
        return getAVUser(USER,User.class);
    }

    public void setUser(AVUser user){
        put(USER,user);
    }

}
