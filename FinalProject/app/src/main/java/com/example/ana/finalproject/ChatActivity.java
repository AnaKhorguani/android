package com.example.ana.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ana.finalproject.Fragments.contactsFragment;
import com.example.ana.finalproject.Fragments.recentFragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.max;


public class ChatActivity extends AppCompatActivity {

    ListView chatlv;
    ChatListAdapter adapter;
    List<HashMap<Bitmap, String>> messagelist;
    Friend friend;
    String currentDateTimeString;
    DBChatHandler cdb;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);


        int friendID=0;
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            friendID = extras.getInt("friendID");
        }
        int id=friendID-1;
        friend=contactsFragment.friends.get(id);

        TextView name=(TextView) findViewById(R.id.chatname);
        ImageView icon=(ImageView) findViewById(R.id.chatavatar);
        Button back= (Button) findViewById(R.id.back);
        name.setText(friend.getName());
        icon.setImageBitmap(friend.getAvatar());
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                friend.color="#ffffff";
                contactsFragment.changecolor();
                finish();
            }
        });

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        cdb=new DBChatHandler(this);

        messagelist= cdb.getAllMessages(friend.getId());
        for (int i=0; i<messagelist.size(); i++)
                {
                   //cdb.deleteMessage(friend);
                    System.out.println(messagelist.get(i));
                }

        chatlv=(ListView) findViewById(R.id.chatlist);
        adapter=new ChatListAdapter(ChatActivity.this,  messagelist);
        chatlv.setAdapter(adapter);

        final EditText message=(EditText) findViewById(R.id.message);
        Button send=(Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final String newmessage=message.getText().toString();
                sendMessage(newmessage);
                message.setText("");

                Random rand = new Random();
                int randomNum = rand.nextInt((10 - 1) + 1) + 1;

                int wait=randomNum*1000;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        receiveMessage(newmessage+" ana");
                    }
                }, wait);

            }
        });


    }
    public void sendMessage(String newmessage)
    {
            friend.messages.add(newmessage);
            Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            cdb.addMessage(friend, Icon, currentDateTimeString);
            messagelist= cdb.getAllMessages(friend.getId());
            adapter=new ChatListAdapter(ChatActivity.this, messagelist);
            chatlv.setAdapter(adapter);

    }

    public void receiveMessage(String mes)
    {
        friend.messages.add(mes);
        friend.color="#CDDC39";
        cdb.addMessage(friend, friend.getAvatar(), currentDateTimeString);
        messagelist= cdb.getAllMessages(friend.getId());
        adapter=new ChatListAdapter(ChatActivity.this, messagelist);
        chatlv.setAdapter(adapter);

        contactsFragment.changecolor();

    }

}
