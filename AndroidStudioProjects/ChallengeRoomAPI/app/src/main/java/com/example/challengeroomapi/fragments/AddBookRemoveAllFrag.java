package com.example.challengeroomapi.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.uihelpers.TopToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookRemoveAllFrag extends Fragment {


    public AddBookRemoveAllFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book_remove_all, container, false);
        Button btnDeleteAll = view.findViewById(R.id.btnDeleteAll);
        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        btnDeleteAll.setOnClickListener((View v) -> {
            try {
                if (booksViewModel.getBooks().getValue().size() == 0) {
                    TopToast.create(getActivity(), getString(R.string.warning_no_book));
                } else {
                    booksViewModel.deleteAll();
                    TopToast.create(getActivity(), getString(R.string.message_success, getString(R.string.message_delete_all)));
                }
            } catch (Exception e) {
                TopToast.create(getActivity(), getString(R.string.message_error, e.getMessage()));
            }
        });

        return view;
    }

}
