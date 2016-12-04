package com.example.ana.finalproject.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ana.finalproject.ChatActivity;
import com.example.ana.finalproject.CustomListAdapter;
import com.example.ana.finalproject.DBHandler;
import com.example.ana.finalproject.Friend;
import com.example.ana.finalproject.HttpHandler;
import com.example.ana.finalproject.MainActivity;
import com.example.ana.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class contactsFragment extends  Fragment{

    private static String url ="https://dl.dropboxusercontent.com/u/28030891/FreeUni/Android/assinments/contacts.json";
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText search;

    private static  ListView lv;
    private static CustomListAdapter adapter;
    public static ArrayList<Friend> friends;

    DBHandler db;

    private static Context context;
    public contactsFragment(Context c)
    {
        context=c;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.contacts, container, false);
        friends=new ArrayList<>();
        lv = (ListView) rootView.findViewById(R.id.list);
        lv.setTextFilterEnabled(true);

        search=(EditText) rootView.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        lv.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Friend friend=friends.get(position);

                Intent chat = new Intent(context, ChatActivity.class);
                chat.putExtra("friendID", friend.getId());
                startActivity(chat);
            }
        }

        );


        db = new DBHandler(context);

        List<Friend> friendslist = db.getAllFriends();
        //friendslist.clear();
       if(friendslist.isEmpty())
        {
           new contactsFragment.GetContacts().execute();
        }
        else  new contactsFragment.SQLread().execute();

        return rootView;
    }


    public static void changecolor()
    {
        adapter = new CustomListAdapter(context, friends);
        lv.setAdapter(adapter);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contactList");
                    // looping through All Contacts
                    for (int i = 0; i < 3; i++) { //contacts.length();
                        JSONObject c = contacts.getJSONObject(i);
                        Integer id = Integer.parseInt(c.getString("id"));
                        String displayName = c.getString("displayName");
                        String phoneNumber = c.getString("phoneNumber");
                        String avatarImgst=c.getString("avatarImg");
                        Bitmap btm = DownloadImageFromPath(avatarImgst);
                        Friend friend=new Friend(id, displayName, phoneNumber, btm);
                        friends.add(friend);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
*/
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new CustomListAdapter(context, friends);
            lv.setAdapter(adapter);

            new contactsFragment.SQLwrite().execute();
        }
    }


    public Bitmap DownloadImageFromPath(String src) {
        InputStream in = null;
        try {
            Log.i("URL", src);
            URL url = new URL(src);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.connect();
            in = httpConn.getInputStream();
            Bitmap bmpimg = BitmapFactory.decodeStream(in);

            return bmpimg;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    private class SQLwrite extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {

                    for (int i = 0; i < friends.size(); i++) {
                        try
                        {
                            db.addFriend(friends.get(i));
                        }
                        catch (Exception e)
                        {
                            System.out.println("can't write "+e);
                        }
                    }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private class SQLread extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<Friend> friendslist = db.getAllFriends();
            for (int i = 0; i < friendslist.size(); i++) {

                try {

                    friends.add(friendslist.get(i));

                } catch (Exception e) {
                    System.out.println("can't write " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            adapter = new CustomListAdapter(context, friends);
            lv.setAdapter(adapter);
        }
    }


}
