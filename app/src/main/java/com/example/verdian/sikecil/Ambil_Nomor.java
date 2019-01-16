package com.example.verdian.sikecil;

/**
 * Created by dianvika on 25/12/2016.
 */
public class Ambil_Nomor extends Koneksi {
    String URL = "http://192.168.43.130/sikecil/minggir.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Biodata: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserBiodata(String nama, String alamat, String telp) {
        nama = nama.replace(" ","%20");
         alamat= alamat.replace(" ","%20");
        telp = telp.replace(" ","%20");
        try {
            url = URL + "?operasi=insert&nama=" + nama + "&alamat=" + alamat + "&telp=" + telp;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById() {
        try {
            url = URL + "?operasi=get_id_terakhir";
            System.out.println("URL Id terakhir: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
}