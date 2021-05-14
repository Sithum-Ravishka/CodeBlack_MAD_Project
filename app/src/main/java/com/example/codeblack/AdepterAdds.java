package com.example.codeblack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeblack.models.ModelAdd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AdepterAdds extends RecyclerView.Adapter<AdepterAdds.MyHolder> {

    Context context;
    List<ModelAdd> addList;

    String myUid;

    //constructor
    public AdepterAdds (Context context, List<ModelAdd> addList){
        this.context = context;
        this.addList = addList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_add,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String uid = addList.get(i).getUid();
        String pId = addList.get(i).getpId();
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

        //Handle Button Click
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will continue later upgrade
                showMoreOption(holder.moreBtn, uid, pId, myUid, addImage);
            }
        });
    }

    private void showMoreOption(ImageButton moreBtn, String uid, String pId, String myUid, String addImage) {

        PopupMenu popupMenu = new PopupMenu(context, moreBtn, Gravity.END);

        if(uid.equals(myUid)){
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Delete");
        }


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id==0){
                    beginDelete(pId, addImage);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void beginDelete(String pId, String addImage) {
        if (addImage.equals("noImage")) {
            deleteWithgoutImage(pId);
        }
        else {
            deleteWithImage(pId, addImage);
        }
    }

    private void deleteWithImage(String pId, String addImage) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting....");

        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(addImage);
        picRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Query fquery = FirebaseDatabase.getInstance().getReference("Ads").orderByChild("pId").equalTo(pId);
                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteWithgoutImage(String pId) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting....");

        Query fquery = FirebaseDatabase.getInstance().getReference("Ads").orderByChild("pId").equalTo(pId);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        ImageButton moreBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            locationTv = itemView.findViewById(R.id.locationTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
        }
    }
}
