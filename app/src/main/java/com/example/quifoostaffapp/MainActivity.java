package com.example.quifoostaffapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quifoostaffapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new view_orders_fragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.view_orders_fragment:
                    replacefragment(new view_orders_fragment());
                    break;
                case R.id.customize_menu_fragment:
                    replacefragment(new customize_menu_fragment());
                    break;
                case R.id.view_history_fragment:
                    replacefragment(new view_history_fragment());
                    break;
            }
            return true;
        });



    }

    public void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout,fragment);
        fragmentTransaction.commit();

    }







}