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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: Started");
        getIncomingIntent();

    }

    public  void  getIncomingIntent(){
        if(getIntent().hasExtra("title")&& getIntent().hasExtra("Image")){
            String imageUrl = getIntent().getStringExtra("Image");
            String imageTitle = getIntent().getStringExtra("title");
            setImage(imageUrl,imageTitle);
        }
    }

    private void setImage(String imageUrl, String imageTitle){

        TextView name = findViewById(R.id.titelTv);
        name.setText(imageTitle);

        ImageView imageView = findViewById(R.id.imageIv);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(imageView);
    }
}
