package com.example.codeblack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.codeblack.models.ModelAdd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    //firebase auth
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    AdepterAdds adepterAdds;
    List<ModelAdd> addList;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads, container, false);


        //init
        firebaseAuth = FirebaseAuth.getInstance();

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelAdd modelAdd = ds.getValue(ModelAdd.class);

                    //get all adds accept currently signed in user
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

    private void searchAdds(String query) {

        FirebaseUser fAdd = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named add containing data info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        //get all data from path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelAdd modelAdd = ds.getValue(ModelAdd.class);

                    //get all search adds accept currently signed in user
                    if(!modelAdd.getUid().equals(fAdd.getUid())){

                        if(!modelAdd.getpTitle().toLowerCase().contains(query.toLowerCase())||
                                modelAdd.getpState().toLowerCase().contains(query.toLowerCase())){
                            addList.add(modelAdd);
                        }


                    }
                    //adepter
                    adepterAdds = new AdepterAdds(getActivity(), addList);
                    //refresh adepter
                    adepterAdds.notifyDataSetChanged();
                    //set adepter to recycler view
                    recyclerView.setAdapter(adepterAdds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //user is signed in stay here
            //set email of logged in user
            //uProfileTxt.setText(user.getEmail());
        }
        else{
            //user not signed in, go to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);//show menu option in fragment
        super.onCreate(savedInstanceState);
    }

    /*inflate option menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //Inflating menu
        menuInflater.inflate(R.menu.menu_main, menu);

        //search view
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchAdds(s);
                }
                else {
                    getAllAdds();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchAdds(s);
                }
                else {
                    getAllAdds();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, menuInflater);
    }
    /*handle menu item click*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}
