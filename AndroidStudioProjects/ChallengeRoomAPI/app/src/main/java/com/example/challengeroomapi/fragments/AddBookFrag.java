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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookFrag extends Fragment {

    public AddBookFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        EditText etISBN = view.findViewById(R.id.etISBN);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        btnCreate.setOnClickListener((View v) -> {
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
                    etTitle.getText().clear();
                    etAuthor.getText().clear();
                    TopToast.create(getActivity(), book.toString() + " ADDED successfully!");
                } catch (Exception e) {
                    TopToast.create(getActivity(), "ERROR! " + e.getMessage());
                }
            }
        });

        return view;
    }
}
