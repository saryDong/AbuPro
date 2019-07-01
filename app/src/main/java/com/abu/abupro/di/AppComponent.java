package com.abu.abupro.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        /**
         *  1.用于生成各个Activity对应的Component,这些生成的Component都将是AppComponent的Subcomponent
         *  定义抽象方法返回某个Activity,抽象方法使用注解@ContributesAndroidInJector 在编译之后生成
         *  Java类，类中定义Activity对应的Component和一个抽象方法，抽象方法返回创建Component的工厂类对象
         *  2.可以提供各个Activity对应的AndroidInjector.Factory,即注入器工厂类
         *
         */
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,//提供Activity,Fragment,Fragment(v4)的注入器工厂集合的注入，由dagger.android提供
        DataRepositoryModule.class}  //提供数据仓库的注入
        )
/**
 *  @date: 2018/11/22 10:11
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description: AppComponent继承自AndroidInjector,泛型为DaggerApplication,表示它可以对DaggerApplication进行注入
 */
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    /**
     * Application对应的注册器
     * @param instance 注入目的地
     */
    @Override
    void inject(DaggerApplication instance);
}
