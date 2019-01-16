package com.example.verdian.sikecil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail_Puskesmas_2 extends AppCompatActivity {
    private static final String AR_URL = "url";
    private WebView view;

    Ambil_Nomor biodata = new Ambil_Nomor();
    TableLayout tabelBiodata;
    EditText txtUsia;
    EditText txtTgl;
    EditText editNama;

    int i;



    JSONArray arrayBiodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__puskesmas_2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        String url = in.getStringExtra(AR_URL);
        System.out.println("kode :" + url);

        view = (WebView) this.findViewById(R.id.webView);  //sinkronisasi object berdasarkan id
        view.getSettings().setJavaScriptEnabled(true);  //untuk mengaktifkan javascript
        view.loadUrl(url);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setSupportZoom(true);
        view.setWebChromeClient(new WebChromeClient());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack(); //method goback() dieksekusi untuk kembali pada halaman sebelumnya
            return true;
        }
        // Jika tidak ada history (Halaman yang sebelumnya dibuka)
        // maka akan keluar dari activity
        return super.onKeyDown(keyCode, event);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    public void tambahBiodata() {

      try{

          LinearLayout layouInput = new LinearLayout(this);
          layouInput.setOrientation(LinearLayout.VERTICAL);

          editNama = new EditText(this);
          editNama.setHint("Nama");
          layouInput.addView(editNama);

          txtTgl = new EditText(this);
          txtTgl.setHint("Alamat");
          layouInput.addView(txtTgl);

          txtUsia = new EditText(this);
          txtUsia.setHint("Telp");
          layouInput.addView(txtUsia);

          arrayBiodata = new JSONArray(biodata.getBiodataById());
          JSONObject jsonChildNode = arrayBiodata.getJSONObject(0);
          String id = jsonChildNode.optString("max(id)");
          final int no = Integer.parseInt(id)+1;
          System.out.println(no);

          AlertDialog.Builder insertBiodata = new AlertDialog.Builder(this);
          insertBiodata.setTitle("Insert Biodata");
          insertBiodata.setView(layouInput);
          insertBiodata.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  String nama = editNama.getText().toString();
                  String tgl = txtTgl.getText().toString();
                  String usia = txtUsia.getText().toString();
                  System.out.println("Nama : " + nama + " Tanggal : " + tgl + " Usia : " + usia);
                  String laporan = biodata.inserBiodata(nama, tgl, usia);
                  Toast.makeText(Detail_Puskesmas_2.this, laporan, Toast.LENGTH_SHORT).show();
                  Toast.makeText(Detail_Puskesmas_2.this, "Nomor Antrian Anda : "+ no, Toast.LENGTH_SHORT).show();
                  finish();
                  startActivity(getIntent());
              }
          });

          insertBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
              }
          });
          insertBiodata.show();
      }catch (JSONException e) {
          e.printStackTrace();
      }
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemAdd:
                tambahBiodata();
                return  true;
            case  R.id.itemShowPeta:
                Intent a =new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(a);
                return  true;
            case R.id.itemLihat:
                Intent i =new Intent(getApplicationContext(),ShowListAntrian.class);
                startActivity(i);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

}
