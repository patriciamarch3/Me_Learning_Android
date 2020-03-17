package com.example.challengeroomapi.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFrag extends Fragment {
    private Book book;

    public BookDetailFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        EditText etISBN = view.findViewById(R.id.etISBN);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
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
                    etTitle.setText(book.getTitle());
                    etAuthor.setText(book.getAuthor());
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        etISBN.setEnabled(false);
        etTitle.setEnabled(false);
        etAuthor.setEnabled(false);

        btnDelete.setOnClickListener((View v) -> {
            try{
                booksViewModel.delete(book);
                setReply(Activity.RESULT_OK, book.toString() + " DELETED");
            } catch (Exception e) {
                setReply(Activity.RESULT_CANCELED, e.getMessage());
            }
        });

        btnEdit.setOnClickListener((View v) -> {
            etTitle.setEnabled(true);
            etAuthor.setEnabled(true);
            btnApply.setVisibility(View.VISIBLE);
        });

        btnApply.setOnClickListener((View v) -> {
            String newTitle = etTitle.getText().toString();
            String newAuthor = etAuthor.getText().toString();
            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter ALL fields!", Toast.LENGTH_SHORT).show();
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
