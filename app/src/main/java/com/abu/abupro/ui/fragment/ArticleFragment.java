package com.abu.abupro.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.abu.abupro.R;
import com.abu.abupro.adapter.ArticlePagerAdapter;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.LoadCallback;
import com.abu.abupro.data.model.ArticleCollect;
import com.abu.abupro.di.ActivityScoped;
import com.abu.abupro.event.TabRefresh;
import com.abu.abupro.ui.activity.TabManagerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class ArticleFragment extends BaseMainFragment {

    @Inject
    public ArticleFragment() {}

    @Inject
    ArticleDataSource mArticleDataSource;

    List<String> title;

    @Override
    protected void init() {
        super.init();
        //加载toolbar 菜单
        mToolBar.inflateMenu(R.menu.home_menu);
        mToolBar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        requestTabs();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    private void requestTabs() {
        mArticleDataSource.getArticleCollectes(new LoadCallback<ArticleCollect>() {
            @Override
            public void onLoadSuccess(final List<ArticleCollect> list) {
                parse(list.get(0));
            }

            @Override
            public void onLoadFailed(String errorMsg) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestTabs();
        getPagerAdapter().notifyDataSetChanged();
    }

    Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(getContext(), TabManagerActivity.class);
            startActivity(intent);
            return true;
        }
    };

    public void parse(ArticleCollect list){
        super.init();
        if (list == null) {
            list = new ArticleCollect();
            list.setArticleCollect("[\"java\",\"Java\"]");
        }
        Gson gson = new Gson();
        title = gson.fromJson(list.getArticleCollect(), new TypeToken<List<String>>() {
        }.getType());
        mTabLayout.setTag(null);
        mTabLayout.setTag(title);
        getPagerAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabRefresh(TabRefresh refresh){
       requestTabs();
    }

    @Override
    public int getTabScrollMode() {
        return TabLayout.MODE_SCROLLABLE;
    }

    @Override
    PagerAdapter getPagerAdapter() {
        FragmentManager manager=getChildFragmentManager();
        return new ArticlePagerAdapter(manager, title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //EventBus
        EventBus.getDefault().unregister(this);
    }
}
