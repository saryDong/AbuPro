package com.abu.abupro.app;

import com.abu.abupro.BuildConfig;
import com.abu.abupro.MyEventBusIndex;
import com.abu.abupro.PushActivity;
import com.abu.abupro.data.model.ArticleCollect;
import com.abu.abupro.data.model.Message;
import com.abu.abupro.data.model.User;
import com.abu.abupro.data.model.Answer;
import com.abu.abupro.data.model.Article;
import com.abu.abupro.data.model.Comment;
import com.abu.abupro.data.model.Question;
import com.abu.abupro.data.model.UserAnswerFavourMap;
import com.abu.abupro.data.model.UserArticleFavourMap;
import com.abu.abupro.data.model.UserQuestionFavourMap;
import com.abu.abupro.di.DaggerAppComponent;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.EventBus;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 *  @date: 2018/11/19 10:41
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class AndroidPlusApp extends DaggerApplication {
 /*   private static final String APP_ID = "e23RXU0ywb3H2hfHgPbmAL3s-gzGzoHsz";
    private static final String APP_KEY = "Hy5zNJf0I4oOAwUA7k7sf1CN";*/
    private static final String APP_ID ="ePvDKr4yg5TPzQNQMkhcJySd-gzGzoHsz";
    private static final String APP_KEY ="IAAgqR4D1nGKgDTYOinK8Dsv";

    @Override
    public void onCreate() {
        super.onCreate();
        //Init Lean Cloud
        initSubClasses();
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);


        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作……
                } else {
                    // 保存失败，输出错误信息
                }
            }
        });
        // 设置默认打开的 Activity
       // PushService.setDefaultPushCallback(this,PushActivity.class);
        /**
         * 只设置一次，并且要在我们第一次使用EventBus之前进行设置
         * 这样每次获取到默认实例，都是使用Subscriber Index的了，代码得到了精简。EventBus eventBus = EventBus.getDefault();
         */

        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        /**
         * 更新升级
         */
        Beta.enableHotfix = false;
        Beta.autoDownloadOnWifi = true;
        Bugly.init(getApplicationContext(), "033d44667d", BuildConfig.DEBUG);
    }

    private void initSubClasses() {
        AVObject.registerSubclass(Comment.class);
        AVObject.registerSubclass(Question.class);
        AVObject.registerSubclass(Answer.class);
        AVObject.registerSubclass(Article.class);
        AVObject.registerSubclass(UserArticleFavourMap.class);
        AVObject.registerSubclass(UserQuestionFavourMap.class);
        AVObject.registerSubclass(UserAnswerFavourMap.class);
        AVObject.registerSubclass(User.class);
        AVUser.alwaysUseSubUserClass(User.class);
        AVUser.registerSubclass(Message.class);
        AVUser.registerSubclass(ArticleCollect.class);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.create();
    }
}
