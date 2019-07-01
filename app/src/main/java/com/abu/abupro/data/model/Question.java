package com.abu.abupro.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Question")
public class Question extends AVObject {
    public static final int QUESTION=2;
    public static final int INTERVIEW=3;
    public static final int JOB_INFO=4;

    public static final String FAVOUR_COUNT = "favour_count";

    public static final String ANSWER_COUNT = "answer_count";

    public static final String TITLE = "title";

    public static final String DESC = "desc";

    public static final String USER = "user";

    public static final String USER_NAME = "user.username";

    public static final String USER_AVATAR = "user.avatar";

    public static final String LIST_TYPE="list_type";

    public void setTitle(String title) {
        put(TITLE, title);
    }

    public void setDescription(String desc) {
        put(DESC, desc);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getDescription() {
        return getString(DESC);
    }

    public void setListType(int type){
        put(LIST_TYPE,type);
    }

    public int getListType(){
       return getInt(LIST_TYPE);
    }


    public void addAnswerCount() {
        increment(ANSWER_COUNT);
        saveInBackground();
    }

    public int getAnswerCount() {
        return getInt(ANSWER_COUNT);
    }

    public void addFavourCount() {
        increment(FAVOUR_COUNT);
        saveInBackground();
    }

    public void minusFavourCount() {
        increment(FAVOUR_COUNT, -1);
        saveInBackground();
    }

    public int getFavourCount() {
        return getInt(FAVOUR_COUNT);
    }

    public void setUser(User dependent) {
        put(USER, dependent);
    }

    public User getUser() {
        return getAVUser(USER, User.class);
    }

}
