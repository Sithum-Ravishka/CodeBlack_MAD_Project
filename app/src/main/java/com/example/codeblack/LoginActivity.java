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

public class LoginActivity extends AppCompatActivity {

    //views
    EditText uEmailLog, uPasswordLog;
    TextView uNotHave_AccountTxt;
    Button uLoginBtn;

    //Declare an instance FirebaseAuth
    private FirebaseAuth mAuth;

    //Progress Dialog
    ProgressDialog PD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Actionbar and its Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        //Enable Back Button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //In your sign-in activity's onCreate method, et the shared instance of the FirebaseAuth object: Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //init
        uEmailLog = findViewById(R.id.emailLog);
        uPasswordLog = findViewById(R.id.passwordLog);
        uLoginBtn = findViewById(R.id.loginBtn);
        uNotHave_AccountTxt = findViewById(R.id.notHave_accountTxt);

        //Login button Click
        uLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uEmailLog.getText().toString().trim();
                String passw = uPasswordLog.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //Set error and focus to email
                    uEmailLog.setError("Invalid Email");
                    uEmailLog.setFocusable(true);
                }
                else {
                    //Valid email pattern
                    loginUser(email, passw);
                }

            }
        });
        //Not have account textView click
        uNotHave_AccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationPage.class));
            }
        });
        //init progress dialog
        PD = new ProgressDialog(this);
        PD.setMessage("Logging In");
    }

    private void loginUser(String email, String passw) {
        //show progress dialog
        PD.show();
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //dismiss progress dialog
                            PD.dismiss();
                            // Sign in success, dismiss dialog and start register page
                            FirebaseUser user = mAuth.getCurrentUser();
                            //User is logged in, so start LoginActivity
                            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //dismiss progress dialog
                PD.dismiss();
                //error, get and show error message
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}