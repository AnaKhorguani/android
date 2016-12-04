package com.example.ana.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater = null;
    //public Friend friend;
    public List<HashMap<Bitmap, String>> messagelist;

    public ChatListAdapter(Context c, List<HashMap<Bitmap, String>> messagelist ) {
        super();
        mContext = c;
        //this.friend = friend;
        this.messagelist=messagelist;
        this.inflater = LayoutInflater.from(c);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return messagelist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return messagelist.get(position);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.oldmessage);
            holder.imageView = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Friend rowItem = (Friend) getItem(position);

        Set<Map.Entry<Bitmap, String>> mapSet = messagelist.get(position).entrySet();
        Iterator<Map.Entry<Bitmap, String>> mapIterator = mapSet.iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<Bitmap, String> mapEntry = mapIterator.next();
            Bitmap btm = mapEntry.getKey();
            String message = mapEntry.getValue();

            holder.txtTitle.setText(message);
            holder.imageView.setImageBitmap(btm);
        }



        return convertView;
    }
}
