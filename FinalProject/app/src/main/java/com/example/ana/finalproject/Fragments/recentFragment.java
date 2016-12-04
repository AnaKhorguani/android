package com.example.ana.finalproject.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ana.finalproject.ChatActivity;
import com.example.ana.finalproject.ChatListAdapter;
import com.example.ana.finalproject.DBChatHandler;
import com.example.ana.finalproject.DBHandler;
import com.example.ana.finalproject.Friend;
import com.example.ana.finalproject.R;

import java.util.HashMap;
import java.util.List;


public class recentFragment extends Fragment {

    ListView recentlv;
    public static ChatListAdapter recentadapter;
    List<HashMap<Bitmap, String>> messagelist;
    DBChatHandler cdb;
    DBHandler db;

    private Context context;
    public recentFragment(Context c)
    {
        context=c;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recent, container, false);

        //System.out.println("hi");
        recentlv=(ListView) rootView.findViewById(R.id.recentchatlist);
        cdb=new DBChatHandler(context);
        db=new DBHandler(context);


        List<Friend> friendslist = db.getAllFriends();

        messagelist=cdb.getRecentMessages(10, context, friendslist);

        recentadapter=new ChatListAdapter(context,  messagelist);
        recentlv.setAdapter(recentadapter);
        return rootView;
    }

    public void onResume()
    {
        super.onResume();
        List<Friend> friendslist = db.getAllFriends();
        messagelist=cdb.getRecentMessages(10, context, friendslist);
        recentadapter=new ChatListAdapter(context,  messagelist);
        recentlv.setAdapter(recentadapter);
    }
}

