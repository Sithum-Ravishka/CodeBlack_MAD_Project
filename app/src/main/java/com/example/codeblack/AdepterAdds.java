package com.example.codeblack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeblack.models.ModelAdd;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AdepterAdds extends RecyclerView.Adapter<AdepterAdds.MyHolder> {

    Context context;
    List<ModelAdd> addList;


    //constructor
    public AdepterAdds (Context context, List<ModelAdd> addList){
        this.context = context;
        this.addList = addList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_add,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String addImage = addList.get(i).getpImage();
        String addTitle = addList.get(i).getpTitle();
        String addLocation = addList.get(i).getpState();
        String addKitchen = addList.get(i).getpKitchen();
        String addPrice = addList.get(i).getpDesPrice();
        String addRoom = addList.get(i).getpBedroom();
        String addBRoom = addList.get(i).getpBathroom();
        String addAdults = addList.get(i).getpAdult();
        String addChildren = addList.get(i).getpChildern();
        String addPhone = addList.get(i).getpHostPhoneNumber();
        String addDescription = addList.get(i).getpDescribePlace();

        //set  data
        holder.locationTv.setText(addLocation);
        holder.nameTv.setText(addTitle);
        try{
            Picasso.get().load(addImage).placeholder(R.drawable.ic_home_black)
                    .into(holder.avatarIv);
        }
        catch (Exception e){

        }

        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,detailsAdd.class);
                intent.putExtra("title",addTitle);
                intent.putExtra("Image",addImage);
                intent.putExtra("price",addPrice);
                intent.putExtra("Description",addDescription);
                intent.putExtra("rooms",addRoom);
                intent.putExtra("Bedroom",addBRoom);
                intent.putExtra("Kitchen",addKitchen);
                intent.putExtra("Adults",addAdults);
                intent.putExtra("Children",addChildren);
                intent.putExtra("Phone",addPhone);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        ImageView avatarIv;

        TextView nameTv,locationTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            locationTv = itemView.findViewById(R.id.locationTv);
        }
    }
}
