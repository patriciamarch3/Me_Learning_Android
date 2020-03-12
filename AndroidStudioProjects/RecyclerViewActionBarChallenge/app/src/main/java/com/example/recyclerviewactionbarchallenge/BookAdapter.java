package com.example.recyclerviewactionbarchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ArrayList<Book> books;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGenre;
        TextView tvTitle, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGenre = itemView.findViewById(R.id.ivGenre);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new BookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = books.get(position);
        if (book.getGenre().toLowerCase().equals("scfi")) {
            holder.ivGenre.setImageResource(R.mipmap.scfi);
        } else if (book.getGenre().toLowerCase().equals("drama")) {
            holder.ivGenre.setImageResource(R.mipmap.drama);
        } else {
            holder.ivGenre.setImageResource(R.mipmap.romance);
        }
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
