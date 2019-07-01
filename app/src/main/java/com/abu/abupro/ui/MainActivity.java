package com.abu.abupro.ui;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.abu.abupro.R;
import com.abu.abupro.event.LogoutEvent;
import com.abu.abupro.event.ScrollEvent;
import com.abu.abupro.ui.activity.BaseActivity;
import com.abu.abupro.ui.fragment.ArticleFragment;
import com.abu.abupro.ui.fragment.HomeFragment;
import com.abu.abupro.ui.fragment.MeFragment;
import com.abu.abupro.ui.fragment.MessageFragment;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.feedback.Comment;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.feedback.FeedbackThread;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Lazy;
/**
 *  @date: 2018/11/19 10:42
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description: 使用BottomNavigation实现切换不同的Fragment
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_frame)
    FrameLayout mFragmentFrame;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    @Inject
    Lazy<HomeFragment> homeFragmentProvider;

    @Inject
    Lazy<ArticleFragment> articleFragmentProvider;

    @Inject
    Lazy<MeFragment> meFragmentProvider;

    @Inject
    Lazy<MessageFragment> messageFragmentProvider;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        //监听底部导航条切换事件
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigation.setSelectedItemId(R.id.main_home);
        //注册EventBus
        EventBus.getDefault().register(this);

        //sync feedback 启用用户反馈模块
        FeedbackAgent feedbackAgent = new FeedbackAgent(this);
        //在用户打开 App 时，通知用户新的反馈回复，只需要在你的入口 Activity 的 OnCreate 方法中添加
        /*feedbackAgent.startDefaultThreadActivity();
        feedbackAgent.sync();
        feedbackAgent.getDefaultThread().sync(new FeedbackThread.SyncCallback() {
            @Override
            public void onCommentsSend(List<Comment> list, AVException e) {

            }

            @Override
            public void onCommentsFetch(List<Comment> list, AVException e) {

            }
        });
        */
    }

    /**
     * 底部导航条切换监听器
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //切换页卡
            switchPage(item.getItemId());
            return true;
        }
    };

    /**
     * 切换页面
     * @param itemId 底部导航条菜单选项的id
     */
    private void switchPage(int itemId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.main_home:
                fragmentTransaction.replace(R.id.fragment_frame, homeFragmentProvider.get());
                break;
            case R.id.main_article:
                fragmentTransaction.replace(R.id.fragment_frame, messageFragmentProvider.get());
                Toast.makeText(this, "最新资讯", Toast.LENGTH_SHORT).show();
                break;
            case R.id.artical_write:
                fragmentTransaction.replace(R.id.fragment_frame, articleFragmentProvider.get());
                break;
            case R.id.main_me:
                fragmentTransaction.replace(R.id.fragment_frame, meFragmentProvider.get());
                break;
                default:
        }
        fragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollChange(ScrollEvent scrollEvent) {
        if (scrollEvent.getDirection() == ScrollEvent.Direction.UP) {
            hideNavigationView();
        } else {
            showNavigationView();
        }
    }

    private void showNavigationView() {
        animationNavigationView(mBottomNavigation.getHeight(), 0);
    }


    private void hideNavigationView() {
        animationNavigationView(0, mBottomNavigation.getHeight());
    }

    private void animationNavigationView(float from ,float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBottomNavigation, "translationY",
                from, to);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEvent logoutEvent) {
        finish();
    }
}


