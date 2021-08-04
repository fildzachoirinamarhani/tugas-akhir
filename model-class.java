package com.fildzachoirinamarhani.tugasakhirfinal;

public class AplikasiModel {
    private String namausaha;
    private String foto;
    private String harga;
    private int nominal;
    private String buka;
    private String tutup;
    private String keterangan;
    private String latitud;
    private String longitud;

    public AplikasiModel(String namausaha, String foto, String harga, int nominal, String buka, String tutup, String keterangan, String latitud, String longitud) {
        this.namausaha = this.namausaha;
        this.foto = this.foto;
        this.harga = this.harga;
        this.nominal = this.nominal;
        this.buka = this.buka;
        this.tutup = this.tutup;
        this.keterangan = this.keterangan;
        this.latitud = this.latitud;
        this.longitud = this.longitud;
    }

    public String getNamausaha() {
        return namausaha;
    }

    public void setNamausaha(String namausaha) {
        this.namausaha = namausaha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getBuka() {
        return buka;
    }

    public void setBuka(String buka) {
        this.buka = buka;
    }

    public String getTutup() {
        return tutup;
    }

    public void setTutup(String tutup) {
        this.tutup = tutup;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}

