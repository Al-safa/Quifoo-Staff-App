package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class customize_menu_fragment extends Fragment {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    FloatingActionButton add_button;

    public customize_menu_fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customize_menu_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);
        add_button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), addDishActivity.class);
            startActivity(intent);

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food Items").child(counterName), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}

