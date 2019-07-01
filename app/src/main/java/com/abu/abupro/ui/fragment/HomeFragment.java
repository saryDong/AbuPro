package com.abu.abupro.ui.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.abu.abupro.R;
import com.abu.abupro.adapter.HomePagerAdapter;
import com.abu.abupro.di.ActivityScoped;
import com.abu.abupro.ui.activity.AddQuestionActivity;

import javax.inject.Inject;
/**
 *  @date: 2018/11/19 11:11
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:  首页界面，包含问题，热门，动态
 */
@ActivityScoped
public class HomeFragment extends BaseMainFragment {

    @Inject
    public HomeFragment() {
    }


    @Override
    protected void init() {
        super.init();
        //加载toolbar 菜单
        mToolBar.inflateMenu(R.menu.home_menu);
        //设置监听
        mToolBar.setOnMenuItemClickListener(mOnMenuItemClickListener);
    }

    /**
     *  问题添加菜单键监听器
     */
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(getContext(), AddQuestionActivity.class);
            startActivity(intent);
            return true;
        }
    };

    /**
     * @return ViewPager的适配器
     */
    @Override
    PagerAdapter getPagerAdapter() {
        return new HomePagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.home_category));
    }
}
