package com.abu.abupro.di;

import com.abu.abupro.ui.fragment.ArticleFragment;
import com.abu.abupro.ui.fragment.HomeFragment;
import com.abu.abupro.ui.fragment.MeFragment;
import com.abu.abupro.ui.fragment.MessageFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *   配置生成各个fragment注入器完成Fragment内部成员变量注入
 */
@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment homeFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = ArticleModule.class)
    abstract ArticleFragment articleFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MeFragment meFragment();


    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract MessageFragment messageFragment();
}
