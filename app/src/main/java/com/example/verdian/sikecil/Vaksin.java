package com.example.verdian.sikecil;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Vaksin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String AR_ID ="id";
    String[] listArray={"Asus","Acer","Apple","Samsung","Thoshiba","Sony","Xiomi","Motorola"};

    Deskripsi_Vaksin vaksin = new Deskripsi_Vaksin();

    JSONArray arrayBiodata;
    ArrayList angkatan = new ArrayList();
    ArrayList<ImageView> icon = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaksin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        try{
            arrayBiodata = new JSONArray(vaksin.tampilBiodata());
            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String name = jsonChildNode.optString("nama_vaksin");
                String kepanjangan = jsonChildNode.optString("kepanjangan");
                String id = jsonChildNode.optString("id_vaksin");

                System.out.println("Nama :" + name);
                System.out.println("Tanggal :" + kepanjangan);
                System.out.println("ID :" + id);

                HashMap map = new HashMap();

                map.put("nm",name);
                map.put("kep",kepanjangan);
                map.put(AR_ID,id);

                angkatan.add(map);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapater_listview();
    }

    public void adapater_listview(){
        ListAdapter adapter = new SimpleAdapter(this, angkatan,R.layout.activity_listview,new String[] { "nm", "kep", AR_ID}, new int[] {R.id.nama_vaksin, R.id.kepanjangan, R.id.kode});
        //ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_listview,listArray);
        ListView listview =(ListView) findViewById(R.id.array_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String kode = ((TextView) view.findViewById(R.id.kode)).getText().toString();
                Intent in = new Intent(Vaksin.this,Detail_Vaksiin.class);
                in.putExtra(AR_ID,kode);
                startActivity(in);
            }
        });
    }

    @Override

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vaksin, menu);
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
            Intent i =new Intent(getApplicationContext(),About.class);

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i =new Intent(getApplicationContext(),Anak.class);

            startActivity(i);
            return true;
        } else if (id == R.id.nav_gallery) {
            Intent i =new Intent(getApplicationContext(),Makanan.class);

            startActivity(i);
            return true;

        } else if (id == R.id.nav_slideshow) {
            Intent i =new Intent(getApplicationContext(),Vaksin.class);

            startActivity(i);
            return true;

        } else if (id == R.id.nav_map) {
            Intent i =new Intent(getApplicationContext(),Puskesmas.class);

            startActivity(i);
            return true;

        } else if (id == R.id.nav_about) {
            Intent i =new Intent(getApplicationContext(),About.class);

            startActivity(i);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
