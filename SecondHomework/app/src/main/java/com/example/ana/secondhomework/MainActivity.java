package com.example.ana.secondhomework;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private boolean list_visibile = false;

    ViewStub listview, gridview;
    GridView grid;
    ListView list;

    CustomGrid gridadapter;
    CustomList listadapter;

    ArrayList<String> web = new ArrayList<String>();
    ArrayList<Integer> imageId = new ArrayList<Integer>();
    ArrayList<String> date = new ArrayList<String>();
    Date lastModDate;
    File temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        File sd = Environment.getRootDirectory();

        String path=sd.getPath();
        final TextView tvName = (TextView)findViewById(R.id.path);
        tvName.setText(path);

        final File[] sdDirList = sd.listFiles();

        for (int i = 0; i < sdDirList.length; i++) {
            web.add(sdDirList[i].getName());
            temp=sdDirList[i];
            lastModDate = new Date(temp.lastModified());
            date.add(lastModDate.toString());
            if(sdDirList[i].isDirectory()) imageId.add(R.drawable.folder);
            else imageId.add(R.drawable.notfolder);
        }



         listview = (ViewStub) findViewById(R.id.list_view);
         gridview = (ViewStub) findViewById(R.id.grid_view);
        listview.inflate();
        gridview.inflate();
        list = (ListView) findViewById(R.id.list);
        grid = (GridView) findViewById(R.id.grid);




        gridadapter = new CustomGrid(MainActivity.this, web, imageId);
        listadapter = new CustomList(MainActivity.this, web, imageId, date);


        setAdapters();

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        if (sdDirList[position].isDirectory()) {
                            File[] content = sdDirList[position].listFiles();
                            if (content == null || content.length == 0) {
                                Toast.makeText(MainActivity.this, "You Clicked at empty folder " + web.get(position), Toast.LENGTH_SHORT).show();
                            } else {
                                File fileDirectory = Environment.getRootDirectory();
                                File fileDir = fileDirectory.listFiles()[position];

                                String childpath=fileDir.getPath();
                                tvName.setText(childpath);

                                File[] dirFiles = fileDir.listFiles();

                                web.clear();
                                imageId.clear();
                                for (int i = 0; i < dirFiles.length; i++) {
                                    web.add(dirFiles[i].getName());
                                    if (dirFiles[i].isDirectory()) imageId.add(R.drawable.folder);
                                    else imageId.add(R.drawable.notfolder);
                                }

                                setAdapters();
                            }

                        }
                        else Toast.makeText(MainActivity.this, "You Clicked at file " + web.get(position), Toast.LENGTH_SHORT).show();
                    }
                });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (sdDirList[position].isDirectory()) {
                    File[] content = sdDirList[position].listFiles();
                    if (content == null || content.length == 0) {
                        Toast.makeText(MainActivity.this, "You Clicked at empty folder " + web.get(position), Toast.LENGTH_SHORT).show();
                    } else {
                        File fileDirectory = Environment.getRootDirectory();
                        File fileDir = fileDirectory.listFiles()[position];

                        String childpath=fileDir.getPath();
                        tvName.setText(childpath);

                        File[] dirFiles = fileDir.listFiles();

                        web.clear();
                        imageId.clear();
                        for (int i = 0; i < dirFiles.length; i++) {
                            web.add(dirFiles[i].getName());
                            if (dirFiles[i].isDirectory()) imageId.add(R.drawable.folder);
                            else imageId.add(R.drawable.notfolder);
                        }

                        setAdapters();
                    }

                }
                else Toast.makeText(MainActivity.this, "You Clicked at file " + web.get(position), Toast.LENGTH_SHORT).show();
            }
        });



                }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.back) {

           back();
        }
        if(id==R.id.change)
        {
            changeView();

        }

        return super.onOptionsItemSelected(item);
    }

public void back()
{
    String path = getApplicationInfo().dataDir;
    File file=new File(path);
    String parentpath=file.getParentFile().getName();
    File parentfile=new File(parentpath);

    //File files = file.listFiles()[1];
    String backpath=file.getPath();
    TextView tvName = (TextView)findViewById(R.id.path);
    tvName.setText(backpath);

    File[] folder=file.listFiles();
    web.clear();
    imageId.clear();
    for (int i = 0; i < folder.length; i++) {
        web.add(folder[i].getName());
        if (folder[i].isDirectory()) imageId.add(R.drawable.folder);
        else imageId.add(R.drawable.notfolder);
    }

    gridadapter = new CustomGrid(MainActivity.this, web, imageId);
    listadapter = new CustomList(MainActivity.this, web, imageId, date);
    setAdapters();
}

    public void changeView()
    {
        if(list_visibile) {
            listview.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            list_visibile = false;
            setAdapters();
        }

        else {
            gridview.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            list_visibile = true;
            setAdapters();
        }
    }

    private void setAdapters() {

        if(list_visibile) {
            listadapter = new CustomList(MainActivity.this, web, imageId, date);
            list.setAdapter(listadapter);
        }

        else {
            gridadapter = new CustomGrid(MainActivity.this, web, imageId);
            grid.setAdapter(gridadapter);
        }

    }

}
