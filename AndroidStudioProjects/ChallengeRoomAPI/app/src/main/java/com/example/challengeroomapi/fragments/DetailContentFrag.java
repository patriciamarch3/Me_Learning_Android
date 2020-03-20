package com.example.challengeroomapi.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailContentFrag extends Fragment {
    private OnActionListener callback;
    private static final String EDIT_BUTTON_CLICKED = "edit_button_isclicked";
    private static final String NEW_TITLE = "new_title";
    private static final String NEW_AUTHOR = "new_author";
    private boolean edit_button_clicked = false;
    private EditText etTitle, etAuthor;

    public DetailContentFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_content, container, false);

        EditText etISBN = view.findViewById(R.id.etISBN);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        Button btnApply = view.findViewById(R.id.btnApply);

        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        booksViewModel.getBookForDetail().observe(getViewLifecycleOwner(), (Book book) -> {
            etISBN.setText(String.format(Locale.getDefault(), "%d", book.getISBN()));
            etTitle.setText(book.getTitle());
            etAuthor.setText(book.getAuthor());
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(EDIT_BUTTON_CLICKED)
                        && savedInstanceState.getBoolean(EDIT_BUTTON_CLICKED)
                        && edit_button_clicked) {
                    etTitle.setText(savedInstanceState.getString(NEW_TITLE));
                    etAuthor.setText(savedInstanceState.getString(NEW_AUTHOR));
                }
            }
        });

        etISBN.setEnabled(false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EDIT_BUTTON_CLICKED)
                    && savedInstanceState.getBoolean(EDIT_BUTTON_CLICKED)) {
                booksViewModel.setEditClicked(true);
            }
        }

        booksViewModel.getEditClicked().observe(getViewLifecycleOwner(), (Boolean editClicked) -> {
            if (editClicked) {
                edit_button_clicked = true;
                etTitle.setEnabled(true);
                etAuthor.setEnabled(true);
                btnApply.setVisibility(View.VISIBLE);
            } else {
                edit_button_clicked = false;
                etTitle.setEnabled(false);
                etAuthor.setEnabled(false);
                btnApply.setVisibility(View.GONE);
            }
        });

        btnApply.setOnClickListener((View v) -> {
            view.requestFocus();
            String newTitle = etTitle.getText().toString().trim();
            String newAuthor = etAuthor.getText().toString().trim();
            edit_button_clicked = callback.onApplyClicked(newTitle, newAuthor);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        callback = (OnActionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(EDIT_BUTTON_CLICKED, edit_button_clicked);
        outState.putString(NEW_TITLE, etTitle.getText().toString());
        outState.putString(NEW_AUTHOR, etAuthor.getText().toString());
    }
}
