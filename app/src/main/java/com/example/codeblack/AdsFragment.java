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


public class AdsFragment extends Fragment {

        FirebaseAuth firebaseAuth;


        public AdsFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_ads, container, false);

            firebaseAuth = FirebaseAuth.getInstance();

            return view;
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
            setHasOptionsMenu(true);//to show menu option in fragment
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            inflater.inflate(R.menu.menu_main, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.action_add_ads){
                startActivity(new Intent(getActivity(), AddAdvertisementActivity.class));
            }
            if(id == R.id.action_add_vads){
                startActivity(new Intent(getActivity(), AddVehicleAdsActivity.class));
            }
            return super.onOptionsItemSelected(item);
        }
    }