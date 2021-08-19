package com.example.Project;

public class Oglas {
    Integer id;
    String ime;
    String naslov;
    boolean tip;
    Integer plata;
    String opis;
    String mesto;
    Integer likes;
    Integer dislikes;

    public Oglas(Integer id, String ime, String naslov, boolean tip, Integer plata, String opis, String Mesto,
            Integer likes, Integer dislikes) {
        this.id = id;
        this.ime = ime;
        this.naslov = naslov;
        this.tip = tip;
        this.plata = plata;
        this.opis = opis;
        this.mesto = Mesto;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public boolean isTip() {
        return tip;
    }

    public void setTip(boolean tip) {
        this.tip = tip;
    }

    public Integer getPlata() {
        return plata;
    }

    public void setPlata(Integer plata) {
        this.plata = plata;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

}
