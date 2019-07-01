package com.abu.abupro.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.abu.abupro.R;
import com.abu.abupro.app.Constant;
import com.abu.abupro.contract.LoginContract;
import com.abu.abupro.di.DaggerAppComponent;
import com.abu.abupro.presenter.LoginPresenter;
import com.abu.abupro.ui.MainActivity;
import com.abu.abupro.utils.PermissionUtils;
import com.abu.abupro.utils.RegexUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *  @date: 2018/11/20 9:14
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.register)
    Button mRegister;
    @BindView(R.id.login)
    Button mLogin;

    @Inject
    LoginPresenter mLoginPresenter;
    @BindView(R.id.user_name_input_layout)
    TextInputLayout mUserNameInputLayout;
    @BindView(R.id.password_input_layout)
    TextInputLayout mPasswordInputLayout;
    @BindView(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
        /**
         *  设置软键盘监听事件
         */
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                login();
                return true;
            }
        });
        /**
         *  检查权限并进行权限申请
         */
        PermissionUtils.checkPermissions(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.dropView();
    }

    @OnClick({R.id.register, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                navigateForResultTo(RegisterActivity.class, Constant.REQUEST_CODE);
                break;
            case R.id.login:
                login();
                break;
                default:
        }
    }

    private void login() {
        hideSoftKeyboard();
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (checkUserName(userName)) {
            if (checkPassword(password)) {
                mProgressLayout.setVisibility(View.VISIBLE);
                mLoginPresenter.login(userName, password);
            }
        }
    }

    /**
     *  密码检验
     * @param password 密码
     * @return 是否合格
     */
    private boolean checkPassword(String password) {
        if (password.length() == 0) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError(getString(R.string.error_password_empty));
            return false;
        } else if (!RegexUtils.isValidPassword(password)) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError(getString(R.string.error_password));
            return false;
        }
        return true;
    }
    /**
     *  密码用户名
     * @param userName 用户名
     * @return 是否合格
     */
    private boolean checkUserName(String userName) {
        if (userName.length() == 0) {
            mUserNameInputLayout.setErrorEnabled(true);
            mUserNameInputLayout.setError(getString(R.string.error_user_name_empty));
            return false;
        } else if (!RegexUtils.isValidUserName(userName)) {
            mUserNameInputLayout.setErrorEnabled(true);
            mUserNameInputLayout.setError(getString(R.string.error_user_name));
            return false;
        }
        return true;
    }

    /**
     *   Activity返回值
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data  返回结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            mUserName.setText(data.getStringExtra(Constant.EXTRA_USER_NAME));
            mPassword.setText(data.getStringExtra(Constant.EXTRA_PASSWORD));
        }
    }


    @Override
    public void onLoginSuccess() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        navigateTo(MainActivity.class);
    }

    @Override
    public void onLoginFailed() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUserNamePasswordMismatch() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.user_name_password_not_match), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNameDoesNotExist() {
        mProgressLayout.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.user_name_does_not_exist), Toast.LENGTH_SHORT).show();
    }
}
