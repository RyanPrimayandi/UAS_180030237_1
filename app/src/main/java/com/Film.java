package com;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String caption;
    private String penulis;
    private String isiFilm;
    private String link;

    public Film(int idFilm, String judul, Date tanggal, String gambar, String caption, String penulis, String isiFilm, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.caption = caption;
        this.penulis = penulis;
        this.isiFilm = isiFilm;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsiFilm() {
        return isiFilm;
    }

    public void setIsiFilm(String isiFilm) {
        this.isiFilm = isiFilm;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
