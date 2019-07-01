package com.abu.abupro.contract;

public interface RegisterContract {

    interface View extends BaseView {
        /**
         *  初测成功
         */
        void onRegisterSuccess();

        /**
         *  注册失败
         */
        void onRegisterFailed();

        /**
         *  用户名被占用
         */
        void onUserNameTaken();
    }

    interface Presenter extends BasePresenter<View> {
        /**
         *  注册方法
         * @param email  邮件
         * @param password 密码
         */
        void register(String email, String password);
    }
}
