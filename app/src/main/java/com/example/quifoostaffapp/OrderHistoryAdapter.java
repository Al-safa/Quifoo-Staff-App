package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderHistoryAdapter extends FirebaseRecyclerAdapter<OrderHistoryModel, OrderHistoryAdapter.ViewHolder> {

    DatabaseReference historyRef;
    public OrderHistoryAdapter(@NonNull FirebaseRecyclerOptions<OrderHistoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OrderHistoryModel model) {
        holder.orderId.setText(model.getOrderId());
        holder.orderTime.setText(model.getOrderTime());
        holder.orderStatus.setText(model.getOrderStatus());
        holder.orderDate.setText(model.getOrderDate());
        holder.orderTotalPrice.setText(model.getOrderTotalPrice());
        holder.user.setText(model.getUser());
        historyRef = FirebaseDatabase.getInstance().getReference().child("OrderHistory").child(counterName);
        setOrder(holder, model);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistoryholder, parent, false);
        return new OrderHistoryAdapter.ViewHolder(view);
    }

    private void setOrder(OrderHistoryAdapter.ViewHolder holder, OrderHistoryModel model) {
        historyRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String user = childsnapshot.getKey();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(holder.orderHistoryFoodView.getContext(), LinearLayoutManager.VERTICAL, false);
                    holder.orderHistoryFoodView.setLayoutManager(layoutManager);

                    FirebaseRecyclerOptions<OrderItemModel> options = new FirebaseRecyclerOptions.Builder<OrderItemModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderHistory").child(counterName).child(user).child("OrderItems"), OrderItemModel.class)
                            .build();

                    OrderItemAdapter orderItemAdapter = new OrderItemAdapter(options);
                    holder.orderHistoryFoodView.setAdapter(orderItemAdapter);
                    orderItemAdapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderId, orderTime, orderStatus, orderDate, orderTotalPrice, user;
        RecyclerView orderHistoryFoodView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId2);
            orderTime = itemView.findViewById(R.id.orderTime2);
            orderStatus = itemView.findViewById(R.id.orderStatus2);
            orderDate = itemView.findViewById(R.id.orderDate2);
            orderTotalPrice = itemView.findViewById(R.id.orderTotalPrice);
            orderHistoryFoodView = itemView.findViewById(R.id.orderHistoryFoodView);
            user = itemView.findViewById(R.id.userEmailID);
        }
    }
}
