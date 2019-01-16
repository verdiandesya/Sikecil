package com.example.verdian.sikecil;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowListAntrian extends AppCompatActivity {
    Ambil_Nomor biodata = new Ambil_Nomor();
    TableLayout tabelBiodata;

    int i;

    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_antrian);

        tabelBiodata = (TableLayout) findViewById(R.id.tableBiodata);

        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.rgb(243,156,18));

        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNama = new TextView(this);
        TextView viewHeaderTanggal_Lahir = new TextView(this);
        TextView viewHeaderUsia = new TextView(this);
        TextView viewHeaderAction= new TextView(this);

        viewHeaderId.setText("Nomor Antrian");
        viewHeaderNama.setText("Nama");
        viewHeaderTanggal_Lahir.setText("Alamat");
        viewHeaderUsia.setText("Telepon");
        viewHeaderAction.setText("Status");

        viewHeaderId.setTextColor(Color.WHITE);
        viewHeaderNama.setTextColor(Color.WHITE);
        viewHeaderTanggal_Lahir.setTextColor(Color.WHITE);
        viewHeaderUsia.setTextColor(Color.WHITE);
        viewHeaderAction.setTextColor(Color.WHITE);

        barisTabel.addView(viewHeaderId);
        barisTabel.addView(viewHeaderNama);
        barisTabel.addView( viewHeaderTanggal_Lahir);
        barisTabel.addView(viewHeaderUsia);
        barisTabel.addView(viewHeaderAction);

        viewHeaderId.setPadding(10, 5, 10, 5);
        viewHeaderNama.setPadding(10, 5, 10, 5);
        viewHeaderTanggal_Lahir.setPadding(10, 5, 10, 5);
        viewHeaderUsia.setPadding(10, 5, 10, 5);
        viewHeaderAction.setPadding(10, 5, 10, 5);

        tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String name = jsonChildNode.optString("nama");
                String alamat = jsonChildNode.optString("alamat");
                String telp = jsonChildNode.optString("telp");
                String status = jsonChildNode.optString("status");
                String id = jsonChildNode.optString("id");

                System.out.println("Nama :" + name);
                System.out.println("Tanggal :" + alamat);
                System.out.println("Usia :" + telp);
                System.out.println("ID :" + id);
                System.out.println("ID :" + status);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewId = new TextView(this);
                viewId.setText(id);
                viewId.setPadding(10, 1, 10, 1);
                viewId.setGravity(Gravity.LEFT|Gravity.CENTER);
                barisTabel.addView(viewId);

                TextView viewNama = new TextView(this);
                viewNama.setText(name);
                viewNama.setPadding(10, 1, 10, 1);
                viewNama.setGravity(Gravity.LEFT|Gravity.CENTER);
                barisTabel.addView(viewNama);

                TextView viewAlamat = new TextView(this);
                viewAlamat.setText(alamat);
                viewAlamat.setPadding(10, 1, 10, 1);
                viewAlamat.setGravity(Gravity.LEFT|Gravity.CENTER);
                barisTabel.addView(viewAlamat);

                TextView viewUsia = new TextView(this);
                viewUsia.setText(telp);
                viewUsia.setPadding(10, 1, 10, 1);
                viewUsia.setGravity(Gravity.LEFT|Gravity.CENTER);
                barisTabel.addView(viewUsia);

                TextView viewTelp = new TextView(this);
                viewTelp.setText(status);
                viewTelp.setPadding(10, 1, 10, 1);
                viewTelp.setGravity(Gravity.LEFT|Gravity.CENTER);
                barisTabel.addView(viewTelp);

                tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
