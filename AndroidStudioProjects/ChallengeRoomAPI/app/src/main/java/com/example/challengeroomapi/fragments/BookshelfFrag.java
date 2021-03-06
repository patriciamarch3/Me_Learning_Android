package com.example.challengeroomapi.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookshelfFrag extends Fragment {
    private View view;

    public BookshelfFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tvNoBook = view.findViewById(R.id.tvNoBook);
        tvNoBook.setVisibility(View.GONE);

        RecyclerView bookshelf = view.findViewById(R.id.bookshelf);
        bookshelf.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        BookAdapter adapter = new BookAdapter(getActivity());
        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        booksViewModel.getBooks().observe(getViewLifecycleOwner(), (List<Book> bookList) -> {
            adapter.setBooks(bookList);
            if (bookList.size() == 0) {
                tvNoBook.setVisibility(View.VISIBLE);
            } else {
                tvNoBook.setVisibility(View.GONE);
            }
        });
        bookshelf.setAdapter(adapter);
    }
}
