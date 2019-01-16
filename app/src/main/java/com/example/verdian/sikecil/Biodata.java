package com.example.verdian.sikecil;

/**
 * Created by dianvika on 25/12/2016.
 */
public class Biodata extends Koneksi {
    String URL = "http://192.168.43.130/sikecil/anak.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL+"?operasi=view";
            System.out.println("URL Tampil Biodata: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserBiodata(String nama, String tgl, String usia) {
        nama = nama.replace(" ","%20");
        tgl = tgl.replace(" ","%20");
        usia = usia.replace(" ","%20");
        try {
            url = URL+"?operasi=insert&nama=" + nama + "&tgl=" + tgl+ "&usia=" + usia;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int id) {
        try {
            url =  URL+"?operasi=get_biodata_by_id&id_anak=" + id;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(String id, String nama, String tgl, String usia) {
        nama = nama.replace(" ","%20");
        tgl = tgl.replace(" ","%20");
        usia = usia.replace(" ","%20");
        try {
            url =  URL+"?operasi=update&id_anak=" + id + "&nama=" + nama + "&tgl=" + tgl+ "&usia=" + usia;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deleteBiodata(int id) {
        try {
            url =  URL+"?operasi=delete&id_anak=" + id;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}