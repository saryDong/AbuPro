package com.abu.abupro.data;

public interface SaveCallback {
    void onSaveSuccess();
    void onSaveFailed(String errorMsg);
}
