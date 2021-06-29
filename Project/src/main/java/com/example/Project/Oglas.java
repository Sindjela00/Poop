package com.example.Project;

public class Oglas {
    Integer id;
    String naslov;
    Integer plata;

    public Oglas(Integer id, String naslov, Integer plata) {
        this.id = id;
        this.naslov = naslov;
        this.plata = plata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Integer getPlata() {
        return plata;
    }

    public void setPlata(Integer plata) {
        this.plata = plata;
    }
}
