package com.example.asynctasktemplate.db_operations;

import android.content.Context;
import android.os.AsyncTask;

public class RunSomethingInTheBackground extends AsyncTask<Integer, Void, String> {

    private Context context;
    private AsyncTaskCallback callback;
    private Exception exception;

    public RunSomethingInTheBackground(Context context, AsyncTaskCallback callback) {
        this.context = context;
        this.callback = callback;
        exception = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String result = "";

        try {
            result = "This is the result.";
        } catch (Exception e) {
            exception = e;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (callback != null) {
            if (exception == null) {
                callback.handleResponse(s);
            } else {
                callback.handleFault(exception);
            }
        }
    }
}
