package com.example.verdian.sikecil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class Detail_Vaksiin extends AppCompatActivity {

    Deskripsi_Vaksin vaksin = new Deskripsi_Vaksin();

    JSONArray arrayBiodata;
    ArrayList angkatan = new ArrayList();

    private static final String AR_ID= "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__vaksiin);

        Intent in = getIntent();
        String kode = in.getStringExtra(AR_ID);
        System.out.println("kode :" + kode);
        int kd = Integer.parseInt(kode);
        try{
            arrayBiodata = new JSONArray(vaksin.getBiodataById(kd));
            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject ar = arrayBiodata.getJSONObject(i);

                TextView nama = (TextView) findViewById(R.id.judul  );
                TextView detail = (TextView) findViewById(R.id.detail);
                TextView isi = (TextView) findViewById(R.id.isi);

                String nama_vaksin = ar.getString("nama_vaksin");
                String kep = ar.getString("kepanjangan");
                String deskripsi = ar.getString("deskripsi");

                nama.setText(nama_vaksin);
                detail.setText(kep);
                isi.setText(deskripsi);
            }

        }catch (JSONException e) {
            e.printStackTrace();

        }
}
}
