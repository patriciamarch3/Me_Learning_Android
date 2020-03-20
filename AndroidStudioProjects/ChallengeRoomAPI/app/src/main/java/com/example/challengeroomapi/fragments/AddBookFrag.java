package com.example.challengeroomapi.fragments;


import android.os.Bundle;

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
import com.example.challengeroomapi.uihelpers.TopToast;
import com.example.challengeroomapi.uihelpers.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.challengeroomapi.uihelpers.ValidationTextWatcher.validateEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookFrag extends Fragment {
    private EditText etISBN, etTitle, etAuthor;
    private TextInputLayout tilISBN, tilTitle, tilAuthor;

    public AddBookFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        etISBN = view.findViewById(R.id.etISBN);
        tilISBN = view.findViewById(R.id.tilISBN);
        etTitle = view.findViewById(R.id.etTitle);
        tilTitle = view.findViewById(R.id.tilTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        tilAuthor = view.findViewById(R.id.tilAuthor);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        etISBN.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                validateEditText(etISBN, tilISBN);
            }
        });
        etTitle.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                validateEditText(etTitle, tilTitle);
            }
        });
        etAuthor.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                validateEditText(etAuthor, tilAuthor);
            }
        });

        btnCreate.setOnClickListener((View v) -> {
            view.requestFocus();

            String ISBNString = etISBN.getText().toString().trim();
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();

            if (ISBNString.isEmpty() || title.isEmpty() || author.isEmpty()) {
                TopToast.create(getActivity(), "Please fill ALL fields!");
            } else {
                Book book = new Book(Long.parseLong(ISBNString), title, author);
                try{
                    booksViewModel.insert(book);
                    etISBN.getText().clear();
                    tilISBN.setErrorEnabled(false);
                    etTitle.getText().clear();
                    tilTitle.setErrorEnabled(false);
                    etAuthor.getText().clear();
                    tilAuthor.setErrorEnabled(false);
                    TopToast.create(getActivity(), book.toString() + " ADDED successfully!");
                } catch (Exception e) {
                    TopToast.create(getActivity(), "ERROR! " + e.getMessage());
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // add textChangedListener in onResume() rather than onCreateView()
        // to prevent it being triggered after orientation changes
        etISBN.addTextChangedListener(new ValidationTextWatcher(etISBN, tilISBN));
        etTitle.addTextChangedListener(new ValidationTextWatcher(etTitle, tilTitle));
        etAuthor.addTextChangedListener(new ValidationTextWatcher(etAuthor, tilAuthor));
    }
}