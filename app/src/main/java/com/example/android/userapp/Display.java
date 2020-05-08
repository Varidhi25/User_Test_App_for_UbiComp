package com.example.android.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.model.details;

import java.util.ArrayList;
import com.example.android.userapp.Adapter.myAdapter;

public class Display extends AppCompatActivity {

    SensorObjectDbHelper sdb;
    ArrayList<details> detailsArrayList;
    myAdapter myAdapter;
    ListView l1;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        sdb=new SensorObjectDbHelper(this);
        l1=(ListView) findViewById(R.id.displayListView);
        detailsArrayList=new ArrayList<>();
        back=(TextView) findViewById(R.id.backButton);
        loadDataInListView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Display.this,Query.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loadDataInListView() {
        detailsArrayList=sdb.getAllData();
     myAdapter = new myAdapter(this,detailsArrayList);
     l1.setAdapter(myAdapter);
     myAdapter.notifyDataSetChanged();
    }

}
