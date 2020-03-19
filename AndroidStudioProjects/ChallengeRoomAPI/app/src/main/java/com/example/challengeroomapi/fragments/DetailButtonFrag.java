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

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailButtonFrag extends Fragment {
    private OnActionListener callback;

    public DetailButtonFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_button, container, false);

        BooksViewModel booksViewModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        booksViewModel.setEditClicked(false);

        btnDelete.setOnClickListener((View v) -> callback.onDeleteClicked());

        btnEdit.setOnClickListener((View v) -> booksViewModel.setEditClicked(true));

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
