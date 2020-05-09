package com.example.android.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Query extends AppCompatActivity {

    Button b1,b2,b3,b4;
    public String rEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        b1=(Button) findViewById(R.id.displayButton);
       // b2=(Button) findViewById(R.id.updateButton);
        b3=(Button) findViewById(R.id.addButton);
        b4=(Button) findViewById(R.id.deleteButton);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
         rEmail=bundle.getString("rEmail");
        //Toast.makeText(getApplicationContext(),rEmail+" : ",Toast.LENGTH_SHORT).show();
        //open display page when display button is pressed
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent display=new Intent(Query.this, Display.class);
                display.putExtra("rEmail",rEmail);
                startActivity(display);
                finish();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(Query.this, Add.class);
                add.putExtra("rEmail",rEmail);
                startActivity(add);
                finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete=new Intent(Query.this, Delete.class);
                delete.putExtra("rEmail",rEmail);
                startActivity(delete);
                finish();
            }
        });
    }
}
