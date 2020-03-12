package com.example.roomapivideo.repositories;

public interface AsyncTaskCallBack<T> {
    void handleResponse(T response);
    void handleFault(Exception e);
}
