package com.example.challengeroomapi.repositories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;
import com.example.challengeroomapi.room.ConnectionToDB;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ItemClicked context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = (ItemClicked) context;
        this.bookList = bookList;
    }

    public interface ItemClicked {
        void onClickItem(long ISBN);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.onClickItem(((Book) itemView.getTag()).getISBN());
                }
            });
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
        Book book = bookList.get(position);
        holder.itemView.setTag(book);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (bookList == null) {
            return 0;
        } else {
            return bookList.size();
        }
    }
}
