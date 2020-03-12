package com.example.challengeroomapi.repositories;

public interface AsyncTaskCallback<T> {
    void handleResponse(T response);
    void handleFault(Exception e);
}
