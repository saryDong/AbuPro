package com.abu.abupro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abu.abupro.ui.fragment.DynamicFragment;
import com.abu.abupro.ui.fragment.HotQuestionFragment;
import com.abu.abupro.ui.fragment.JobInfoFragment;
import com.abu.abupro.ui.fragment.QuestionFragment;

/**
 *  @date: 2018/11/19 10:51
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;

    public HomePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new QuestionFragment();
            case 1: return new HotQuestionFragment();
            case 2: return new JobInfoFragment();
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    /**
     * TabLayout里会调用到该方法获取页面标题
     * @param position 页面下标
     * @return 返回页面标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
