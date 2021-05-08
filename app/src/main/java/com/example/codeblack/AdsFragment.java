package com.example.codeblack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdsFragment extends Fragment {
    RecyclerView recyclerView;
    AdepterAdds adepterAdds;
    List<modelAdd> addList;




    public AdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.row_add, container, false);

        //init recycler view
        recyclerView = view.findViewById(R.id.adds_recyclerView);

        //set it's properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //init add list
        addList = new ArrayList<>();

        //get all adds
        getAllAdds();
        return view;
    }

    private void getAllAdds() {

        FirebaseUser fAdd = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named add containing data info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        //get all data from path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    modelAdd modelAdd = ds.getValue(com.example.codeblack.modelAdd.class);

                    //get all users accept currently signed in user
                    if(!modelAdd.getUid().equals(fAdd.getUid())){
                        addList.add(modelAdd);

                    }
                    //adepter
                    adepterAdds = new AdepterAdds(getActivity(), addList);
                    //set adepter to recycler view
                    recyclerView.setAdapter(adepterAdds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}