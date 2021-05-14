package com.example.codeblack.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.codeblack.R;

import java.util.List;

public class ListAdepter extends ArrayAdapter {

    private Activity mContext;
    List<ModelBook> bookList;
    public ListAdepter(Activity mContext,List<ModelBook> bookList) {
            super(mContext, R.layout.list_layout,bookList);
            this.mContext = mContext;
            this.bookList = bookList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = mContext.getLayoutInflater();
        View listItemView = layoutInflater.inflate(R.layout.list_layout,null,true);

        TextView nameTV,emailTV,phoneTV,dateTV;

        nameTV = listItemView.findViewById(R.id.nameTv);
        emailTV = listItemView.findViewById(R.id.emailTv);
        phoneTV = listItemView.findViewById(R.id.phoneTv);
        dateTV = listItemView.findViewById(R.id.dateTv);

        ModelBook modelBook = bookList.get(position);

        nameTV.setText(modelBook.getName());
        emailTV.setText(modelBook.getEmail());
        phoneTV.setText(modelBook.getPhone());
        dateTV.setText(modelBook.getProvince());

        return listItemView;
    }
}
