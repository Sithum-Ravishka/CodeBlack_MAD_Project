package com.example.codeblack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeblack.models.ModelBook;

import java.util.List;

public class AdepterBooking extends RecyclerView.Adapter<AdepterBooking.MyHolder> {

    Context context;
    List<ModelBook> bookList;

    public AdepterBooking(FragmentActivity activity, List<ModelBook> bookList) {
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_book.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_book,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        String name = bookList.get(position).getName();
        String email = bookList.get(position).getEmail();
        String phone = bookList.get(position).getPhone();
        String date = bookList.get(position).getProvince();

        //set data
        holder.nameTv.setText(name);
        holder.emailTv.setText(email);
        holder.phoneTv.setText(phone);
        holder.dateTv.setText(date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,detailsAdd.class);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("phone",phone);
                intent.putExtra("date",date);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //views from row_booking xml
        TextView nameTv,dateTv,emailTv,phoneTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            nameTv = itemView.findViewById(R.id.nameTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
        }
    }
}
