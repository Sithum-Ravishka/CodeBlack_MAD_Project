package com.example.codeblack;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import com.bumptech.glide.Glide;

public class detailsAdd extends AppCompatActivity {

    private static final String TAG = "detailsAdd";
    TextView name,pr,de,ro,bed,kit,adu,child,pho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: Started");
        getIncomingIntent();

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
