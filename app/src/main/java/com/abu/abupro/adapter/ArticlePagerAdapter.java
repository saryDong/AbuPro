package com.abu.abupro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abu.abupro.data.model.Article;
import com.abu.abupro.ui.fragment.ArticleCategoryFragment;

import java.util.List;

/**
 *  @date: 2018/11/19 10:44
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public class ArticlePagerAdapter extends FragmentPagerAdapter {

    private final List<String> mTitles;


    public ArticlePagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleCategoryFragment.newInstance(mTitles.get(position));
    }

    @Override
    public int getCount() {
        return mTitles==null?0:mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
