package com.abu.abupro.data;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.data.model.Comment;

/**
 *  评论数据源
 */
public interface CommentDataSource {

    void sendComment(Answer answer, String replayTo, String comment, SaveCallback callback);

    void loadComments(String commentId, LoadCallback<Comment> callback);

    void loadMoreComments(String commentId, LoadCallback<Comment> loadCallback);
}
