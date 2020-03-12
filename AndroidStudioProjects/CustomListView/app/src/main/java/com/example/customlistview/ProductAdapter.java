package com.example.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final ArrayList<Product> values;

    public ProductAdapter(@NonNull Context context, ArrayList<Product> list) {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView tvProduct = rowView.findViewById(R.id.tvProduct);
        TextView tvPrice = rowView.findViewById(R.id.tvPrice);
        TextView tvDescription = rowView.findViewById(R.id.tvDescription);
        ImageView ivProduct = rowView.findViewById(R.id.ivProduct);
        ImageView ivSale = rowView.findViewById(R.id.ivSale);

        Product product = values.get(position);
        tvProduct.setText(product.getTitle());
        tvPrice.setText("R" + product.getPrice());
        tvDescription.setText(product.getDescription());

        if (product.isOnSale()) {
            ivSale.setImageResource(R.drawable.on_sale_big);
        } else {
            ivSale.setImageResource(R.drawable.best_price);
        }

        if (product.getType().equals("Laptop")) {
            ivProduct.setImageResource(R.drawable.laptop);
        } else if (product.getType().equals("Memory")) {
            ivProduct.setImageResource(R.drawable.memory);
        } else if (product.getType().equals("Screen")) {
            ivProduct.setImageResource(R.drawable.screen);
        } else {
            ivProduct.setImageResource(R.drawable.hdd);
        }

        return rowView;
    }
}
