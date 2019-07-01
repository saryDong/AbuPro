package com.abu.abupro.data;

import java.util.List;

public interface LoadCallback<T> {
    void onLoadSuccess(List<T> list);
    void onLoadFailed(String errorMsg);
}
