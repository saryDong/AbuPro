package com.abu.abupro.data;

import com.abu.abupro.data.model.Question;

/**
 *  问题数据源
 */
public interface QuestionDataSource {

    void getRecentQuestionList(LoadCallback<Question> callback,int list_type);

    void getMoreRecentQuestionList(LoadCallback<Question> callback,int list_type);

    void getHotQuestionList(LoadCallback<Question> callback,int list_type);

    void getMoreHotQuestionList(LoadCallback<Question> callback,int list_type);

    void getUserQuestions(String userId, LoadCallback<Question> callback);

    void getMoreUserQuestions(String userId, LoadCallback<Question> callback);

    void refreshUserQuestions(String userId, LoadCallback<Question> callback);

    void getUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void getMoreUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void refreshUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void addQuestion(String title, String des, int REQUEST_TYPE,SaveCallback callback);

}
