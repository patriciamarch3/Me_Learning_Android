package com.example.challengeroomapi.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.room.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ItemClicked context;
    private List<Book> bookList;

    BookAdapter(Context context) {
        this.context = (ItemClicked) context;
    }

    public interface ItemClicked {
        void onClickItem(long ISBN);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvAuthor;

        private ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);

            itemView.setOnClickListener((View v) -> context.onClickItem(((Book) itemView.getTag()).getISBN()));
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
        if (bookList != null) {
            Book book = bookList.get(position);
            holder.itemView.setTag(book);
            holder.tvTitle.setText(book.getTitle());
            holder.tvAuthor.setText(book.getAuthor());
        } else {
            holder.tvTitle.setText("No book");
            holder.tvAuthor.setText("No book");
        }
    }

    @Override
    public int getItemCount() {
        if (bookList == null) {
            return 0;
        } else {
            return bookList.size();
        }
    }

    void setBooks(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }
}
