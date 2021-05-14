package com.example.codeblack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class booking extends AppCompatActivity {

    EditText nameEt,emailEt,phoneEt,provinceEt;
    Button bookBtn;

    ProgressDialog pd;

    //database reference
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //init
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        provinceEt = findViewById(R.id.provinceEt);
        bookBtn = findViewById(R.id.bookBtn);

        //create database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Booking");

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();
                String date = provinceEt.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(booking.this, "Enter name...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(booking.this, "Enter email...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(booking.this, "Enter phone...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(date)){
                    Toast.makeText(booking.this, "Enter date...", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    bookingData bd = new bookingData(name, email, phone, date);
                    databaseReference.push().setValue(bd);
                    Toast.makeText(booking.this, "Successfully completed", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), details2.class);
                startActivity(intent);
            }
        });
    }

}