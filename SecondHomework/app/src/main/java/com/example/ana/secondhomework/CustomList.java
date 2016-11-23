package com.example.ana.secondhomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ana on 11/20/2016.
 */

public class CustomList extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> web;
    private final ArrayList<Integer> Imageid;
    private final ArrayList<String> date;

    public CustomList(Context c, ArrayList<String> web, ArrayList<Integer> Imageid, ArrayList<String> date) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.date=date;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View list;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            list = new View(mContext);
            list = inflater.inflate(R.layout.list, null);
            TextView textView = (TextView) list.findViewById(R.id.list_text);
            ImageView imageView = (ImageView)list.findViewById(R.id.list_image);
            TextView datetext = (TextView) list.findViewById(R.id.list_date);
            textView.setText(web.get(position));
            imageView.setImageResource(Imageid.get(position));
            datetext.setText(date.get(position));
        } else {
            list = (View) convertView;
        }

        return list;
    }
}
