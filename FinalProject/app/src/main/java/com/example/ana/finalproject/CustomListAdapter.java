package com.example.ana.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends  BaseAdapter {//implements Filterable {

    private Context mContext;
    private LayoutInflater inflater=null;
    public ArrayList<Friend> friends;
    public ArrayList<Friend> searchedlist;
    public CustomListAdapter(Context c, ArrayList<Friend> friends ) {
        super();
        mContext = c;
        this.friends=friends;
        this.searchedlist =friends;
       this.inflater= LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return searchedlist.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return searchedlist.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Friend rowItem = (Friend) getItem(position);

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            if(rowItem.color.equals("#CDDC39")) convertView.setBackgroundColor(Color.parseColor(rowItem.color));
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.displayName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(rowItem.getName());
        holder.imageView.setImageBitmap(rowItem.getAvatar());

        return convertView;
    }


    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Friend> FilteredList= new ArrayList<Friend>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = friends;
                    results.count = friends.size();
                }
                else {
                    for (int i = 0; i < friends.size(); i++) {
                        Friend data = friends.get(i);
                        if (data.getName().toUpperCase().contains(constraint.toString().toUpperCase()))  {
                            FilteredList.add(data);

                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();

                }
                return results;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchedlist =(ArrayList<Friend>)results.values;
                notifyDataSetChanged();
            }

        };
        return filter;
    }

}

