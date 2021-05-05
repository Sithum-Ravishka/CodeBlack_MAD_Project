package com.example.codeblack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //View from xml
    ImageView avatarTv;
    TextView profileNameTv, profileEmailTv, profilePhoneTv;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        //init views
        avatarTv = view.findViewById(R.id.avatarIv);
        profileNameTv = view.findViewById(R.id.profileNameTv);
        profileEmailTv= view.findViewById(R.id.profileEmailTv);
        profilePhoneTv = view.findViewById(R.id.profilePhoneTv);

        /*We have to get info of currently signed in user. We can get it using user's email or uid I'm gonna retrieve user detail using email*/
        /*by using orderByChild query we will show the detail from a node whose key named email has value email equal currently signed in email.
        * It will search all node, where the key matches it will get its detail*/

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check util required data get
                for (DataSnapshot ds : snapshot.getChildren()){
                    //Get data
                    String name = ""+ ds.child("name").getValue();
                    String email = ""+ ds.child("email").getValue();
                    String phone = ""+ ds.child("phone").getValue();
                    String image = ""+ ds.child("image").getValue();

                    //Set data
                    profileNameTv.setText(name);
                    profileEmailTv.setText(email);
                    profilePhoneTv.setText(phone);
                    try{
                        //If image is received then set
                        Picasso.get().load(image).into(avatarTv);
                    }catch (Exception e){
                        //If there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarTv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}