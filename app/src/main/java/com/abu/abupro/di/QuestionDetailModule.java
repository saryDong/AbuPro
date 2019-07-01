package com.abu.abupro.di;


import com.abu.abupro.ui.fragment.HotAnswerFragment;
import com.abu.abupro.ui.fragment.RecentAnswerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class QuestionDetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract RecentAnswerFragment recentAnswerFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract HotAnswerFragment hotAnswerFragment();
}
