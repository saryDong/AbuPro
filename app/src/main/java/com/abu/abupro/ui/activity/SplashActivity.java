package com.abu.abupro.ui.activity;

import com.abu.abupro.R;
import com.abu.abupro.data.model.User;
import com.abu.abupro.ui.MainActivity;
import com.avos.avoscloud.AVUser;
/**
 *  @date: 2018/11/20 9:05
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description: 欢迎界面，底部状态栏透明，底部显示产品logo, 在此界面但在停留，检查用户登录状态，已
 *               登录则进入主界面，否则进入登陆界面
 */
public class SplashActivity extends BaseActivity {
    /**
     *  延迟时间
     */
    private static final int DELAY_TIME = 2000;

    @Override
    protected void init() {
        super.init();
        /**
         * 设置状态栏颜色
         */
        setStatusBarTransparent();
        /**
         *  获取当前用户
         */
        User currentUser= AVUser.getCurrentUser(User.class);
        if (currentUser==null){
            navigateToLoginActivity();
        }else {
            navigateToMainActivity();
        }

        /**
         * Activity转场动画，淡入浅出
         */
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    /**
     * 延时跳转到主界面
     */
    private void navigateToMainActivity() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                //跳转到主界面
                navigateTo(MainActivity.class);
            }
        }, DELAY_TIME);
    }

    /**
     * 延时跳转到登陆界面
     */
    private void navigateToLoginActivity() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                //跳转到登陆界面
                navigateTo(LoginActivity.class);

            }
        }, DELAY_TIME);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }
}
