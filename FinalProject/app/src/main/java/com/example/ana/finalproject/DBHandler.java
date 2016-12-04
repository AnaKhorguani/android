package com.example.ana.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "DB1";
    private static final String TABLE_friends = "friends";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Number = "number";
    private static final String KEY_Avatar = "avatar";

    String CREATE_Friends_TABLE = " CREATE TABLE " + TABLE_friends + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
            + KEY_Number + " TEXT, "+ KEY_Avatar + " BLOB " + ")" ;



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Friends_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_friends);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_friends);
// Creating tables again
        onCreate(db);
    }

    public void addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, friend.getId());
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_Number, friend.getNumber());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        friend.getAvatar().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        values.put(KEY_Avatar, byteArray);
// Inserting Row
        db.insert(TABLE_friends, null, values);
        db.close(); // Closing database connection
    }

    public Friend getFriend(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_friends, new String[] { KEY_ID,
                        KEY_NAME, KEY_Number }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
        Friend contact = new Friend(Integer.parseInt(cursor.getString(0)),  cursor.getString(1), cursor.getString(2), bitmap);

        return contact;
    }

    public List<Friend> getAllFriends() {
        List<Friend> friendList = new ArrayList<Friend>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_friends;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setId(Integer.parseInt(cursor.getString(0)));
                friend.setName(cursor.getString(1));
                friend.setNumber(cursor.getString(2));
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                friend.setAvatar(bitmap);
// Adding contact to list
                friendList.add(friend);
            } while (cursor.moveToNext());
        }
// return contact list
        return friendList;
    }

    // Getting shops Count
    public int getFriendsCount() {
        String countQuery = "SELECT * FROM " + TABLE_friends;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friend.getName());
        values.put(KEY_Number, friend.getNumber());
// updating row
        return db.update(TABLE_friends, values, KEY_ID + " = ?",
                new String[]{String.valueOf(friend.getId())});
    }
    // Deleting a shop
    public void deleteFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_friends, KEY_ID + " = ?",
                new String[] { String.valueOf(friend.getId()) });
        /*db.delete(TABLE_friends, KEY_NAME + " = ?",
                new String[] { String.valueOf(friend.getName()) });*/

        db.close();
    }
}
