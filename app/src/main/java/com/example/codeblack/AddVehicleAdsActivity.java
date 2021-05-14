package com.example.codeblack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class  AddVehicleAdsActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;

    ActionBar actionBar;

    //Permission constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    //Array of permission to be requested
    String[] cameraPermissions;
    String[] storagePermissions;

    //Views
    EditText vname, vcn, vc, vdc, vvt, vvc, vvm, vpfkm, vpes, vsva, vdsc;
    ImageView adsImage;
    Button adsPublishBtn;

    //User info
    String name, email, uid, dp;

    //uri of picked image
    Uri image_uri = null;

    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_ads);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Create New Advertisement");

        //Enable back button in actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        actionBar.setTitle(email);

        //Get some info of current user to include in post
        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    name = ""+ ds.child("name").getValue();
                    email = ""+ ds.child("email").getValue();
                    dp = ""+ ds.child("image").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //init views data
        vname = findViewById(R.id.vname);
        vcn = findViewById(R.id.vcn);
        vc = findViewById(R.id.vc);
        vdc = findViewById(R.id.vdc);
        vvt = findViewById(R.id.vvt);
        vvc = findViewById(R.id.vvc);
        vvm = findViewById(R.id.vvm);
        vpfkm = findViewById(R.id.vpfkm);
        vpes = findViewById(R.id.vpes);
        vsva = findViewById(R.id.vsva);
        vdsc = findViewById(R.id.vdsc);
        adsImage = findViewById(R.id.adsImage);
        adsPublishBtn = findViewById(R.id.adsPublishBtn);

        //Get image from camera/gallery on click
        adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Image pick dialog
                showImagePickDialog();
            }
        });

        //Upload button click listener
        adsPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data(title, description) from EditText
                String userName = vname.getText().toString().trim();
                String userContactNumber = vcn.getText().toString().trim();
                String userCity = vc.getText().toString().trim();
                String userDistrid = vdc.getText().toString().trim();
                String userVehicleType = vvt.getText().toString().trim();
                String userVehicleCondition = vvc.getText().toString().trim();
                String userVehicleModel = vvm.getText().toString().trim();
                String userprice = vpfkm.getText().toString().trim();
                String usergetpssenger = vpes.getText().toString().trim();
                String userselect = vsva.getText().toString().trim();
                String userDesctription = vdsc.getText().toString().trim();

                //If you try to submit without entering the Details, the location where the details were not entered will be shown
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Name...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userContactNumber)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Contact Number...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userCity)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your City...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userDistrid)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your District...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userVehicleType)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Vehicle Type...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userVehicleCondition)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Vehicle Condition...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userVehicleModel)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Vehicle Model...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userprice)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Your Price For 1 KM...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(usergetpssenger)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Passengers...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userselect)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Available or Not...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userDesctription)){
                    Toast.makeText(AddVehicleAdsActivity.this, "Enter Desctription...", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (image_uri==null){
                    //Post without image
                    uploadData(userName, userContactNumber, userCity, userDistrid, userVehicleType, userVehicleCondition, userVehicleModel, userprice, usergetpssenger, userselect, userDesctription,  "noImage");
                }
                else {
                    //Post with image
                    uploadData(userName, userContactNumber, userCity, userDistrid, userVehicleType, userVehicleCondition, userVehicleModel, userprice, usergetpssenger, userselect, userDesctription, String.valueOf(image_uri));
                }
            }
        });
    }

    private void uploadData(String userName, String userContactNumber, String userCity, String userDistrid, String userVehicleType, String userVehicleCondition, String userVehicleModel, String userprice, String usergetpssenger, String userselect, String userDesctription, String uri) {
        pd.setMessage("Publishing Advertisement...");
        pd.show();
        pd.dismiss();

        //For ads-image name, ads-id, post publish time
        String timeStamp = String.valueOf(System.currentTimeMillis());

        String filePathAndName = "VehicleAdd/" + "VehicleAdd_" + timeStamp;

        if (!uri.equals("noImage")){
            //Ads with image
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Image is uploaded to firebase storage, now get it's url
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()){
                                //Url is received upload ads to firebase database

                                HashMap<Object, String> hashMap = new HashMap<>();
                                //Put ads info
                                hashMap.put("uid", uid);
                                hashMap.put("uName", name);
                                hashMap.put("uEmail",email);
                                hashMap.put("uDp",dp);
                                hashMap.put("pId",timeStamp);
                                hashMap.put("vname",userName);
                                hashMap.put("vcontact", userContactNumber);
                                hashMap.put("vcity",userCity );
                                hashMap.put("vdistrict",userDistrid);
                                hashMap.put("vvehicleType",userVehicleType);
                                hashMap.put("vvehicleCondition",userVehicleCondition);
                                hashMap.put("vvehicleModle",userVehicleModel);
                                hashMap.put("vprice",userprice );
                                hashMap.put("vpassenger",usergetpssenger);
                                hashMap.put("vselect",userselect);
                                hashMap.put("vdesctription",userDesctription);
                                hashMap.put("pImage", downloadUri);
                                hashMap.put("pTime", timeStamp);

                                //Path to store Ads data
                                DatabaseReference ref =FirebaseDatabase.getInstance().getReference("VehicleAdd");
                                //Put data in this ref
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Publish in database
                                                Toast.makeText(AddVehicleAdsActivity.this, "Ads Publishing", Toast.LENGTH_SHORT).show();
                                                //Reset views
                                                vname.setText("");
                                                vcn.setText("");
                                                vc.setText("");
                                                vdc.setText("");
                                                vvt.setText("");
                                                vvc.setText("");
                                                vvm.setText("");
                                                vpfkm.setText("");
                                                vpes.setText("");
                                                vsva.setText("");
                                                vdsc.setText("");
                                                adsImage.setImageURI(null);
                                                image_uri = null;

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Failed publish
                                                pd.dismiss();
                                                Toast.makeText(AddVehicleAdsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed uploading image
                            pd.dismiss();
                            Toast.makeText(AddVehicleAdsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            //Ads without image

            HashMap<Object, String> hashMap = new HashMap<>();
            //Put ads info
            hashMap.put("uid", uid);
            hashMap.put("uName", name);
            hashMap.put("uEmail",email);
            hashMap.put("uDp",dp);
            hashMap.put("pId",timeStamp);
            hashMap.put("vname",userName);
            hashMap.put("vcontact", userContactNumber);
            hashMap.put("vcity",userCity );
            hashMap.put("vdistrict",userDistrid);
            hashMap.put("vvehicleType",userVehicleType);
            hashMap.put("vvehicleCondition",userVehicleCondition);
            hashMap.put("vvehicleModle",userVehicleModel);
            hashMap.put("vprice",userprice );
            hashMap.put("vpassenger",usergetpssenger);
            hashMap.put("vselect",userselect);
            hashMap.put("vdesctription",userDesctription);
            hashMap.put("pImage", "noImage");
            hashMap.put("pTime", timeStamp);

            //Path to store Ads data
            DatabaseReference ref =FirebaseDatabase.getInstance().getReference("VehicleAdd");
            //Put data in this ref
            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Publish in database
                            Toast.makeText(AddVehicleAdsActivity.this, "Ads Publishing", Toast.LENGTH_SHORT).show();
                            //Reset views
                            vname.setText("");
                            vcn.setText("");
                            vc.setText("");
                            vdc.setText("");
                            vvt.setText("");
                            vvc.setText("");
                            vvm.setText("");
                            vpfkm.setText("");
                            vpes.setText("");
                            vsva.setText("");
                            vdsc.setText("");
                            adsImage.setImageURI(null);
                            image_uri = null;

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed publish
                            pd.dismiss();
                            Toast.makeText(AddVehicleAdsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void showImagePickDialog() {
        //Option(camera, gallery) to show in dialog
        String[] options = {"Camera", "Gallery"};

        //Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");
        //Set option to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Item click handle
                if (which == 0){
                    //Camera clicked
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        PickFromCamera();
                    }
                }
                if (which==1){
                    //Gallery clicked
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        PickFromGallery();
                    }
                }
            }
        });
        //Create and show dialog
        builder.create().show();
    }

    private void PickFromCamera() {
        //Intent of picking image from device camera
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //Put image uri
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        //Intent to start camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private void PickFromGallery() {
        //Pick from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission() {
        //Check if storage permission is enabled or not
        //Return true if enabled
        //Return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        //Request runtime storage permission
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        //Check if storage permission is enabled or not
        //Return true if enabled
        //Return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        //Request runtime storage permission
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    public void checkUserStatus(){
        //get current User
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            email = user.getEmail();
            uid = user.getUid();
        }else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //goto previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        menu.findItem(R.id.action_add_ads).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item ID
        int id = item.getItemId();
        if (id == R.id.action_logout){
            firebaseAuth.signOut ();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    //Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                //Picking from camera, first check if camera and storage permission allowed or not
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean StorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && StorageAccepted){
                        //Permission enabled
                        PickFromCamera();
                    }
                    else {
                        //Permission denied
                        Toast.makeText(this, "Camera & Storage both permissions are necessary", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                //Picking from gallery, first check if storage permission allowed or not
                if (grantResults.length > 0){
                    boolean StorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (StorageAccepted){
                        //Permission enabled
                        PickFromGallery();
                    }
                    else {
                        //Permission denied
                        Toast.makeText(this, "Storage permissions are necessary", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*This method will be called after picking image from camera or Gallery*/
        if (resultCode == RESULT_OK){

            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                //Image is picked from gallery, get uri image
                image_uri = data.getData();

                adsImage.setImageURI(image_uri);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //Image is picked from camera, get uri image

                adsImage.setImageURI(image_uri);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}