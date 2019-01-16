package com.example.verdian.sikecil;

/**
 * Created by dianvika on 25/12/2016.
 */
public class Deskripsi_Vaksin extends Koneksi {
    String URL = "http://192.168.43.130/sikecil/vaksin.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Vaksin: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int id) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id_vaksin=" + id;
            System.out.println("URL vaksin Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
}
