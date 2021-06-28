package com.example.Project;

public class Oglas {
    Integer id;
    String naslov;
    Integer plata;
    String tags;
    String opis;

    public Oglas(Integer id, String naslov, Integer plata, String tags, String opis) {
        this.id = id;
        this.naslov = naslov;
        this.plata = plata;
        this.tags = tags;
        this.opis = opis;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
