package com.issat.alaeddine.firebaseissat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity implements View.OnClickListener {
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button buttonlogin;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

          //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
             //start  the Profile activity
           startActivity( new Intent(getApplicationContext(),Profile.class));
            finish();
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonlogin =(Button)findViewById(R.id.buttonLogIn);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            // email  is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            //stopping the  function execution
            return;
        }

        if(TextUtils.isEmpty(password)){
            // password is  empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            //stopping the  function execution
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){;
                            //display some message here
                            Toast.makeText(Authentication.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            //start  the Profile activity
                            startActivity( new Intent(getApplicationContext(),Profile.class));
                            finish();
                        }else{
                            //display some message here
                            Toast.makeText(Authentication.this,"Registration Error"+task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
    private void loginUser() {
        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            // email  is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            //stopping the  function execution
            return;
        }

        if(TextUtils.isEmpty(password)){
            // password is  empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            //stopping the  function execution
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //start  the  Profile activity
                    startActivity( new Intent(getApplicationContext(),Profile.class));
                    finish();

                }else{
                    //display some message here
                    Toast.makeText(Authentication.this,"Log In Error"+task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //calling register method on click
            registerUser();
        }else if(view==buttonlogin){
            //calling login method on click

            loginUser();


        }
    }


}
