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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class updateDishActivity extends AppCompatActivity {

    EditText name, price, image;
    Button btnUpdate;
    RadioGroup dishTypeGroup, availabilityGroup;
    RadioButton regularDishBtn, specialDishBtn, availableBtn, notAvailableBtn;
    String Name, Price, Image, Category, Availability, DishType;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String[] category_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dish);

        name = findViewById(R.id.dish_name2);
        price = findViewById(R.id.dish_price2);
        image = findViewById(R.id.dish_image2);
        btnUpdate = findViewById(R.id.update_button);

        dishTypeGroup = findViewById(R.id.dishTypeRadioBtn);
        availabilityGroup = findViewById(R.id.availabilityRadioBtn);
        regularDishBtn = findViewById(R.id.regularDish2);
        specialDishBtn = findViewById(R.id.specialDish2);
        availableBtn = findViewById(R.id.available);
        notAvailableBtn = findViewById(R.id.notAvailable);

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

        autoCompleteTextView = findViewById(R.id.auto_complete_textview);
        adapterItems = new ArrayAdapter<>(this, R.layout.category_list_items, category_list);
        autoCompleteTextView.setAdapter(adapterItems);



        btnUpdate.setOnClickListener(v -> {

            String a, dt;
            a = getCheckedBtnText(availabilityGroup);
            dt = getCheckedBtnText(dishTypeGroup);

            Map<String, Object> map = new HashMap<>();
            map.put("Name", name.getText().toString());
            map.put("Price", Integer.parseInt(price.getText().toString()));
            map.put("Category", autoCompleteTextView.getText().toString());
            map.put("Image", image.getText().toString());
            map.put("DishType", dt);
            if(a.equals("Available"))
                map.put("Available", true);
            else
                map.put("Available", false);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Food Items").child(counterName);
            ref.orderByChild("Name").equalTo(Name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getKey();
                        ref.child(key).updateChildren(map)
                                .addOnSuccessListener(unused -> Toast.makeText(updateDishActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(updateDishActivity.this, "Failed while updating", Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });

                getAndSetIntentData();


            }

            void getAndSetIntentData() {
                if (getIntent().hasExtra("Name") && getIntent().hasExtra("Price") && getIntent().hasExtra("Category") &&
                        getIntent().hasExtra("Image")) {
                    //Getting data from intent
                    Name = getIntent().getStringExtra("Name");
                    Price = getIntent().getStringExtra("Price");
                    Category = getIntent().getStringExtra("Category");
                    Image = getIntent().getStringExtra("Image");
                    Availability = getIntent().getStringExtra("Availability");
                    DishType = getIntent().getStringExtra("DishType");


                    //Setting data from intent
                    name.setText(Name);
                    price.setText(Price);
                    autoCompleteTextView.setText(Category);
                    image.setText(Image);

                    if(Availability.equals("true"))
                        availabilityGroup.check(R.id.available);
                    else
                        availabilityGroup.check(R.id.notAvailable);

                    if(DishType.equals("Special Dish"))
                        dishTypeGroup.check(R.id.specialDish2);
                    else
                        dishTypeGroup.check(R.id.regularDish2);

                } else {
                    Toast.makeText(updateDishActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            private String getCheckedBtnText(RadioGroup radioGroup)
            {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton r = findViewById(radioId);
                return r.getText().toString();
            }
        }
