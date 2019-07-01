
package com.abu.abupro.contract;


import com.abu.abupro.data.model.Question;
/**
 *  @date: 2018/11/19 10:55
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public interface AddAnswerContract {

    interface View extends BaseView {
        /**
         *  发布答案成功
         */
        void onPublishAnswerSuccess();

        /**
         *  发布答案失败
         */
        void onPublishAnswerFailed();
    }

    interface Presenter extends BasePresenter<View> {
        /**
         *  发布答案
         * @param answer 答案
         * @param question 问题
         */
        void publishAnswer(String answer, Question question);
    }
}
