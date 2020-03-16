package com.example.challengeroomapi.repositories;

import android.content.Intent;

public interface AsyncTaskCallback<T> {
    void handleResponse(T response);
    void handleFault(Exception e);
}
