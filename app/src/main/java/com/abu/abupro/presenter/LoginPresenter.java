package com.abu.abupro.presenter;

import com.abu.abupro.contract.LoginContract;
import com.abu.abupro.di.ActivityScoped;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import javax.inject.Inject;
/**
 *  @date: 2018/11/20 10:42
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
@ActivityScoped
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    @Inject
    LoginPresenter() {
    }

    @Override
    public void takeView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void login(String userName, String password) {
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    mView.onLoginSuccess();
                } else {
                    handleError(e.getCode());
                }
            }
        });
    }

    private void handleError(int code) {
        switch (code) {
            case AVException.USERNAME_PASSWORD_MISMATCH:
                mView.onUserNamePasswordMismatch();
                break;
            case AVException.USER_DOESNOT_EXIST:
                mView.onUserNameDoesNotExist();
                break;
            default:
                mView.onLoginFailed();
        }
    }
}
