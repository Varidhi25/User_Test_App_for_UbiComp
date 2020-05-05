package com.example.android.userapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.userapp.database.CategoryDbHelper;
import com.example.android.userapp.database.RegisterDbHelper;

public class MainActivity extends AppCompatActivity {
    //get the reference of RegisterDbHelper class
   RegisterDbHelper db;

   // create references for all fields of Register form
    TextInputEditText e1,e2,e3,e4;
    TextView t1;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create object of RegisterDbHelper class
        db=new RegisterDbHelper(this);

        e1=(TextInputEditText) findViewById(R.id.regName);
        e2=(TextInputEditText) findViewById(R.id.regEmail);
        e3=(TextInputEditText) findViewById(R.id.regPhoneNumber);
        e4=(TextInputEditText) findViewById(R.id.regPassword);
        t1=(TextView) findViewById(R.id.regSignIn);
        //button
        b=(Button) findViewById(R.id.regButton);

        /*create OnListener function
        This is called whenever the button "sign up" is pressed
        Get all the inputs given by the user
        */
        b.setOnClickListener(new View.OnClickListener(){
           //implement the onClick method
            @Override
            public void onClick(View v) {


               //check if any of the fields are empty
                if(e1.getText().toString().trim().equals("") || e2.getText().toString().trim().equals("") || e3.getText().toString().equals("") ||e4.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Some of the fields are empty!!!", Toast.LENGTH_SHORT).show();
                }
                //if none of the fields are empty
              else{
                    //get the user inputs
                    String Name=e1.getText().toString().trim();
                    String Email=e2.getText().toString().trim();
                    long PhoneNo= Long.parseLong(e3.getText().toString());
                    String Password =e4.getText().toString().trim();
                  Boolean checkmail = db.checkEmail(Email);

                  if(e3.getText().toString().length() == 10){
                      //if the user doesn't exist already
                      if(checkmail==true){

                          Boolean insert= db.insertUser(Name,Email,Password,PhoneNo);

                          //check if database updated successfully
                          if(insert==true){
                              Toast.makeText(getApplicationContext(),"Registered Successfully.",Toast.LENGTH_SHORT).show();
                              Intent subscribe=new Intent(MainActivity.this,Subscribe.class);
                              subscribe.putExtra("rEmail",Email);
                              startActivity(subscribe);
                              finish();
                          }
                      }

                      //if the user exists already
                      else{
                          Toast.makeText(getApplicationContext(),"Account already exits, Try to Sign in instead.",Toast.LENGTH_SHORT).show();
                      }
                  }
                  else{
                      Toast.makeText(getApplicationContext(),"Invalid Phone Number, Re-enter.",Toast.LENGTH_SHORT).show();
                  }


                }
            }
        });

        //OnClickListener for Sign In TextView
     t1.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View v) {
             //to open login page
             Intent login= new Intent(MainActivity.this,Login.class);
             startActivity(login);

         }
     });

    }
}
