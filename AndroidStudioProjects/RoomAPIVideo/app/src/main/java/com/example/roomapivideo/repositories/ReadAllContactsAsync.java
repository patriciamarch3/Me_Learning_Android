package com.example.roomapivideo.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.roomapivideo.room.ConnectToDB;
import com.example.roomapivideo.room.Contact;
import com.example.roomapivideo.room.ContactDAO;

import java.util.List;

public class ReadAllContactsAsync extends AsyncTask<Void, Void, List<Contact>> {
    private AsyncTaskCallBack<List<Contact>> callBack;
    private Exception exception;
    private ContactDAO contactDAO;

    public ReadAllContactsAsync(Context context, AsyncTaskCallBack<List<Contact>> callBack) {
        this.callBack = callBack;
        exception = null;
        contactDAO = ConnectToDB.getInstance(context).getDb().getContactDAO();
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {
        List<Contact> contacts = null;

        try {
            contacts = contactDAO.getAllContacts();
            if (contacts.size() == 0) {
                throw new Exception("No data in the database!");
            }
        } catch (Exception e) {
            exception = e;
        }

        return contacts;
    }

    @Override
    protected void onPostExecute(List<Contact> s) {
        super.onPostExecute(s);

        if (callBack != null) {
            if (exception == null) {
                callBack.handleResponse(s);
            } else {
                callBack.handleFault(exception);
            }
        }
    }
}
