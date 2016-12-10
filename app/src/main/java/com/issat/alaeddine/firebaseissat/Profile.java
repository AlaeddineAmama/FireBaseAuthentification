package com.issat.alaeddine.firebaseissat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener{
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //defining view objects
    private TextView editTextWelcomeEmail;
    private Button buttonLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() ==null){
            //start  the Profile activity
            startActivity( new Intent(getApplicationContext(),Authentication.class));
            finish();
        }

        editTextWelcomeEmail =(TextView)findViewById(R.id.editTextWelcomeEmail);
        buttonLogOut= (Button)findViewById(R.id.buttonLogOut);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        editTextWelcomeEmail.setText("Welcome "+firebaseUser.getEmail());

        buttonLogOut.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if(view ==buttonLogOut){
         firebaseAuth.signOut();
            //start  the Profile activity
            startActivity( new Intent(this,Authentication.class));
            finish();
          }
            }
}
