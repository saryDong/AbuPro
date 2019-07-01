package com.abu.abupro.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;


import com.abu.abupro.R;

import butterknife.BindView;
/**
 *  @date: 2018/11/19 11:04
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description: 抽取出Fragment通用的功能，下拉刷新，列表数据展示，加载更多，出错重试
 */
public abstract class BaseMainFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    /**
     *  返回布局id
     * @return 布局id
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_base_main;
    }

    /**
     *  1. 设置ViewPaper适配器
     *  2. TabLayout与ViewPaper设置关联
     *  3. TabLayout设置滚动模式
     */
    @Override
    protected void init() {
        super.init();
        mViewPager.setAdapter(getPagerAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(getTabScrollMode());
    }
    /**
     *  返回ViewPaper适配器
     * @return 返回ViewPaper适配器
     */
    abstract PagerAdapter getPagerAdapter();

    /**
     *  返回TabLayout滚动模式
     * @return 滚动模式值
     */
    public int getTabScrollMode() {
        return TabLayout.MODE_FIXED;
    }
}
