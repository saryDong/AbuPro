package com.abu.abupro.contract;
/**
 *  @date: 2018/11/19 10:57
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public interface AddQuestionContract {


    interface View extends BaseView {

        void onPublishSuccess();
        void onPublishFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void publishQuestion(String title, String des,int addQuestion);
    }
}
