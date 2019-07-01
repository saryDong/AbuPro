package com.abu.abupro.di;

import com.abu.abupro.ui.fragment.DynamicFragment;
import com.abu.abupro.ui.fragment.HotQuestionFragment;
import com.abu.abupro.ui.fragment.JobInfoFragment;
import com.abu.abupro.ui.fragment.MessageFragment;
import com.abu.abupro.ui.fragment.QuestionFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class HomeModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract QuestionFragment questionFragment();

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract JobInfoFragment jobInfoFragment();

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract HotQuestionFragment hotFragment();

}
