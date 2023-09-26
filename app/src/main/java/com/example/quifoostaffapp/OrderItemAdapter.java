package com.example.quifoostaffapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderItemAdapter extends FirebaseRecyclerAdapter<OrderItemModel, OrderItemAdapter.ViewHolder> {


    public OrderItemAdapter(@NonNull FirebaseRecyclerOptions<OrderItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OrderItemModel model) {
        holder.orderDishName.setText(model.getName());
        holder.orderDishQty.setText(String.valueOf(model.getQuantity()));
        if((model.getDishType()).equals("Cancelled Food"))
        {
            holder.offerIcon.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.offerIcon.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitemholder, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderDishName, orderDishQty;
        ImageView offerIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDishName = itemView.findViewById(R.id.orderDishName);
            orderDishQty = itemView.findViewById(R.id.orderDishQty);
            offerIcon = itemView.findViewById(R.id.offerFood_ic);


        }
    }

}
