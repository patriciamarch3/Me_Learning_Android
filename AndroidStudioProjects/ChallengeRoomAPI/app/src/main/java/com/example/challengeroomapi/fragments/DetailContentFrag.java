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

    public DetailContentFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_content, container, false);

        EditText etISBN = view.findViewById(R.id.etISBN);
        etISBN.setEnabled(false);
        EditText etTitle = view.findViewById(R.id.etTitle);
        etTitle.setEnabled(false);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        etAuthor.setEnabled(false);
        Button btnApply = view.findViewById(R.id.btnApply);
        btnApply.setVisibility(View.GONE);

        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        booksViewModel.getBookForDetail().observe(getViewLifecycleOwner(), (Book book) -> {
            etISBN.setText(String.format(Locale.getDefault(), "%d", book.getISBN()));
            etTitle.setText(book.getTitle());
            etAuthor.setText(book.getAuthor());
        });

        booksViewModel.getEditClicked().observe(getViewLifecycleOwner(), (Boolean editClicked) -> {
            if (editClicked) {
                etTitle.setEnabled(true);
                etAuthor.setEnabled(true);
                btnApply.setVisibility(View.VISIBLE);
            } else {
                etTitle.setEnabled(false);
                etAuthor.setEnabled(false);
                btnApply.setVisibility(View.GONE);
            }
        });

        btnApply.setOnClickListener((View v) -> {
            String newTitle = etTitle.getText().toString().trim();
            String newAuthor = etAuthor.getText().toString().trim();
            callback.onApplyClicked(newTitle, newAuthor);
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
}
