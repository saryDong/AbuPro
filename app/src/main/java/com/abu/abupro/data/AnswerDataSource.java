package com.abu.abupro.data;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.data.model.Question;

/**
 *  回答数据源
 */
public interface AnswerDataSource {

    void getRecentAnswerList(LoadCallback<Answer> callback);

    void getMoreRecentAnswerList(LoadCallback<Answer> callback);

    void getRecentAnswerListByQuestion(String questionId, LoadCallback<Answer> callback);

    void getMoreRecentAnswerListByQuestion(String questionId, LoadCallback<Answer> callback);

    void getHotAnswerListByQuestion(String questionId, LoadCallback<Answer> callback);

    void getMoreHotAnswerListByQuestion(String questionId, LoadCallback<Answer> callback);

    void addAnswerToQuestion(String answer, Question question, SaveCallback callback);

    void getUserAnswerList(String userId, LoadCallback<Answer> callback);

    void getMoreUserAnswerList(String userId, LoadCallback<Answer> callback);

    void refreshUserAnswerList(String userId, LoadCallback<Answer> callback);

    void getUserFavouredAnswers(String userId, LoadCallback<Answer> callback);

    void getMoreUserFavouredAnswers(String userId, LoadCallback<Answer> callback);

    void refreshUserFavouredAnswers(String userId, LoadCallback<Answer> callback);
}
