package com.example.android.userapp;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.userapp.database.BrandDbHelper;
import com.example.android.userapp.database.CategoryDbHelper;
import com.example.android.userapp.database.RegisterDbHelper;
import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.database.SubscribeDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView t,t3;
    private Button b;
    String rEmail;
    private TextInputEditText t1,t2;
    private Spinner s1,s2;
    private String category,brand;
    int eDay,eMonth,eYear;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    public ArrayList<String> solid=new ArrayList<>();
    public ArrayList<String> liquid=new ArrayList<>();
    public ArrayList<String> semiSolid=new ArrayList<>();
    CategoryDbHelper cdb;
    SensorObjectDbHelper sdb;
    BrandDbHelper bdb;
    RegisterDbHelper rdb;
    SubscribeDbHelper s;
    long R_ID;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        cdb=new CategoryDbHelper(this);
        sdb= new SensorObjectDbHelper(this);
        bdb=new BrandDbHelper(this);
        rdb=new RegisterDbHelper(this);
        s= new SubscribeDbHelper(this);
        t=(TextView) findViewById(R.id.back);
        b=(Button) findViewById(R.id.addButton);
        t1=(TextInputEditText) findViewById(R.id.itemName);
        rEmail=getIntent().getExtras().getString("rEmail");
        R_ID=rdb.getRID(rEmail);
       // Toast.makeText(getApplicationContext(),rEmail+" : ",Toast.LENGTH_SHORT).show();
        s1=(Spinner) findViewById(R.id.itemCategory);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.Category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(this);
        s2=(Spinner) findViewById(R.id.itemBrand);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,
                R.array.Brand,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter1);
        s2.setOnItemSelectedListener(this);
        t2=(TextInputEditText) findViewById(R.id.itemQuantity);
        t3=(TextView) findViewById(R.id.expDate);
        //add items to solid list
        solid.add("vegetable");
        solid.add("fruit");
        solid.add("cake");
        solid.add("chocolate");
        solid.add("bread");

        //add items to liquid list
        liquid.add("softDrink");
        liquid.add("coldDrink");
        liquid.add("juice");
        liquid.add("milkshake");
        liquid.add("water");
        liquid.add("milk");

        //add items to semiSolid list
        semiSolid.add("pudding");
        semiSolid.add("iceCream");
        semiSolid.add("sauce");
        semiSolid.add("jam");
        semiSolid.add("butter");

        //when back is pressed, open query page
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Add.this,Query.class);
                i.putExtra("rEmail",rEmail);
                startActivity(i);
                finish();
            }
        });

        // when expiry date is clicked
        t3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Add.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });
        mDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              eYear=year; eMonth=month; eDay=dayOfMonth;
            }
        };
        //when add button is pressed
        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(t1.getText().toString()=="" || t2.getText().toString()=="" || t3.getText().toString()=="" )
                    Toast.makeText(getApplicationContext(),"One or more fields are empty",Toast.LENGTH_SHORT).show();
                else{
                    category=s1.getSelectedItem().toString();
                    String itemName=t1.getText().toString().trim().toLowerCase();
                    String mainCategory="semiSolid";

                    if(solid.contains(category)) {
                        mainCategory = "solid";
                    }
                    else if(liquid.contains(category)) {
                        mainCategory = "liquid";
                      }
                    else if(semiSolid.contains(category)) {
                        mainCategory = "semiSold";
                    }

                    long C_ID=cdb.getCID(category);
                    brand=s2.getSelectedItem().toString();
                    long B_ID=bdb.getBID(brand);
                    String expDate;
                    //check if item already exists with same expiry date
                    if(eMonth+1>0 && eMonth+1<=9)
                         expDate=eYear+"-0"+eMonth+1+"-"+eDay;
                    else
                       expDate= eYear+"-"+eMonth+1+"-"+eDay;
                    long exists=sdb.itemExist(itemName,C_ID,B_ID,expDate);
                    //Toast.makeText(getApplicationContext(),rEmail+"",Toast.LENGTH_SHORT).show();

                    //if item doesn't exist before with same expiry date
                    if(exists==-1)
                    {
                        quantity=Integer.parseInt(t2.getText().toString().trim());

                          sdb.insertObject(itemName,mainCategory,C_ID,B_ID,quantity,eYear,eMonth,eDay);

                        boolean sub=s.subscriber(R_ID,C_ID);
                        //Toast.makeText(getApplicationContext(),sub+" : ",Toast.LENGTH_SHORT).show();
                        if(sub==true){
                            String message=quantity+ " "+itemName+" "+category +" added\nClick here to display. ";
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(Add.this,"personal_notification");
                                    builder.setSmallIcon(R.drawable.ic_message);
                                    builder.setContentTitle("New Item Added");
                                    builder.setContentText(message);


                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Add.this);
                            notificationManager.notify(1, builder.build());
                        }
                        else{
                            Toast.makeText(getApplicationContext()," Successful ",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),itemName+" already exists, please update the quantity.",Toast.LENGTH_SHORT).show();;
                        Intent i=new Intent(Add.this,Update.class);
                        i.putExtra("itemName",itemName);
                        i.putExtra("mainCategory",mainCategory);
                        i.putExtra("C_ID",C_ID);
                        i.putExtra("B_ID",B_ID);
                        int be_quantity= sdb.getQuantity(exists);
                        i.putExtra("quantity",be_quantity);
                        i.putExtra("expDate",expDate);
                        i.putExtra("S_ID",exists);
                        i.putExtra("R_ID",R_ID);
                        i.putExtra("rEmail",rEmail);
                        startActivity(i);
                        finish();
                    }


                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category=parent.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
