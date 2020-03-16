package com.example.challengeroomapi.repositories;

import android.content.Intent;

public interface AsyncTaskCallback<T> {
    Intent replyIntent = new Intent();
    void handleResponse(T response);
    void handleFault(Exception e);
}
