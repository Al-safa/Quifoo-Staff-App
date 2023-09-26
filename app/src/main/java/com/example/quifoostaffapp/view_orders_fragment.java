package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class view_orders_fragment extends Fragment {

    RecyclerView recyclerView;
    ViewOrdersAdapter viewOrdersAdapter;
    DatabaseReference orderRef;

    public view_orders_fragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_orders_fragment, container, false);
        recyclerView = view.findViewById(R.id.ordersView);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(counterName);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));





        FirebaseRecyclerOptions<ViewOrdersModel> options = new FirebaseRecyclerOptions.Builder<ViewOrdersModel>()
                .setQuery(orderRef, ViewOrdersModel.class)
                .build();

        viewOrdersAdapter = new ViewOrdersAdapter(options);
        recyclerView.setAdapter(viewOrdersAdapter);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        viewOrdersAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewOrdersAdapter.stopListening();
    }
}