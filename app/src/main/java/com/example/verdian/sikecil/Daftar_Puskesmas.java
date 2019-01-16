package com.example.verdian.sikecil;

/**
 * Created by Verdian on 09-Jan-17.
 */
public class Daftar_Puskesmas extends Koneksi {
    String URL ="http://192.168.43.130/sikecil/puskesmas.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL+"?operasi=view";
            System.out.println("URL Tampil puskesmas: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
}
