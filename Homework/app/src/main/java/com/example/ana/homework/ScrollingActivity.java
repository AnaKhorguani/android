package com.example.ana.homework;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

public class ScrollingActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Button uninstall, open;
    private ImageView travel, similar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        View buttonlayout= findViewById(R.id.buttons);
        View iconlayout=findViewById(R.id.ic_icons);

        travel=(ImageView)iconlayout.findViewById(R.id.travel);
        similar=(ImageView)iconlayout.findViewById(R.id.similar);
        uninstall=(Button)buttonlayout.findViewById(R.id.uninstall);
        open =(Button)buttonlayout.findViewById(R.id.open);

        uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "uninstall", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "open", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Travel&Locate", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "similar", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        String st=getString(R.string.icon_stars);
        double starnum= Double.parseDouble(st);

        RatingBar iconsratingBar = ((RatingBar) iconlayout.findViewById(R.id.iconsratingbar));
        LayerDrawable iconsstars = (LayerDrawable) iconsratingBar.getProgressDrawable();
        iconsratingBar.setRating((float) starnum);
        iconsstars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        View ratinglayout=findViewById(R.id.rating);
        RatingBar bar=(RatingBar) ratinglayout.findViewById(R.id.finalrating);
        LayerDrawable ratestar=(LayerDrawable) bar.getProgressDrawable();
        bar.setRating((float) starnum);
        ratestar.getDrawable(2).setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
