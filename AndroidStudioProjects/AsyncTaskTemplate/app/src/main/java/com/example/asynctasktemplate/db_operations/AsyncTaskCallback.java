package com.example.asynctasktemplate.db_operations;

public interface AsyncTaskCallback {
    void handleResponse(String object);
    void handleFault(Exception e);
}
