package com.example.android.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.userapp.Adapter.myAdapter;
import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class QueryDisplay extends AppCompatActivity {
    TextView back;
    String rEmail;
    ListView l1;
    myAdapter myAdapter;
    ArrayList<details> detailsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_display);
        Bundle bundle=getIntent().getExtras();
        detailsArrayList=(ArrayList<details>)bundle.getSerializable("details");
        rEmail=getIntent().getExtras().getString("rEmail");
        back=(TextView) findViewById(R.id.queryBackButton);
        l1=(ListView) findViewById(R.id.queryDisplayListView);
        if(detailsArrayList.size()>0)
         loadDataInListView();
        else{
            TextView head=(TextView) findViewById(R.id.queryDisplayHeading);
            head.setText("No such items found");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(QueryDisplay.this,Query.class);
                i.putExtra("rEmail",rEmail);
                startActivity(i);
                finish();
            }
        });


    }
    private void loadDataInListView() {
        myAdapter = new myAdapter(this,detailsArrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}
