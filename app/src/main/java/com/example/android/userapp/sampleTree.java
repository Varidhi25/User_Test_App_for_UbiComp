package com.example.android.userapp;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.userapp.Adapter.myAdapter;
import com.example.android.userapp.database.BrandContract;
import com.example.android.userapp.database.BrandDbHelper;
import com.example.android.userapp.database.CategoryDbHelper;
import com.example.android.userapp.database.SensorObjectContract;
import com.example.android.userapp.database.SensorObjectDbHelper;
import com.example.android.userapp.database.Temp1DbHelper;
import com.example.android.userapp.database.Temp2DbHelper;
import com.example.android.userapp.database.Temp3DbHelper;
import com.example.android.userapp.model.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class TreeNodeIter<T> implements Iterator<TreeNode<T>>
{
    enum ProcessStages
    {
        ProcessParent, ProcessChildCurNode, ProcessChildSubNode
    }
    private TreeNode<T> treeNode;
    public TreeNodeIter(TreeNode<T> treeNode) {
        this.treeNode = treeNode;
        this.doNext = ProcessStages.ProcessParent;
        this.childrenCurNodeIter = treeNode.children.iterator();
    }
    private ProcessStages doNext;
    private TreeNode<T> next;
    private Iterator<TreeNode<T>> childrenCurNodeIter;
    private Iterator<TreeNode<T>> childrenSubNodeIter;
    @Override
    public boolean hasNext() {
        if (this.doNext == ProcessStages.ProcessParent) {
            this.next = this.treeNode;
            this.doNext = ProcessStages.ProcessChildCurNode;
            return true;
        }
        if (this.doNext == ProcessStages.ProcessChildCurNode) {
            if (childrenCurNodeIter.hasNext()) {
                TreeNode<T> childDirect = childrenCurNodeIter.next();
                childrenSubNodeIter = childDirect.iterator();
                this.doNext = ProcessStages.ProcessChildSubNode;
                return hasNext();
            }
            else {
                this.doNext = null;
                return false;
            }
        }
        if (this.doNext == ProcessStages.ProcessChildSubNode) {
            if (childrenSubNodeIter.hasNext()) {
                this.next = childrenSubNodeIter.next();
                return true;
            }
            else {
                this.next = null;
                this.doNext = ProcessStages.ProcessChildCurNode;
                return hasNext();
            }
        }
        return false;
    }
    @Override
    public TreeNode<T> next() {
        return this.next;
    }
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
class TreeNode<T> implements Iterable<TreeNode<T>> {
    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;
    public boolean isRoot() {
        return parent == null;
    }
    public boolean isLeaf() {
        return children.size() == 0;
    }
    private List<TreeNode<T>> elementsIndex;
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }
    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }
    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }
    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }
        return null;
    }
    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }
    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
        return iter;
    }
}
public class sampleTree extends Application {
    public static TreeNode<String> ItemList;
    public static TreeNode<String> BrandList;
   // static List<String> mainCategoryList = new ArrayList<String>();
    static Map<String, String> mainCategoryList=new HashMap<>();
  //  static List<String> solidList = new ArrayList<String>();
    static Map<String, String> solidList=new HashMap<>();
 //   static List<String> semiSolidList = new ArrayList<String>();
    static Map<String, String> semiSolidList=new HashMap<>();
   // public static List<String> liquidList = new ArrayList<String>();
    static Map<String, String> liquidList=new HashMap<>();
  //  static List<String> vegetableBrandList = new ArrayList<String>();
    static Map<String, String> vegetableBrandList=new HashMap<>();
   // static List<String> fruitBrandList = new ArrayList<String>();
    static Map<String, String> fruitBrandList=new HashMap<>();
  //  static List<String> cakeBrandList = new ArrayList<String>();
    static Map<String, String> cakeBrandList=new HashMap<>();
  //  static List<String> chocolateBrandList = new ArrayList<String>();
    static Map<String, String> chocolateBrandList=new HashMap<>();
  //  static List<String> breadBrandList = new ArrayList<String>();
    static Map<String, String> breadBrandList=new HashMap<>();
 //   static List<String> softDrinkBrandList = new ArrayList<String>();
    static Map<String, String> softDrinkBrandList=new HashMap<>();
 //   static List<String> coldDrinkBrandList = new ArrayList<String>();
    static Map<String, String> coldDrinkBrandList=new HashMap<>();
//    static List<String> juiceBrandList = new ArrayList<String>();
    static Map<String, String> juiceBrandList=new HashMap<>();
 //   static List<String> milkshakeBrandList = new ArrayList<String>();
    static Map<String, String> milkshakeBrandList=new HashMap<>();
  //  static List<String> milkBrandList = new ArrayList<String>();
    static Map<String, String> milkBrandList=new HashMap<>();
  //  static List<String> waterBrandList = new ArrayList<String>();
    static Map<String, String> waterBrandList=new HashMap<>();
  //  static List<String> puddingBrandList = new ArrayList<String>();
    static Map<String, String> puddingBrandList=new HashMap<>();
  //  static List<String> iceCreamBrandList = new ArrayList<String>();
    static Map<String, String> iceCreamBrandList=new HashMap<>();
 //   static List<String> butterBrandList = new ArrayList<String>();
    static Map<String, String> butterBrandList=new HashMap<>();
 //   static List<String> sauceBrandList = new ArrayList<String>();
    static Map<String, String> sauceBrandList=new HashMap<>();
 //   static List<String> jamBrandList = new ArrayList<String>();
    static Map<String, String> jamBrandList=new HashMap<>();
    static List<String> n3 = new ArrayList<String>();
    public static List<TreeNode<String>> node = new ArrayList<TreeNode<String>>();
    static String Container = "fridge";
    static TreeNode<String> root = new TreeNode<String>(Container);
    public Activity activity;
    Temp1DbHelper t1db;
    BrandDbHelper bdb;
    CategoryDbHelper cdb;
    Temp2DbHelper t2db;
    Temp3DbHelper t3db;
    myAdapter myAdapter;
    public SensorObjectDbHelper sdb;
    public sampleTree (Activity act)
    {
        this.activity = act;
        sdb=new SensorObjectDbHelper(activity.getApplicationContext());
        t1db=new Temp1DbHelper(activity.getApplicationContext());
        cdb=new CategoryDbHelper(activity.getApplicationContext());
        bdb=new BrandDbHelper(activity.getApplicationContext());
        t2db=new Temp2DbHelper(activity.getApplicationContext());
        t3db=new Temp3DbHelper(activity.getApplicationContext());
        // Now here you can get getApplication()
    }
    public static TreeNode<String> getSet(ArrayList<details> d) {
        long start = System.nanoTime();
      for(int i=0;i<d.size();i++){
          String mainCategory;
          Iterator<TreeNode<String>> itr = node.iterator();
          Iterator<TreeNode<String>> itr1 = node.iterator();
          Iterator<TreeNode<String>> itr2= node.iterator();

          //adding main category
          TreeNode<String> n=null;
          mainCategory =d.get(i).getMainCategory();
          if(!mainCategoryList.containsKey(mainCategory)){
              mainCategoryList.put(mainCategory,mainCategory);
              n= root.addChild(mainCategory);
              node.add(n);
          }

          TreeNode<String> c=null;
          itr = node.iterator();
          while(itr.hasNext()) {
              c = itr.next();
              if(mainCategory.equals(c.data))
              {
                  break;
              }
          }
          String category=d.get(i).getCategory();
          ItemList=null;


          //adding category like solid,liquid or semi-solid
          if(mainCategory.equals("liquid")){
              if(!liquidList.containsKey(category)){
                  liquidList.put(category,category);
                  //System.out.println(liquidList);
                  ItemList = c.addChild(category);
                  node.add(ItemList);

              }
          }
          else if(mainCategory.equals("solid")){
              if(!solidList.containsKey(category)){
                  solidList.put(category,category);
                  ItemList = c.addChild(category);
                  node.add(ItemList);
              }
          }
          else{
              if(!semiSolidList.containsKey(category)){
                  semiSolidList.put(category,category);
                  ItemList = c.addChild(category);
                  node.add(ItemList);
              }
          }

          TreeNode<String> c2=null;
          itr1 = node.iterator();
          while(itr1.hasNext())
          {
              c2 = itr1.next();
              if(category.equals(c2.data))
              {
                  break;
              }
          }
          String brand=d.get(i).getBrand();
          BrandList=null;

          //adding brand
          if(category.equals("vegetable")){
              if(!vegetableBrandList.containsKey(brand)){
                  vegetableBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("fruit")){
              if(!fruitBrandList.containsKey(brand)){
                  fruitBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("chocolate")){
              if(!chocolateBrandList.containsKey(brand)){
                  chocolateBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("cake")){
              if(!cakeBrandList.containsKey(brand)){
                  cakeBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("bread")){
              if(!breadBrandList.containsKey(brand)){
                  breadBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("softDrink")){
              if(!softDrinkBrandList.containsKey(brand)){
                  softDrinkBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("coldDrink")){
              if(!coldDrinkBrandList.containsKey(brand)){
                  coldDrinkBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("juice")){
              if(!juiceBrandList.containsKey(brand)){
                  juiceBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("milkshake")){
              if(!milkshakeBrandList.containsKey(brand)){
                  milkshakeBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("milk")){
              if(!milkBrandList.containsKey(brand)){
                  milkBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("water")){
              if(!waterBrandList.containsKey(brand)){
                  waterBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("pudding")){
              if(!puddingBrandList.containsKey(brand)){
                  puddingBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("iceCream")){
              if(!iceCreamBrandList.containsKey(brand)){
                  iceCreamBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("sauce")){
              if(!sauceBrandList.containsKey(brand)){
                  sauceBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("jam")){
              if(!jamBrandList.containsKey(brand)){
                  jamBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
          else if(category.equals("butter")){
              if(!butterBrandList.containsKey(brand)){
                  butterBrandList.put(brand,brand);
                  BrandList = c2.addChild(brand);
                  node.add(BrandList);
              }
          }
      }//end of loop
        long time = System.nanoTime() - start;
        System.out.println("tree built time  "+time);
        return root;
    }

    public void query(ArrayList<Item> item,String rEmail){
        long start = System.currentTimeMillis();
        ArrayList<details> finall=new ArrayList<>();

        que q=new que();
        //check if category exists in fridge
        for(int i=0;i<item.size();i++){
            t1db.deleteall();
            t2db.deleteall();
            t3db.deleteall();
            if(mainCategoryList.containsKey(item.get(i).getMainCategory())){
               // insertion into temp1 database
             //   ArrayList<details> MainCatBased =new ArrayList<>();
               // System.out.println(solidList);
              //  MainCatBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory());

                //insert in temp1 database
//                for(int j=0;j<MainCatBased.size();j++)
//                    t1db.insertObject(MainCatBased.get(j).getItemName(),MainCatBased.get(j).getMainCategory(),MainCatBased.get(j).getCategory(),MainCatBased.get(j).getBrand(),MainCatBased.get(j).getQuantity(),MainCatBased.get(j).getExpDate());

                if(liquidList.containsKey(item.get(i).getCategory())){
//                    ArrayList<details> CatBased =t1db.CategoryBased(item.get(i).getCategory());
//
//                    for(int k=0;k<CatBased.size();k++)
//                        t2db.insertObject(CatBased.get(k).getItemName(),CatBased.get(k).getMainCategory(),CatBased.get(k).getCategory(),CatBased.get(k).getBrand(),CatBased.get(k).getQuantity(),CatBased.get(k).getExpDate());


                    if(softDrinkBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);

                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(coldDrinkBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(milkshakeBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(milkBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(juiceBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(waterBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }

                }// end of liquid list

                 else if(solidList.containsKey(item.get(i).getCategory())){
//                     ArrayList<details> CatBased =t1db.CategoryBased(item.get(i).getCategory());
//
//                    for(int k=0;k<CatBased.size();k++)
//                        t2db.insertObject(CatBased.get(k).getItemName(),CatBased.get(k).getMainCategory(),CatBased.get(k).getCategory(),CatBased.get(k).getBrand(),CatBased.get(k).getQuantity(),CatBased.get(k).getExpDate());


                    if(vegetableBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(fruitBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(chocolateBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(cakeBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(breadBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                }// end of solid list

                if(semiSolidList.containsKey(item.get(i).getCategory())){
//                    ArrayList<details> CatBased =t1db.CategoryBased(item.get(i).getCategory());
//
//                    for(int k=0;k<CatBased.size();k++)
//                        t2db.insertObject(CatBased.get(k).getItemName(),CatBased.get(k).getMainCategory(),CatBased.get(k).getCategory(),CatBased.get(k).getBrand(),CatBased.get(k).getQuantity(),CatBased.get(k).getExpDate());


                    if(puddingBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(iceCreamBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());

                    }
                    else if(jamBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(butterBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());
                    }
                    else if(sauceBrandList.containsKey(item.get(i).getBrand())){
                        long B_ID=bdb.getBID(item.get(i).getBrand());
                        long C_ID=cdb.getCID(item.get(i).getCategory());
                        ArrayList<details> BrBased=sdb.getItemsBasedOnMcategory(item.get(i).getMainCategory(),C_ID,B_ID);
                        for(int l=0;l<BrBased.size();l++)
                            t3db.insertObject(BrBased.get(l).getItemName(),BrBased.get(l).getMainCategory(),BrBased.get(l).getCategory(),BrBased.get(l).getBrand(),BrBased.get(l).getQuantity(),BrBased.get(l).getExpDate());

                    }


                }// end of semi-solid list
            }//end of mainCategory

            //printing of data
            ArrayList<details> all=t3db.getData();
            for(int f=0;f<all.size();f++)
                finall.add(all.get(f));
//

            }//end of for loop
        System.out.println("hi");
        long time = System.currentTimeMillis() - start;
        System.out.println("query time   "+time);
        Intent dis=new Intent(activity.getApplicationContext(),QueryDisplay.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("details",finall);
        dis.putExtras(bundle);
        //dis.putExtra("details",finall);
        System.out.println(rEmail);
        dis.putExtra("rEmail",rEmail);
        activity.getApplicationContext().startActivity(dis);
        }
}

