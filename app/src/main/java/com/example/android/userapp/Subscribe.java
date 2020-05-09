package com.example.android.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.android.userapp.database.CategoryDbHelper;
import com.example.android.userapp.database.RegisterDbHelper;
import com.example.android.userapp.database.SubscribeDbHelper;

import java.util.ArrayList;

public class Subscribe extends AppCompatActivity {

    //ArrayList to store the checked items id
    ArrayList<Long> selectedC= new ArrayList<>();
    //DbHelper class references
    RegisterDbHelper rdb;
    CategoryDbHelper db1;
    SubscribeDbHelper db;
    //reference of all the fields
    Button b;
    String rEmail;
    private CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        //get the registered email
        rEmail=getIntent().getExtras().getString("rEmail");
        b=(Button) findViewById(R.id.subButton);
        db=new SubscribeDbHelper(this);
        db1=new CategoryDbHelper(this);
        rdb=new RegisterDbHelper(this);

        //when the SUBSCRIBE button is pressed
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id of registered email
               long R_ID = rdb.getRID(rEmail);

                c1=(CheckBox) findViewById(R.id.subC1);  c2=(CheckBox) findViewById(R.id.subC2);
                c3=(CheckBox) findViewById(R.id.subC3);  c4=(CheckBox) findViewById(R.id.subC4);
                c5=(CheckBox) findViewById(R.id.subC5);  c6=(CheckBox) findViewById(R.id.subC6);
                c7=(CheckBox) findViewById(R.id.subC7);  c8=(CheckBox) findViewById(R.id.subC8);
                c9=(CheckBox) findViewById(R.id.subC9);  c10=(CheckBox) findViewById(R.id.subC10);
                c11=(CheckBox) findViewById(R.id.subC11);  c12=(CheckBox) findViewById(R.id.subC12);
                c13=(CheckBox) findViewById(R.id.subC13);  c14=(CheckBox) findViewById(R.id.subC14);
                c15=(CheckBox) findViewById(R.id.subC15);  c16=(CheckBox) findViewById(R.id.subC16);
                long C_ID;

                //save the id of category in the ArrayList if checkbox is clicked
                if(c1.isChecked()) {
                    C_ID=db1.getCID("vegetable");
                    selectedC.add(C_ID);
                }
                if(c2.isChecked()) {
                    C_ID=db1.getCID("fruit");
                    selectedC.add(C_ID);
                }
                if(c3.isChecked()) {
                    C_ID=db1.getCID("cake");
                    selectedC.add(C_ID);
                }
                if(c4.isChecked()) {
                    C_ID=db1.getCID("chocolate");
                    selectedC.add(C_ID);
                }
                if(c5.isChecked()) {
                    C_ID=db1.getCID("bread");
                    selectedC.add(C_ID);
                }
                if(c6.isChecked()) {
                    C_ID=db1.getCID("softDrink");
                    selectedC.add(C_ID);
                }
                if(c7.isChecked()) {
                    C_ID=db1.getCID("coldDrink");
                    selectedC.add(C_ID);
                }
                if(c8.isChecked()) {
                    C_ID=db1.getCID("juice");
                    selectedC.add(C_ID);
                }
                if(c9.isChecked()) {
                    C_ID=db1.getCID("milkshake");
                    selectedC.add(C_ID);
                }
                if(c10.isChecked()) {
                    C_ID=db1.getCID("water");
                    selectedC.add(C_ID);
                }
                if(c11.isChecked()) {
                    C_ID=db1.getCID("milk");
                    selectedC.add(C_ID);
                }
                if(c12.isChecked()) {
                    C_ID=db1.getCID("pudding");
                    selectedC.add(C_ID);
                }
                if(c13.isChecked()) {
                    C_ID=db1.getCID("iceCream");
                    selectedC.add(C_ID);
                }
                if(c14.isChecked()) {
                    C_ID=db1.getCID("sauce");
                    selectedC.add(C_ID);
                }
                if(c15.isChecked()) {
                    C_ID=db1.getCID("jam");
                    selectedC.add(C_ID);
                }
                if(c16.isChecked()) {
                    C_ID=db1.getCID("butter");
                    selectedC.add(C_ID);
                }

                //if none of the checkboxes are clicked, then make the user to click one or more checkbox
                 if (selectedC.size() == 0)
                    Toast.makeText(getApplicationContext(), "Please select one or more items.", Toast.LENGTH_SHORT).show();

                 //if one or more checkbox are checked, save the register id and category id into the database by calling insertItem() method
                else {
                    for (int i = 0; i < selectedC.size(); i++)
                        db.insertItem(R_ID, selectedC.get(i));

                    //open query page
                    Intent query = new Intent(Subscribe.this, Query.class);
                     query.putExtra("rEmail",rEmail);
                    startActivity(query);
                    finish();
                }
            }
        });
    }
}
