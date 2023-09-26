package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class view_history_fragment extends Fragment {

    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;

    public view_history_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_history_fragment, container, false);
        recyclerView = view.findViewById(R.id.orderHistoryView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<OrderHistoryModel> options = new FirebaseRecyclerOptions.Builder<OrderHistoryModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderHistory").child(counterName) , OrderHistoryModel.class)
                .build();

        orderHistoryAdapter = new OrderHistoryAdapter(options);
        recyclerView.setAdapter(orderHistoryAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        orderHistoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderHistoryAdapter.stopListening();
    }
}