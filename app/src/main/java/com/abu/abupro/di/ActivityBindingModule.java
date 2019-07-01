package com.abu.abupro.di;

import com.abu.abupro.ui.MainActivity;
import com.abu.abupro.ui.activity.AddAnswerActivity;
import com.abu.abupro.ui.activity.AddQuestionActivity;
import com.abu.abupro.ui.activity.AnswerDetailActivity;
import com.abu.abupro.ui.activity.ArticleDetailActivity;
import com.abu.abupro.ui.activity.CommentActivity;
import com.abu.abupro.ui.activity.LoginActivity;
import com.abu.abupro.ui.activity.ProfileActivity;
import com.abu.abupro.ui.activity.QuestionDetailActivity;
import com.abu.abupro.ui.activity.RegisterActivity;
import com.abu.abupro.ui.activity.SettingsActivity;
import com.abu.abupro.ui.activity.ShareArticleActivity;
import com.abu.abupro.ui.activity.SplashActivity;
import com.abu.abupro.ui.activity.TabManagerActivity;
import com.abu.abupro.ui.activity.UserAnswerActivity;
import com.abu.abupro.ui.activity.UserFavourAnswerActivity;
import com.abu.abupro.ui.activity.UserFavourArticleActivity;
import com.abu.abupro.ui.activity.UserFavourQuestionActivity;
import com.abu.abupro.ui.activity.UserQuestionsActivity;
import com.abu.abupro.ui.activity.UserShareArticleActivity;
import com.abu.abupro.ui.activity.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
/**
 *  @date: 2018/11/22 9:07
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:  生成对应Component并提供Component工厂，由于TextView这类注入时需要传入TextViewModule
 *   对象，不能使用此方法生成，需手动创建并在内部提供抽象方法将此对象作为参数
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract RegisterActivity registerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract TabManagerActivity mTabManagerActivity();

    /**
     *  生成的各个Fragment的注入器（Component）会是MainActivity注入器的Subcomponent
     * @return
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddQuestionActivity addQuestionActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = QuestionDetailModule.class)
    abstract QuestionDetailActivity questionDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddAnswerActivity addAnswerActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract AnswerDetailActivity answerDetailActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract CommentActivity commentActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ShareArticleActivity shareArticleActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ArticleDetailActivity articleDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserShareArticleActivity userShareActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserQuestionsActivity userQuestionActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserAnswerActivity userAnswerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourArticleActivity myFavourArticleActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourQuestionActivity myFavourQuestionActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourAnswerActivity myFavourAnswerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SettingsActivity settingsActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WebViewActivity mWebViewActivity();


}
