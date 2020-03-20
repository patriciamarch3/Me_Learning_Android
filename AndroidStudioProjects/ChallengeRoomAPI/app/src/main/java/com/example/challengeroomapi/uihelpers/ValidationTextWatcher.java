package com.example.challengeroomapi.uihelpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.challengeroomapi.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationTextWatcher implements TextWatcher {
    private View view;
    private TextInputLayout til;

    public ValidationTextWatcher(View view, TextInputLayout til) {
        this.view = view;
        this.til = til;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d("text_error", "because of this");
        validateEditText(view, til);
    }

    public static void validateEditText(View view, TextInputLayout til) {
        EditText et = (EditText) view;
        if (et.getText().toString().trim().isEmpty()) {
            switch (view.getId()) {
                case R.id.etISBN:
                    til.setError("Missing ISBN");
                    break;

                case R.id.etTitle:
                    til.setError("Missing Title");
                    break;

                case R.id.etAuthor:
                    til.setError("Missing Author");
                    break;
            }
        } else {
            til.setErrorEnabled(false);
        }
    }
}
