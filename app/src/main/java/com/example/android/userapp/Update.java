package com.example.android.userapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.userapp.database.CategoryDbHelper;
import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.database.SubscribeDbHelper;

public class Update extends AppCompatActivity {
    private String itemName,mainCategory,expDate;
    int quantity;
    SensorObjectDbHelper sdb;
    CategoryDbHelper cdb;
    SubscribeDbHelper s;
    Long S_ID ,C_ID,B_ID,R_ID;
    Button b,back;
    TextInputEditText t;
    String rEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        sdb=new SensorObjectDbHelper(this);
        s=new SubscribeDbHelper(this);
        cdb=new CategoryDbHelper(this);
        itemName=getIntent().getExtras().getString("itemName");
        mainCategory=getIntent().getExtras().getString("mainCategory");
        C_ID=getIntent().getExtras().getLong("C_ID");
        quantity=(getIntent().getExtras().getInt("quantity"));
        expDate=getIntent().getExtras().getString("expDate");
        rEmail=getIntent().getExtras().getString("rEmail");
        S_ID=getIntent().getExtras().getLong("S_ID");
        B_ID=getIntent().getExtras().getLong("B_ID");
        R_ID=getIntent().getExtras().getLong("R_ID");
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
                    sdb.updateQuantity(S_ID,itemName,mainCategory,C_ID,B_ID,quantity,expDate);
                  //  Toast.makeText(getApplicationContext(),"Quantity"+quantity+" updated successfully for "+itemName,Toast.LENGTH_SHORT).show();
                    boolean sub=s.subscriber(R_ID,C_ID);

                    if(sub==true){
                        String message=quantity+ " "+itemName+" updated. ";
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(Update.this,"personal_notification");
                        builder.setSmallIcon(R.drawable.ic_message);
                        builder.setContentTitle("Item Updated");
                        builder.setContentText(message);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Update.this);
                        notificationManager.notify(2, builder.build());
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Update Successful ",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(Update.this,Query.class);
               i.putExtra("rEmail",rEmail);
               startActivity(i);
               finish();
           }
       });
    }
}
