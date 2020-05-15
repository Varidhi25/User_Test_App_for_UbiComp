package com.example.android.userapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.userapp.database.ItemDbHelper;
import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.database.Temp1DbHelper;
import com.example.android.userapp.database.Temp2DbHelper;
import com.example.android.userapp.database.Temp3DbHelper;
import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class que extends AppCompatActivity {
    String rEmail,itemName;
    Button back,query;
    TextInputEditText t;
    SensorObjectDbHelper sdb;
    ItemDbHelper idb;
    Temp1DbHelper t1db;
    Temp2DbHelper t2db;
    Temp3DbHelper t3db;
    ArrayList<details> detailsArrayList;
    ArrayList<Item> itemArrayList;
    sampleTree s;
    static TreeNode<String> treeRoot = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que);
        s=new sampleTree(this);
        rEmail=getIntent().getExtras().getString("rEmail");
        back=(Button) findViewById(R.id.queBack);
        query=(Button) findViewById(R.id.queButton);
        t=(TextInputEditText) findViewById(R.id.queInput);
        detailsArrayList=new ArrayList<details>();
        itemArrayList=new ArrayList<>();
        sdb=new SensorObjectDbHelper(this);
        idb=new ItemDbHelper(this);
        t1db=new Temp1DbHelper(this);
        t2db=new Temp2DbHelper(this);
        t3db=new Temp3DbHelper(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(que.this,Query.class);
                i.putExtra("rEmail",rEmail);
                startActivity(i);
                finish();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t.getText().toString()==""){
                    Toast.makeText(getApplicationContext(),"Please enter the input name",Toast.LENGTH_SHORT);
                }
                else{
                    //construct the tree
                    detailsArrayList=sdb.getAllData(detailsArrayList);
                    treeRoot=s.getSet(detailsArrayList);
                   // System.out.println("TREE");
//                    for (TreeNode<String> node : treeRoot) {
//                        String indent = createIndent(node.getLevel());
//                        System.out.println(indent + node.data);
//                    }
                    itemName=t.getText().toString().toLowerCase();

                    //get the details of inputted item
                    itemArrayList=idb.getDetails(itemName);
                    s.query(itemArrayList);
                }
            }
        });
    }
    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

  public void get(String mainCategory){
     //   System.out.println(getApplicationContext());

  }
  public void basedCategory(String category){

      ArrayList<details> CatBased =t1db.CategoryBased(category);
      for(int i=0;i<CatBased.size();i++)
          t2db.insertObject(CatBased.get(i).getItemName(),CatBased.get(i).getMainCategory(),CatBased.get(i).getCategory(),CatBased.get(i).getBrand(),CatBased.get(i).getQuantity(),CatBased.get(i).getExpDate());
  }

  public void basedBrand(String brand){
      ArrayList<details> BrBased=t2db.BrandBased(brand);
      for(int l=0;l<BrBased.size();l++)
          t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
  }
  public void printData(){
     ArrayList<details> all=t3db.getData();
     for(int m=0;m<all.size();m++)
         System.out.println(all.get(m).getItemName() +"   "+all.get(m).getBrand() +"   "+all.get(m).getQuantity() +"     "+all.get(m).getExpDate());

  }

}
