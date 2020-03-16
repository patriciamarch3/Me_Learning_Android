package com.example.challengeroomapi.activities;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookFrag extends Fragment {
    private EditText etISBN, etTitle, etAuthor;
    private BooksViewModel viewModel;

    public AddBookFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        etISBN = view.findViewById(R.id.etISBN);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        viewModel = new ViewModelProvider(this).get(BooksViewModel.class);

        btnCreate.setOnClickListener((View v) -> {
            Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            String ISBNString = etISBN.getText().toString();
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();

            if (ISBNString.isEmpty() || title.isEmpty() || author.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill ALL fields!", Toast.LENGTH_SHORT).show();
            } else {
                Book book = new Book(Long.parseLong(ISBNString), title, author);
                try{
                    viewModel.insert(book);
                    etISBN.getText().clear();
                    etTitle.getText().clear();
                    etAuthor.getText().clear();
                    Toast.makeText(getActivity(), book.toString() + " ADDED successfully!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
