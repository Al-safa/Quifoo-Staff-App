package com.example.quifoostaffapp;

import static android.content.ContentValues.TAG;
import static com.example.quifoostaffapp.login.counterName;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class ViewOrdersAdapter extends FirebaseRecyclerAdapter <ViewOrdersModel, ViewOrdersAdapter.ViewHolder> {

    DatabaseReference orderRef;


    public ViewOrdersAdapter(@NonNull FirebaseRecyclerOptions<ViewOrdersModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ViewOrdersModel model) {
        holder.orderId.setText(model.getOrderId());
        holder.orderTime.setText(model.getOrderTime());
        holder.orderStatus.setText(model.getOrderStatus());
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(counterName);

        setOrder(holder, model);
        setButton(holder, model);

        holder.acceptBtn.setOnClickListener(v -> orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String id = dataSnapshot.getKey();
                    Map<String,Object> status = new HashMap<>();
                    status.put("OrderStatus","Accepted");
                    orderRef.child(id).updateChildren(status);
                    holder.acceptBtn.setText("Accepted");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
        holder.finishBtn.setOnClickListener(v -> orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String id = dataSnapshot.getKey();
                    Map<String,Object> status = new HashMap<>();
                    status.put("OrderStatus","Finished");
                    orderRef.child(id).updateChildren(status);
                    holder.finishBtn.setVisibility(View.INVISIBLE);
                    holder.deliverBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        }));

        holder.deliverBtn.setOnClickListener(v -> orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String id = dataSnapshot.getKey();
                    Map<String,Object> status = new HashMap<>();
                    status.put("OrderStatus","Delivered");
                    orderRef.child(id).updateChildren(status);

                    insertDatabase(id);
                    deleteDatabase(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));

        holder.close.setOnClickListener(v -> orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String user = dataSnapshot.getKey();

                    insertDatabase(user);
                    deleteDatabase(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderholder, parent, false);
        return new ViewHolder(view);
    }

    private void setOrder(ViewHolder holder, ViewOrdersModel model)
    {
        orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childsnapshot : snapshot.getChildren()) {
                    String user = childsnapshot.getKey();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(holder.orderItemView.getContext(), LinearLayoutManager.VERTICAL, false);
                    holder.orderItemView.setLayoutManager(layoutManager);

                    FirebaseRecyclerOptions<OrderItemModel> options = new FirebaseRecyclerOptions.Builder<OrderItemModel>()
                            .setQuery(orderRef.child(user).child("OrderItems"), OrderItemModel.class)
                            .build();

                    OrderItemAdapter orderItemAdapter = new OrderItemAdapter(options);
                    holder.orderItemView.setAdapter(orderItemAdapter);
                    orderItemAdapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setButton(ViewHolder holder, ViewOrdersModel model)
    {
       orderRef.orderByChild("OrderId").equalTo(model.getOrderId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren())
               {
                   String user = dataSnapshot.getKey();
                   orderRef.child(user).child("OrderStatus").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           String status = snapshot.getValue(String.class);

                           if(status!=null) {
                               switch (status) {
                                   case "Accepted":
                                       holder.acceptBtn.setText("Accepted");
                                       break;
                                   case "Finished":
                                       holder.acceptBtn.setText("Accepted");
                                       holder.finishBtn.setVisibility(View.INVISIBLE);
                                       holder.deliverBtn.setVisibility(View.VISIBLE);
                                       break;
                                   case "Delivered":
                                       holder.itemView.setVisibility(View.GONE);
                                       break;
                                   case "Cancelled":
                                       holder.acceptBtn.setVisibility(View.INVISIBLE);
                                       holder.finishBtn.setVisibility(View.INVISIBLE);
                                       holder.deliverBtn.setVisibility(View.INVISIBLE);
                                       holder.orderStatus.setTextColor(Color.RED);
                                       holder.close.setVisibility(View.VISIBLE);
                                       break;
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void insertDatabase(String user)
    {
        orderRef.child(user).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                FirebaseDatabase.getInstance().getReference().child("OrderHistory").child(counterName).push()
                        .setValue(snapshot.getValue(), ((error, ref) -> {
                    if (error != null) {
                        Log.d(TAG, "Copy Failed");
                    } else {
                        Log.d(TAG, "Successful");
                    }
                }));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteDatabase(String user)
    {
        orderRef.child(user).removeValue();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

            TextView orderId, orderTime, orderStatus;
            RecyclerView orderItemView;
            Button acceptBtn, finishBtn, deliverBtn;
            ImageView close;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                orderId = itemView.findViewById(R.id.orderId);
                orderTime = itemView.findViewById(R.id.orderTime);
                orderStatus = itemView.findViewById(R.id.orderStatus);
                orderItemView = itemView.findViewById(R.id.orderItemsView);
                acceptBtn = itemView.findViewById(R.id.acceptOrderBtn);
                finishBtn = itemView.findViewById(R.id.finishOrderBtn);
                deliverBtn = itemView.findViewById(R.id.deliverOrderBtn);
                close = itemView.findViewById(R.id.closeBtn);

            }
        }
    }



