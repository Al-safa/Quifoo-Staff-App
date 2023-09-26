package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainAdapter extends FirebaseRecyclerAdapter <MainModel, MainAdapter.ViewHolder>{

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(String.valueOf(model.getPrice()));
        holder.category.setText(model.getCategory());

        if((model.getDishType()).equals("Special Dish"))
        {
            holder.starImg.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.starImg.setVisibility(View.INVISIBLE);
        }

        if(model.getAvailable().equals(false))
        {
            holder.availabilityStatus.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.availabilityStatus.setVisibility(View.INVISIBLE);
        }
        if(model.getDishType().equals("Cancelled Food"))
        {
            holder.cancelledImg.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cancelledImg.setVisibility(View.INVISIBLE);
        }

        Glide.with(holder.img.getContext())
                .load(model.getImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), updateDishActivity.class);
            intent.putExtra("Name", String.valueOf(model.getName()));
            intent.putExtra("Price", String.valueOf(model.getPrice()));
            intent.putExtra("Category", String.valueOf(model.getCategory()));
            intent.putExtra("Image", String.valueOf(model.getImage()));
            intent.putExtra("Availability", String.valueOf(model.getAvailable()));
            intent.putExtra("DishType", String.valueOf(model.getDishType()));
            v.getContext().startActivity(intent);
        });

                    holder.btnDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                    builder.setTitle("Delete " + model.getName() + " ?");
                    builder.setMessage("Are you sure you want to delete " + model.getName() + " ?");

                    builder.setPositiveButton("Delete", (dialog, which) -> FirebaseDatabase.getInstance().getReference().child("Food Items").child(counterName)
                            .child(getRef(position).getKey()).removeValue());

                    builder.setNegativeButton("Cancel", (dialog, which) -> {

                    });
                    builder.show();
                });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img, starImg, cancelledImg;
        TextView name,price,category,availabilityStatus;
        ImageButton btnEdit, btnDelete;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            starImg = itemView.findViewById(R.id.starImg);
            cancelledImg = itemView.findViewById(R.id.cancelledImg);
            name = itemView.findViewById(R.id.dish_name);
            price = itemView.findViewById(R.id.dish_price);
            category = itemView.findViewById(R.id.dish_category);
            btnEdit = itemView.findViewById(R.id.edit_button);
            btnDelete = itemView.findViewById(R.id.delete_button);
            availabilityStatus = itemView.findViewById(R.id.availabilityStatus);

        }
    }
}
