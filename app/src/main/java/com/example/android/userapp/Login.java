package com.example.android.userapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.userapp.database.RegisterDbHelper;
import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.model.details;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login extends AppCompatActivity {
    // create references for all fields of Register form
    TextInputEditText e1,e2;
    Button b;
    TextView t1;
    String sEmail, sPassword;
    //get the reference of RegisterDbHelper class
    RegisterDbHelper db;
    SensorObjectDbHelper sdb;
    int now_year,now_day,now_month;
    String now_Date,next_date;
    Properties properties;
    ArrayList<details> detailsArrayList_today,detailsArrayList_next;
    Session session;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //create object of RegisterDbHelper class
        db=new RegisterDbHelper(this);
        sdb=new SensorObjectDbHelper(this);
        e1=(TextInputEditText) findViewById(R.id.loginEmail);
        e2=(TextInputEditText) findViewById(R.id.loginPassword);
        t1=(TextView) findViewById(R.id.loginSignUp);
        b=(Button) findViewById(R.id.loginButton);

        //get today's date
        Calendar cal = Calendar.getInstance();
        now_year=cal.get(Calendar.YEAR);
        now_month=cal.get(Calendar.MONTH)+1;
        now_day=cal.get(Calendar.DAY_OF_MONTH);

        //sender username and password
        sEmail="dvaridhi25@gmail.com";
        sPassword="cvaridhi1999";

        if(now_month>0 && now_month<=9)
            now_Date=now_year+"-0"+(now_month)+"-"+now_day;
        else now_Date=now_year+"-"+(now_month)+"-"+now_day;

        detailsArrayList_today=sdb.getExp(now_Date);
        if((now_month==1 || now_month==3 || now_month==5 || now_month==7 || now_month==8)&& now_day==31)
            next_date=now_year+"-0"+(now_month+1)+"-1";
        else if(now_month==10 && now_day==31)
            next_date=now_year+"-"+(now_month+1)+"-1";
        else if((now_month==4 || now_month==6) &&  now_day==30)
            next_date=now_year+"-0"+(now_month+1)+"-1";
        else if((now_month==9 || now_month==11) &&  now_day==30)
            next_date=now_year+"-"+(now_month+1)+"-1";
        else if (now_month==2 && now_day==28)
            next_date=now_year+"-0"+(now_month+1)+"-1";
        else if (now_month==12 && now_day==31)
            next_date=(now_year+1)+"-01-1";
        else if(now_month>0 && now_month<=9)
            next_date=now_year+"-0"+now_month+"-"+(now_day+1);
        else if(now_month>10)
            next_date=now_year+"-"+now_month+"-"+(now_day+1);
        detailsArrayList_next=sdb.getExp(next_date);
        properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });
        /*create OnListener function
        This is called whenever the button "sign in" is pressed
        Get all the inputs given by the user
        */
        b.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                long start = System.currentTimeMillis();
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

                              if(detailsArrayList_today.size()>0) {
                                  Message message = new MimeMessage(session);
                                  try {
                                      message.setFrom(new InternetAddress(sEmail));
                                      ArrayList<String> emailList = db.getEmail();
                                      InternetAddress[] recipientAddress = new InternetAddress[1];
                                      recipientAddress[0] = new InternetAddress(Email.trim());

                                      message.setRecipients(Message.RecipientType.TO, recipientAddress);

                                      message.setSubject("Some of the items will expire today...");
                                      String mess = new String();
                                      for (int i = 0; i < detailsArrayList_today.size(); i++)
                                          mess += detailsArrayList_today.get(i).getItemName() +
                                                  "  Category: " + detailsArrayList_today.get(i).getCategory() +
                                                  "  Brand: " + detailsArrayList_today.get(i).getBrand() +
                                                  "  Quantity:  " + detailsArrayList_today.get(i).getQuantity() + "\n";

                                      message.setText(mess);
                                      Transport.send(message);
                                  } catch (MessagingException e) {
                                      e.printStackTrace();
                                  }
                              }

                              if(detailsArrayList_next.size()>0){
                                  Message message = new MimeMessage(session);
                                  try {
                                      message.setFrom(new InternetAddress(sEmail));
                                      ArrayList<String> emailList = db.getEmail();
                                      InternetAddress[] recipientAddress = new InternetAddress[1];
                                      recipientAddress[0] = new InternetAddress(Email.trim());

                                      message.setRecipients(Message.RecipientType.TO, recipientAddress);

                                      message.setSubject("Some of the items will expire tomorrow...");
                                      String mess1 =new String();
                                      for(int i=0;i<detailsArrayList_next.size();i++)
                                          mess1+=detailsArrayList_next.get(i).getItemName()+
                                                  "  Category: "+detailsArrayList_next.get(i).getCategory() +
                                                  "  Brand: "+detailsArrayList_next.get(i).getBrand()+
                                                  "  Quantity:  "+detailsArrayList_next.get(i).getQuantity()+"\n";

                                      message.setText(mess1);
                                      Transport.send(message);
                                  } catch (MessagingException e) {
                                      e.printStackTrace();
                                  }
                              }

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

   private class sendMail extends AsyncTask<Message,String,String>{



       @Override
       protected String doInBackground(Message... messages) {
           try {
               Transport.send(messages[0]);
                return "Success";
           } catch (MessagingException e) {
               e.printStackTrace();
               return "Error";
           }

       }
   }

}
