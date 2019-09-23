package com.example.uas2019;

public class Notes {

    String id;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    String tanggal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String judul;
    String deskripsi;

    public Notes(){

    }

    public Notes(String id, String tanggal, String judul, String deskripsi) {
        this.judul = judul;
        this.id = id;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
