package com.example.roomapivideo.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.roomapivideo.room.ConnectToDB;
import com.example.roomapivideo.room.Contact;
import com.example.roomapivideo.room.ContactDAO;

public class InsertContactAsync extends AsyncTask<Contact, Void, Contact> {
    private AsyncTaskCallBack<Contact> callBack;
    private Exception exception;
    private ContactDAO contactDAO;

    public InsertContactAsync(Context context, AsyncTaskCallBack<Contact> callBack) {
        this.callBack = callBack;
        exception = null;
        contactDAO = ConnectToDB.getInstance(context).getDb().getContactDAO();
    }

    @Override
    protected Contact doInBackground(Contact... contacts) {
        Contact contact = contacts[0];
        try {
            Contact contactInDB = contactDAO.getContactByID(contact.getID());
            if (contactInDB == null) {
                contactDAO.insert(contact);
            } else {
                throw new Exception("Contact already exists!");
            }
        } catch (Exception e) {
            exception = e;
        }
        return contact;
    }

    @Override
    protected void onPostExecute(Contact s) {
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
