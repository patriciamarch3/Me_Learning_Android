package com.example.challengeroomapi.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.uihelpers.TopToast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFrag extends Fragment {
    private Book book;
    private EditText etTitle, etAuthor;
    private static final String EDIT_BUTTON_CLICKED = "edit_button_isclicked";
    private static final String NEW_TITLE = "new_title";
    private static final String NEW_AUTHOR = "new_author";
    private boolean edit_button_clicked = false;

    public BookDetailFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        EditText etISBN = view.findViewById(R.id.etISBN);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnApply = view.findViewById(R.id.btnApply);
        btnApply.setVisibility(View.GONE);

        long ISBN = getActivity().getIntent().getLongExtra("isbn", 0);
        etISBN.setText(String.format(Locale.getDefault(), "%d", ISBN));

        BooksViewModel booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        try {
            booksViewModel.getBookByISBN(ISBN).observe(getViewLifecycleOwner(), (Book foundBook) -> {
                if (foundBook != null) {
                    book = foundBook;
                    if (savedInstanceState != null) {
                        if (savedInstanceState.containsKey(EDIT_BUTTON_CLICKED)) {
                            if (savedInstanceState.getBoolean(EDIT_BUTTON_CLICKED)) {
                                edit_button_clicked = true;
                                etTitle.setText(savedInstanceState.getString(NEW_TITLE));
                                etAuthor.setText(savedInstanceState.getString(NEW_AUTHOR));
                            } else {
                                etTitle.setText(book.getTitle());
                                etAuthor.setText(book.getAuthor());
                            }
                        }
                    } else {
                        etTitle.setText(book.getTitle());
                        etAuthor.setText(book.getAuthor());
                    }
                }
            });
        } catch (Exception e) {
            TopToast.create(getActivity(), "ERROR! " + e.getMessage());
//            Toast.makeText(getActivity(), "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        etISBN.setEnabled(false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EDIT_BUTTON_CLICKED)) {
                if (savedInstanceState.getBoolean(EDIT_BUTTON_CLICKED)) {
                    edit_button_clicked = true;
                    etTitle.setEnabled(true);
                    etAuthor.setEnabled(true);
                    btnApply.setVisibility(View.VISIBLE);
                } else {
                    etTitle.setEnabled(false);
                    etAuthor.setEnabled(false);
                }
            }
        } else {
            etTitle.setEnabled(false);
            etAuthor.setEnabled(false);
        }

        btnDelete.setOnClickListener((View v) -> {
            try{
                booksViewModel.delete(book);
                setReply(Activity.RESULT_OK, book.toString() + " DELETED");
            } catch (Exception e) {
                setReply(Activity.RESULT_CANCELED, e.getMessage());
            }
        });

        btnEdit.setOnClickListener((View v) -> {
            edit_button_clicked = true;
            etTitle.setEnabled(true);
            etAuthor.setEnabled(true);
            btnApply.setVisibility(View.VISIBLE);
        });

        btnApply.setOnClickListener((View v) -> {
            edit_button_clicked = false;
            String newTitle = etTitle.getText().toString();
            String newAuthor = etAuthor.getText().toString();
            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                TopToast.create(getActivity(), "Please enter ALL fields!");
//                Toast.makeText(getActivity(), "Please enter ALL fields!", Toast.LENGTH_SHORT).show();
            } else {
                book.setTitle(newTitle.trim());
                book.setAuthor(newAuthor.trim());
                try{
                    booksViewModel.update(book);
                    setReply(Activity.RESULT_OK, book.toString() + " UPDATED");
                } catch (Exception e) {
                    setReply(Activity.RESULT_CANCELED, e.getMessage());
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(EDIT_BUTTON_CLICKED, edit_button_clicked);
        outState.putString(NEW_TITLE, etTitle.getText().toString());
        outState.putString(NEW_AUTHOR, etAuthor.getText().toString());
    }

    private void setReply(int resultCode, String message) {
        Intent replyIntent = new Intent();
        String key, value;
        if (resultCode == Activity.RESULT_OK) {
            key = "success";
            value = message + " successfully!";
        } else {
            key = "error";
            value = "ERROR! " + message;
        }
        replyIntent.putExtra(key, value);
        getActivity().setResult(resultCode, replyIntent);
        getActivity().finish();
    }
}
