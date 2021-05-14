package com.example.codeblack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.codeblack.models.ListAdepter;
import com.example.codeblack.models.ModelBook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetriveData extends AppCompatActivity {

    ListView myListview;
    List<ModelBook> bookList;

    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_data);

        myListview = findViewById(R.id.myList);
        bookList = new ArrayList<>();

        dbr = FirebaseDatabase.getInstance().getReference("Booking");

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelBook modelBook = ds.getValue(ModelBook.class);
                    bookList.add(modelBook);
                }
                ListAdepter adepter = new ListAdepter(RetriveData.this,bookList);
                myListview.setAdapter(adepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}