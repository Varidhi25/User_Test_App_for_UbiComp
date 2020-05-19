package com.example.android.userapp;

import android.app.DatePickerDialog;
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

import org.w3c.dom.Text;

public class Delete extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView back,exp;
    private Button delete;
    String rEmail;
    int eDay,eMonth,eYear;
    TextInputEditText t1,t2;
    String category,brand;
    private Spinner s1,s2;
    String expDate;
    SensorObjectDbHelper sdb;
    CategoryDbHelper cdb;
    BrandDbHelper bdb;
    SubscribeDbHelper s;
    RegisterDbHelper rdb;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        rEmail=getIntent().getExtras().getString("rEmail");
        back=(TextView) findViewById(R.id.deleteBack);
        exp=(TextView)findViewById(R.id.deleteExpDate);
        t1=(TextInputEditText) findViewById(R.id.delete_itemName) ;
        t2=(TextInputEditText) findViewById(R.id.delete_itemQuantity) ;

        s1=(Spinner) findViewById(R.id.delete_itemCategory);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.Category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(this);
        s2=(Spinner) findViewById(R.id.delete_itemBrand);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,
                R.array.Brand,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter1);
        s2.setOnItemSelectedListener(this);
        sdb=new SensorObjectDbHelper(this);
        cdb=new CategoryDbHelper(this);
        bdb= new BrandDbHelper(this);
        rdb=new RegisterDbHelper(this);
        s=new SubscribeDbHelper(this);
        delete=(Button) findViewById(R.id.delete_button);
        //called when back button is pressed
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Delete.this,Query.class);
                i.putExtra("rEmail",rEmail);
                startActivity(i);
                finish();
            }
        });

        exp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Delete.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });
        mDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eYear=year; eMonth=month; eDay=dayOfMonth;
                if(eMonth+1>0 && eMonth+1<=9)
                    expDate=year+"-0"+(month+1)+"-"+dayOfMonth;
                else expDate=year+"-"+(month+1)+"-"+dayOfMonth;

            }
        };

        //when delete button is pressed
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //check if any field is empty
                if(t1.getText().toString()=="" || t2.getText().toString()=="" || exp.getText().toString()=="" )
                    Toast.makeText(getApplicationContext(),"One or more fields are empty",Toast.LENGTH_SHORT).show();
                else{
                    String itemName=t1.getText().toString().trim().toLowerCase();
                    category=s1.getSelectedItem().toString();
                    brand=s2.getSelectedItem().toString();
                    long C_ID=cdb.getCID(category);
                    long B_ID=bdb.getBID(brand);

                    long S_ID=sdb.itemExist(itemName,C_ID,B_ID,expDate);
                    //check if item exists or not
                    if(S_ID>0){
                        //get the available quantity of item
                        int available_quantity=sdb.getQuantity(S_ID);
                        int quantity=Integer.parseInt(t2.getText().toString());
                        //if required quantity is greater than available quantity
                        if(available_quantity<quantity){
                            Toast.makeText(getApplicationContext(),"Sorry, Available quantity of "+itemName+" is"+available_quantity,Toast.LENGTH_SHORT).show();
                        }
                        //if required quantity is less than available quantity
                        else if(available_quantity>quantity){
                            quantity=available_quantity-quantity;
                            String mainCategory="solid";
                             Add add=new Add();
                             if(add.solid.contains(category))
                                     mainCategory="solid";
                             else if(add.liquid.contains(category))
                                 mainCategory="liquid";
                             else if(add.liquid.contains(category))
                                 mainCategory="semiSolid";
                            sdb.updateQuantity(S_ID,itemName,mainCategory,C_ID,B_ID,quantity,expDate);
                          //  Toast.makeText(getApplicationContext(),"Deleted Successfully.",Toast.LENGTH_SHORT).show();
                            long R_ID=rdb.getRID(rEmail);
                            boolean sub=s.subscriber(R_ID,C_ID);
                           // Toast.makeText(getApplicationContext(),sub+" : ",Toast.LENGTH_SHORT).show();
                            if(sub==true){
                                String message=quantity+ " "+itemName+" "+category +" deleted. Available: "+quantity;
                                NotificationCompat.Builder builder=new NotificationCompat.Builder(Delete.this,"personal_notification");
                                builder.setSmallIcon(R.drawable.ic_message);
                                builder.setContentTitle("Item Deleted");
                                builder.setContentText(message);


                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Delete.this);
                                notificationManager.notify(1, builder.build());
                            }
                            else{
                                Toast.makeText(getApplicationContext()," Successful ",Toast.LENGTH_SHORT).show();
                            }
                        }
                        //if required quantity is equal to available
                        else if(available_quantity==quantity){
                            sdb.deleteItem(S_ID);
                            long R_ID=rdb.getRID(rEmail);
                            //Toast.makeText(getApplicationContext(),"Deleted Successfully.",Toast.LENGTH_SHORT).show();
                            boolean sub=s.subscriber(R_ID,C_ID);
                            Toast.makeText(getApplicationContext(),sub+" : ",Toast.LENGTH_SHORT).show();
                            if(sub==true){
                                String message=quantity+ " "+itemName+" "+category +" deleted. ";
                                NotificationCompat.Builder builder=new NotificationCompat.Builder(Delete.this,"personal_notification");
                                builder.setSmallIcon(R.drawable.ic_message);
                                builder.setContentTitle("Item Deleted");
                                builder.setContentText(message);


                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Delete.this);
                                notificationManager.notify(1, builder.build());
                            }
                            else{
                                Toast.makeText(getApplicationContext()," Successful ",Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                    else{
                        Toast.makeText(getApplicationContext(),itemName+" not found.",Toast.LENGTH_SHORT).show();
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
