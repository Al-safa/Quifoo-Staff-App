package com.example.quifoostaffapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {

    Button btnLogin;
    EditText inputUsername,inputPassword;
    static String counterName;
    String[][] userpwd = {
            {"Leeways","Leeways123"},
            {"HotChatCorner","HotChatCorner123"},
            {"JuiceCorner","JuiceCorner123"},
            {"RoyalCafe","RoyalCorner123"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnlogin);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(v -> performLogin());


    }

    private void performLogin() {
        String Username = inputUsername.getText().toString().trim();
        String Password = inputPassword.getText().toString().trim();

        if (Username.isEmpty()) {
            inputUsername.setError("Email ID is blank");
        } else if (Password.isEmpty()) {
            inputPassword.setError("Password is blank");
        } else {
            int i;
            for(i = 0; i < userpwd.length; i++)
            {
                if (Username.equals(userpwd[i][0]) && Password.equals(userpwd[i][1]) ) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    switch (userpwd[i][0])
                    {
                        case "Leeways":
                            counterName = "Leeways Canteen";
                            break;

                        case "HotChatCorner":
                            counterName = "Hot Chat Corner";
                            break;

                        case "JuiceCorner":
                            counterName = "Juice Corner";
                            break;

                        case "RoyalCafe":
                            counterName = "Royal Cafe";
                            break;

                    }
                    sendusertonextactivity();
                    break;
                }

            }
            if(i == userpwd.length)
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();

        }
    }


    private void sendusertonextactivity()
    {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
    }

}