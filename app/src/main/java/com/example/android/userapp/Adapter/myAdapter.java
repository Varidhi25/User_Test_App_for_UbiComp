package com.example.android.userapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.userapp.R;
import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class myAdapter extends BaseAdapter {
    Context context;
    ArrayList<details> detailsArrayList;

    public myAdapter(Context c,ArrayList<details> array){
        context=c;
        detailsArrayList=array;
    }

    @Override
    public int getCount() {
        return detailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_view_layout,null);
            TextView t_itemName=(TextView) convertView.findViewById(R.id.a_itemName);
            TextView t_category=(TextView) convertView.findViewById(R.id.a_category);
            TextView t_quantity=(TextView) convertView.findViewById(R.id.a_quantity);
            TextView t_expDate=(TextView) convertView.findViewById(R.id.a_expDate);

            details details=detailsArrayList.get(position);
            t_itemName.setText(details.getItemName());
            t_category.setText(details.getCategory());
            t_quantity.setText(toString().valueOf(details.getQuantity()));
            t_expDate.setText(details.getExpDate());


        return convertView;
    }
}
