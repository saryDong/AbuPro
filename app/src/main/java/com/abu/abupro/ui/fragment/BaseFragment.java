package com.abu.abupro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
/**
 *  @date: 2018/11/20 11:00
 *  @author: 董长峰
 *  @blog: https://www.jianshu.com/u/04a705fae99b
 *  @description:
 */
public abstract class BaseFragment extends DaggerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResId(), null);
        ButterKnife.bind(this, root);
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected void init(){}

    /**
     *  返回布局id
     * @return 布局id
     */
    protected abstract int getLayoutResId();


    public void navigateTo(Class to) {
        Intent intent = new Intent(getContext(), to);
        startActivity(intent);
    }

}
