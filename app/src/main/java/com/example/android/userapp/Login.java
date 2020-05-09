package com.example.android.userapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.userapp.database.RegisterDbHelper;

public class Login extends AppCompatActivity {
    // create references for all fields of Register form
    TextInputEditText e1,e2;
    Button b;
    TextView t1;
    //get the reference of RegisterDbHelper class
    RegisterDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create object of RegisterDbHelper class
        db=new RegisterDbHelper(this);

        e1=(TextInputEditText) findViewById(R.id.loginEmail);
        e2=(TextInputEditText) findViewById(R.id.loginPassword);
        t1=(TextView) findViewById(R.id.loginSignUp);
        b=(Button) findViewById(R.id.loginButton);

        /*create OnListener function
        This is called whenever the button "sign in" is pressed
        Get all the inputs given by the user
        */
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String Email=e1.getText().toString().trim();
                String Password=e2.getText().toString().trim();

                //check if any of the fields are empty
                if(Email.equals("") ||  Password.equals("")){
                    Toast.makeText(getApplicationContext(),"Some of the fields are empty!!!", Toast.LENGTH_SHORT).show();
                }
                //Check whether user already exists or not
                Boolean checkmail = db.checkEmail(Email);
                if(checkmail==false){
                    //call emailPasswordCheck method to check  whether login email and password match existing database
                  Boolean emailPassword= db.emailPasswordCheck(Email,Password);
                  //if both match
                  if(emailPassword==true){
                      Toast.makeText(getApplicationContext(),"Login successful, Welcome back.", Toast.LENGTH_SHORT).show();

                      //opens query page after successful login
                      Intent query=new Intent(Login.this,Query.class);
                      query.putExtra("rEmail",Email);
                      startActivity(query);

                      finish();
                  }
                  //if email or password doesnt match
                  else{
                      Toast.makeText(getApplicationContext(),"Email or password is incorrect, Try again.", Toast.LENGTH_SHORT).show();
                  }
                }
                //user doesn't exist
                else{
                    Toast.makeText(getApplicationContext(),"You are not registered. Please register by signing up.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //OnClickListener for Sign up TextView
        t1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //to open register page
                Intent register= new Intent(Login.this,MainActivity.class);
                startActivity(register);

            }
        });

    }
}
