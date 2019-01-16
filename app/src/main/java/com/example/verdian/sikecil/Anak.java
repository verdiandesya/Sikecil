package com.example.verdian.sikecil;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.view.ContextMenu;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.text.InputType;
import java.util.HashMap;

import android.util.TypedValue;
import android.view.Gravity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.verdian.sikecil.R;

public class Anak extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String AR_ID ="id";

    String kode;
    Biodata biodata = new Biodata();
   // TableLayout tabelBiodata;
    EditText txtUsia;
    EditText txtTgl;
    EditText editNama;

    int i;
    Intent in;
    int id;

    private DatePickerDialog dtpTgl;
    private SimpleDateFormat dateFormatter;

    ArrayList angkatan = new ArrayList();
    private String[] pilihan_menu = { "Edit Data", "Delete Data" };

    JSONArray arrayBiodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anak);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahBiodata();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tampil_list();
    }

    public void tampil_list(){
        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String name = jsonChildNode.optString("nama");
                String tgl = jsonChildNode.optString("tgl");
                String usia = jsonChildNode.optString("usia");
                String id = jsonChildNode.optString("id_anak");

                System.out.println("Nama :" + name);
                System.out.println("Tanggal :" + tgl);
                System.out.println("Usia :" + usia);
                System.out.println("ID :" + id);

                HashMap map = new HashMap();

                map.put("nm",name);
                map.put("tgl",tgl);
                map.put("usia",usia);
                map.put(AR_ID,id);

                angkatan.add(map);




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapater_listview();

    }

    public void adapater_listview(){
        ListAdapter adapter = new SimpleAdapter(this, angkatan,R.layout.activity_listview_anak,new String[] { "nm", "tgl","usia", AR_ID}, new int[] {R.id.nama, R.id.tgl, R.id.usia, R.id.id});
        ListView listview =(ListView) findViewById(R.id.array_list_anak);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                kode = ((TextView) view.findViewById(R.id.id)).getText().toString();
                in = new Intent(Anak.this, Anak.class);
                in.putExtra(AR_ID,kode);
                System.out.println("tika : "+kode);
            }
        });
        registerForContextMenu(listview);

    }

    public void onCreateContextMenu(ContextMenu menu, View tampil,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (tampil.getId() == R.id.array_list_anak) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            /*kode = ((TextView) tampil.findViewById(R.id.id)).getText().toString();
            id = Integer.parseInt(kode);*/
            System.out.println("iki lho: "+kode);
            for (int i = 0; i < pilihan_menu.length; i++) {
                menu.add(Menu.NONE, i, i, pilihan_menu[i]);
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        if(item.getTitle()=="Edit Data"){
            id=Integer.parseInt(kode);
            getDataByID(id);
            System.out.println("Kodenya : "+ id);
        }else if(item.getTitle()=="Delete Data"){
            id=Integer.parseInt(kode);
            deleteBiodata(id);
            System.out.println("delete: "+ id);
            in = new Intent(this, Anak.class);
            startActivity(in);
            this.finish();
        }
        Toast.makeText(Anak.this,
                "Kamu telah memilih : " + item.getTitle(),
                Toast.LENGTH_SHORT).show();
        return true;
    }


    private void deleteBiodata(int id){
        biodata.deleteBiodata(id);
    }
    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar now = Calendar.getInstance();
        dtpTgl = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtTgl.setText(dateFormatter.format(newDate.getTime()));

                int years = now.get(Calendar.YEAR) - newDate.get(Calendar.YEAR);
                int months = now.get(Calendar.MONTH) - newDate.get(Calendar.MONTH);
                int days = now.get(Calendar.DAY_OF_MONTH) - newDate.get(Calendar.DAY_OF_MONTH);

                if (days<0) {
                    months--;
                    days += now.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
                if (months < 0){
                    years --;
                    months +=12;
                }
                String umur = years+" tahun "+months+ " bulan "+days+" hari";
                txtUsia.setText(umur);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void tambahBiodata() {
        LinearLayout layouInput = new LinearLayout(this);
        layouInput.setOrientation(LinearLayout.VERTICAL);

        editNama = new EditText(this);
        editNama.setHint("Nama");
        layouInput.addView(editNama);

        txtTgl = new EditText(this);
        txtTgl.setHint("Tanggal");
        txtTgl.setInputType(InputType.TYPE_NULL);
        layouInput.addView(txtTgl);
        txtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtpTgl.show();
            }
        });

        txtUsia = new EditText(this);
        txtUsia.setHint("Usia");
        txtUsia.setEnabled(false);
        layouInput.addView(txtUsia);

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

                Toast.makeText(Anak.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
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
    }

    public void getDataByID(int id) {
        String namaEdit = null, tglEdit = null, usiaEdit = null;
        JSONArray arrayPersonal;

        try {
            arrayPersonal = new JSONArray(biodata.getBiodataById(id));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                namaEdit = jsonChildNode.optString("nama");
                tglEdit = jsonChildNode.optString("tgl");
                usiaEdit = jsonChildNode.optString("usia");
                // id = jsonChildNode.optString("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layouInput = new LinearLayout(this);
        layouInput.setOrientation(LinearLayout.VERTICAL);

        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layouInput.addView(viewId);

        editNama = new EditText(this);
        //editNama.setHint("Nama");
        editNama.setText(namaEdit);
        layouInput.addView(editNama);

        txtTgl = new EditText(this);
        //txtTgl.setHint("Tanggal");
        layouInput.addView(txtTgl);
        txtTgl.setText(tglEdit);
        txtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtpTgl.show();
            }
        });

        txtUsia = new EditText(this);
        //txtUsia.setHint("Usia");
        txtUsia.setText(usiaEdit);
        txtUsia.setEnabled(false);
        layouInput.addView(txtUsia);

        AlertDialog.Builder EditBiodata = new AlertDialog.Builder(this);
        EditBiodata.setTitle("Update Biodata");
        EditBiodata.setView(layouInput);
        EditBiodata.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = editNama.getText().toString();
                String tgl = txtTgl.getText().toString();
                String usia = txtUsia.getText().toString();


                System.out.println("Nama : " + nama + " Tanggal : " + tgl + " Usia : " + usia);

                String laporan = biodata.updateBiodata(viewId.getText().toString(),nama, tgl, usia);

                Toast.makeText(Anak.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }
        });

        EditBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        EditBiodata.show();
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
        getMenuInflater().inflate(R.menu.anak, menu);
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
