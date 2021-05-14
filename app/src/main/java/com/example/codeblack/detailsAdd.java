package com.example.codeblack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class detailsAdd extends AppCompatActivity {

    private static final String TAG = "detailsAdd";
    TextView name,pr,de,ro,bed,kit,adu,child,pho;
    Button button2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        Log.d(TAG, "onCreate: Started");
        getIncomingIntent();


        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), booking.class);
                startActivity(intent);
            }
        });
    }

    public  void  getIncomingIntent(){
        if(getIntent().hasExtra("title")&& getIntent().hasExtra("Image")
                && getIntent().hasExtra("price") && getIntent().hasExtra("Description")
                && getIntent().hasExtra("rooms")&& getIntent().hasExtra("Bedroom")
                && getIntent().hasExtra("Kitchen")&& getIntent().hasExtra("Adults")
                && getIntent().hasExtra("Children")&& getIntent().hasExtra("Phone")){

            String imageUrl = getIntent().getStringExtra("Image");
            String imageTitle = getIntent().getStringExtra("title");
            String price = getIntent().getStringExtra("price");
            String des = getIntent().getStringExtra("Description");
            String rooms = getIntent().getStringExtra("rooms");
            String bedRooms = getIntent().getStringExtra("Bedroom");
            String kitchen = getIntent().getStringExtra("Kitchen");
            String adults = getIntent().getStringExtra("Adults");
            String children = getIntent().getStringExtra("Children");
            String phone = getIntent().getStringExtra("Phone");

            setImage(imageUrl,imageTitle,price,des,rooms,bedRooms,kitchen,adults,children,phone);
        }
    }

    private void setImage(String imageUrl, String imageTitle,String price, String des,
                          String rooms, String bedRooms,String kitchen, String adults,String children, String phone){

        name = findViewById(R.id.titelTv);
        pr = findViewById(R.id.priceTv);
        de = findViewById(R.id.desTv);
        ro = findViewById(R.id.roomTv);
        kit = findViewById(R.id.kitchenTv);
        bed = findViewById(R.id.bathTv);
        adu = findViewById(R.id.adultsTv);
        child = findViewById(R.id.childTv);
        pho = findViewById(R.id.phoneTv);

        name.setText(imageTitle);
        pr.setText(price);
        de.setText(des);
        ro.setText(rooms);
        kit.setText(kitchen);
        bed.setText(bedRooms);
        adu.setText(adults);
        child.setText(children);
        pho.setText(phone);

        ImageView imageView = findViewById(R.id.imageIv);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(imageView);
    }
}
