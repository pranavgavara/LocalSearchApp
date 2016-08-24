package com.example.pranav.parcelableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pranav on 7/27/2016.
 */
public class ResultsAdapter extends ArrayAdapter {

    ArrayList<Results> results;
    Context context;
    public ResultsAdapter(Context context, ArrayList<Results> results) {
        super(context, R.layout.single_row,results);
        this.context=context;
        this.results=results;
    }
    class resultsViewHolder{
        TextView myTitle;
        TextView myAddress;
        TextView myPhone;
        TextView myDistance;

        resultsViewHolder(View view) {
            myTitle = (TextView) view.findViewById(R.id.titlefield);
            myAddress = (TextView) view.findViewById(R.id.addressfield);
            myPhone = (TextView) view.findViewById(R.id.phonefield);
            myDistance = (TextView) view.findViewById(R.id.distancefield);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        resultsViewHolder holder=null;
        if(row==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.single_row,parent,false);
            holder=new resultsViewHolder(row);
            row.setTag(holder);
        }else{
            holder= (resultsViewHolder) row.getTag();
        }
        Results result=results.get(position);
        holder.myTitle.setText(result.title);
        holder.myAddress.setText(result.address+" , "+result.city+" , "+result.state);
        holder.myPhone.setText("Phone:"+result.phone);
        holder.myDistance.setText("Distance: "+result.distance+" miles");

        return row;
    }
}
