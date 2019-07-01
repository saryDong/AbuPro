package com.abu.abupro.contract;


import com.abu.abupro.data.model.Answer;
import com.abu.abupro.data.model.Comment;

import java.util.List;

public interface CommentContract {

    interface View extends BaseView{

        void onSendCommentSuccess();
        void onSendCommentFailed();

        void onLoadCommentsSuccess(List<Comment> comments);
        void onLoadCommentFailed();

        void onLoadMoreCommentsSuccess();
        void onLoadMoreCommentFailed();

    }

    interface Presenter extends BasePresenter<View>{

        void loadComments(String answerId);

        void loadMoreComments(String answerId);

        void sendComment(Answer answer, String replayTo, String comment);
    }
}
