package com.abu.abupro.di;


import com.abu.abupro.ui.fragment.ArticleCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ArticleModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract ArticleCategoryFragment articleFragment();
}
