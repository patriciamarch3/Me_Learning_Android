package com.example.roomapivideo.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.roomapivideo.room.ConnectToDB;
import com.example.roomapivideo.room.Contact;
import com.example.roomapivideo.room.ContactDAO;

public class FindContactByIdAsync extends AsyncTask<Long, Void, Contact> {
    private AsyncTaskCallBack<Contact> callBack;
    private Exception exception;
    private ContactDAO contactDAO;

    public FindContactByIdAsync(Context context, AsyncTaskCallBack<Contact> callBack) {
        this.callBack = callBack;
        exception = null;
        contactDAO = ConnectToDB.getInstance(context).getDb().getContactDAO();
    }

    @Override
    protected Contact doInBackground(Long... longs) {
        Contact contact = null;
        try {
            contact = contactDAO.getContactByID(longs[0]);
            if (contact == null) {
                throw new Exception("Contact does not exist!");
            }
        } catch (Exception e) {
            exception = e;
        }
        return contact;
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);

        if (callBack != null) {
            if (exception == null) {
                callBack.handleResponse(contact);
            } else {
                callBack.handleFault(exception);
            }
        }
    }
}
