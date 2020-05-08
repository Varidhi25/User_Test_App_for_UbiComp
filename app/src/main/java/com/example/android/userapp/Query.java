package com.example.android.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Query extends AppCompatActivity {

    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        b1=(Button) findViewById(R.id.displayButton);
       // b2=(Button) findViewById(R.id.updateButton);
        b3=(Button) findViewById(R.id.addButton);
        b4=(Button) findViewById(R.id.deleteButton);
        //open display page when display button is pressed
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent display=new Intent(Query.this, Display.class);
                startActivity(display);
                finish();
            }
        });
        //open update page when update button page is pressed
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent display=new Intent(Query.this, Update.class);
//                startActivity(display);
//                finish();
//            }
//        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent display=new Intent(Query.this, Add.class);
                startActivity(display);
                finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent display=new Intent(Query.this, Delete.class);
                startActivity(display);
                finish();
            }
        });
    }
}
