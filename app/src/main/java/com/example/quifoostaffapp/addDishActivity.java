package com.example.quifoostaffapp;

import static com.example.quifoostaffapp.login.counterName;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addDishActivity extends AppCompatActivity {

    EditText name_input, price_input, image_input;
    Button add_button, back_button;
    RadioGroup radioGroup;
    RadioButton normalDishRadioBtn, specialDishRadioBtn;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String[] category_list;
    String dishType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        switch (counterName)
        {
            case "Leeways Canteen":
                category_list = new String[]{"Lunch","Tiffin"};
                break;

            case "Hot Chat Corner":
                category_list = new String[]{"Snacks"};
                break;

            case "Juice Corner":
                category_list = new String[]{"Beverage"};
                break;

            case "Royal Cafe":
                category_list = new String[]{"Sandwich","Snacks","Beverage"};
                break;

        }

        name_input = findViewById(R.id.dish_name);
        price_input = findViewById(R.id.dish_price);
        image_input = findViewById(R.id.dish_image);
        add_button = findViewById(R.id.add_button);
        back_button = findViewById(R.id.back_button);
        normalDishRadioBtn = findViewById(R.id.regularDish);
        specialDishRadioBtn = findViewById(R.id.specialDish);
        autoCompleteTextView = findViewById(R.id.auto_complete_textview2);
        adapterItems = new ArrayAdapter<>(this, R.layout.category_list_items, category_list);
        autoCompleteTextView.setAdapter(adapterItems);
        radioGroup = findViewById(R.id.radioGroup);

        add_button.setOnClickListener(v -> {
            insertData();
            clearAll();
        });

        back_button.setOnClickListener(view -> finish());
        normalDishRadioBtn.setOnClickListener(v -> dishType = "Regular Dish");
        specialDishRadioBtn.setOnClickListener(v -> dishType = "Special Dish");
    }

        private void insertData()
        {
            Map<String,Object> map = new HashMap<>();
            map.put("Name",name_input.getText().toString());
            map.put("Price", Integer.parseInt(price_input.getText().toString()));
            map.put("Category", autoCompleteTextView.getText().toString());
            map.put("Image", image_input.getText().toString());
            map.put("DishType", dishType);
            map.put("Available", true);

            FirebaseDatabase.getInstance().getReference().child("Food Items").child(counterName).push()
                    .setValue(map)
                    .addOnSuccessListener(unused -> Toast.makeText(addDishActivity.this, "Dish Added Successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(addDishActivity.this, "Error while Insertion", Toast.LENGTH_SHORT).show());
        }



        private void clearAll()
        {
            name_input.setText("");
            price_input.setText("");
            autoCompleteTextView.setText("");
            image_input.setText("");
            radioGroup.clearCheck();
        }

    }
