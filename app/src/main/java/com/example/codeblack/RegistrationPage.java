package com.example.codeblack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationPage extends AppCompatActivity {

    //Views
    EditText uEmailReg, uPasswordReg;
    Button uRegisterBtn;
    TextView uHaveAccountTxt;

    //progressbar to display while register user
    ProgressDialog progressDialog;

    //Declare an instance FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //Actionbar and its Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        //Enable Back Button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Init
        uEmailReg = findViewById(R.id.emailLog);
        uPasswordReg = findViewById(R.id.passwordLog);
        uRegisterBtn = findViewById(R.id.loginBtn);
        uHaveAccountTxt = findViewById(R.id.notHave_accountTxt);

        //In your sign-in activity's onCreate method, et the shared instance of the FirebaseAuth object: Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        //handle register button click
        uRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Input email, password
                String email = uEmailReg.getText().toString().trim();
                String password = uPasswordReg.getText().toString().trim();
                //Validate
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //Set error and focus to email edit text
                    uEmailReg.setError("Invalid Email");
                    uEmailReg.setFocusable(true);
                }
                else if (password.length()<8){
                    //Set error and focus to password edit text
                    uPasswordReg.setError("Password length at least 8 characters");
                    uPasswordReg.setFocusable(true);
                }
                else {
                    registerUser(email,password); //Register the User
                }
            }
        });
        //handle login textView click listener
        uHaveAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPage.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String email, String password) {
        //email and password pattern is valid, show progress dialog and start registering user
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register page
                            progressDialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();
                            //When user is registered store user info in firebase realtime database too
                            //using HashMap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info in hashMap
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", ""); //Will add later (e.g. edit profile)
                            hashMap.put("phone", "");
                            hashMap.put("image", "");
                            hashMap.put("cover", "");

                            //fire base database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user data named "Users"
                            DatabaseReference reference = database.getReference("Users");
                            //put data within hashMap in database
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(RegistrationPage.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationPage.this, DashboardActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(RegistrationPage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}