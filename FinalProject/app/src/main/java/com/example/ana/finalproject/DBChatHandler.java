package com.example.ana.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ana.finalproject.Friend;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.ana.finalproject.Fragments.contactsFragment.friends;

/**
 * Created by Ana on 12/4/2016.
 */

public class DBChatHandler extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "DB4";
    private static final String TABLE_messages="Messages";
    private static final String KEY_ID = "id";
    private static final String KEY_AVATAR="avatar";
    private static final String KEY_MESSAGE="message";
    private static final String KEY_DATE="send_time";

    String CREATE_Message_TABLE= " CREATE TABLE " + TABLE_messages + "(" + KEY_ID + " INTEGER, " + KEY_MESSAGE + " Text, " +
            KEY_AVATAR + " BLOB, " + KEY_DATE + " Text " + ")" ;
    public DBChatHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Message_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_friends);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_Message_TABLE);
// Creating tables again
        onCreate(db);
    }

    public void addMessage(Friend friend, Bitmap btm, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, friend.getId());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        btm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        values.put(KEY_AVATAR, byteArray);

        values.put(KEY_MESSAGE, friend.messages.get(friend.messages.size()-1));
        values.put(KEY_DATE, data);
// Inserting Row
        db.insert(TABLE_messages, null, values);
        db.close(); // Closing database connection
    }

   public List<HashMap<Bitmap, String >> getAllMessages(int id) {
       List<HashMap<Bitmap, String >> friendList = new ArrayList<HashMap<Bitmap, String >>();
// Select All Query
       String selectQuery = "SELECT * FROM " + TABLE_messages + " WHERE " + KEY_ID + " = " + id ;
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
               Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length);
               HashMap <Bitmap, String > hm=new HashMap<Bitmap, String>();
               hm.put(bitmap, cursor.getString(1) );
// Adding contact to list
               friendList.add(hm);
           } while (cursor.moveToNext());
       }
// return contact list
       return friendList;
   }

    public void deleteMessage(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_messages, KEY_ID + " = ?",
                new String[] { String.valueOf(friend.getId()) });

        db.close();
    }


    public List<HashMap<Bitmap, String >> getRecentMessages(int number, Context context, List<Friend> list) {


        List<HashMap<Bitmap, String >> friendList = new ArrayList<HashMap<Bitmap, String >>();
// Select All Query

        String selectQuery ="SELECT * FROM " + TABLE_messages + " GROUP BY " + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length);
                HashMap <Bitmap, String > hm=new HashMap<Bitmap, String>();

                int id=Integer.parseInt(cursor.getString(0));
                String name=list.get(id-1).getName();

                String Display=name + "\n" +cursor.getString(1);

                hm.put(bitmap, Display );
// Adding contact to list
                friendList.add(hm);
                number--;
            } while (cursor.moveToNext()||number==0);
        }
// return contact list
        return friendList;
    }

}
