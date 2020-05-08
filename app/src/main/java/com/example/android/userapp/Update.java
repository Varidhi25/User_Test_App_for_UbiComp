package com.example.android.userapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.userapp.database.SensorObjectDbHelper;

public class Update extends AppCompatActivity {
    private String itemName,mainCategory,expDate;
    int quantity;
    SensorObjectDbHelper sdb;
    Long S_ID ,C_ID;
    Button b,back;
    TextInputEditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        sdb=new SensorObjectDbHelper(this);
        itemName=getIntent().getExtras().getString("itemName");
        mainCategory=getIntent().getExtras().getString("mainCategory");
        C_ID=getIntent().getExtras().getLong("C_ID");
        quantity=(getIntent().getExtras().getInt("quantity"));
        expDate=getIntent().getExtras().getString("expDate");
        S_ID=getIntent().getExtras().getLong("S_ID");
        t=(TextInputEditText) findViewById(R.id.Quantity) ;
        b=(Button) findViewById(R.id.updateButton);
        back=(Button) findViewById(R.id.backButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t.getText().toString()=="")
                    Toast.makeText(getApplicationContext(),"Please enter the quantity of "+itemName,Toast.LENGTH_SHORT).show();
                else{
                    quantity+=Integer.parseInt(t.getText().toString());
                    sdb.updateQuantity(S_ID,itemName,mainCategory,C_ID,quantity,expDate);
                    Toast.makeText(getApplicationContext(),"Quantity updated successfully for "+itemName,Toast.LENGTH_SHORT).show();
                }
            }
        });
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(Update.this,Query.class);
               startActivity(i);
               finish();
           }
       });
    }
}
