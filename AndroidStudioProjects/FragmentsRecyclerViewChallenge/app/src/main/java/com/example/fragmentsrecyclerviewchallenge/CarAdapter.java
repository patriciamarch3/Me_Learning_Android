package com.example.fragmentsrecyclerviewchallenge;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    ArrayList<Car> carList;
    ItemClicked activity;

    public CarAdapter(Context context, ArrayList<Car> carList) {
        this.carList = carList;
        this.activity = (ItemClicked) context;
    }

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMake;
        TextView tvCarMake;
        TextView tvOwnerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMake = itemView.findViewById(R.id.ivMake);
            tvCarMake = itemView.findViewById(R.id.tvCarMake);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(carList.indexOf((Car) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new CarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.itemView.setTag(car);

        if (car.getMake().equals("mercedes")) {
            holder.ivMake.setImageResource(R.drawable.mercedes);
        } else if (car.getMake().equals("nissan")) {
            holder.ivMake.setImageResource(R.drawable.nissan);
        } else {
            holder.ivMake.setImageResource(R.drawable.volkswagen);
        }
        holder.tvCarMake.setText(car.getModel());
        holder.tvOwnerName.setText(car.getOwnerName());
    }

    @Override
    public int getItemCount() {
        return carList.size();
//        return 6;
    }
}
