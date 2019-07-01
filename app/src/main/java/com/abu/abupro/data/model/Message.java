package com.abu.abupro.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * @date: 2019/5/16 9:21
 * @author: 董长峰
 * @blog: https://www.jianshu.com/u/04a705fae99b
 * @description:
 */
@AVClassName("Message")
public class Message extends AVObject {

    public static final String CONTENT = "content";

    public static final String USER = "user";

    public static final String IMAGE="image";

    public static final String TITLE="title";

    public Message(){
    }
    public  String getContent() {
        return getString(CONTENT);
    }

    public void setContent(String content){
        put(CONTENT,content);
    }

    public User getUser() {
        return getAVUser(USER,User.class);
    }

    public void setUser(AVUser user){
        put(USER,user);
    }

    public void setImage(String image){
        put(IMAGE,image);
    }

    public String getImage(){
        return getString(IMAGE);
    }

    public String getTitle(){
        return getString(TITLE);
    }

    public void setTitle(String title){
        put(TITLE,title);
    }



}
