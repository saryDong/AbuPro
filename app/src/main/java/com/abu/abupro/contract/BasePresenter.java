package com.abu.abupro.contract;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
