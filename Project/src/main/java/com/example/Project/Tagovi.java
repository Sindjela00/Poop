package com.example.Project;

public class Tagovi {
    Integer id;
    String kategorija;
    String podkategorija;
    public Tagovi(Integer id, String kategorija, String podkategorija) {
        this.id = id;
        this.kategorija = kategorija;
        this.podkategorija = podkategorija;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKategorija() {
        return kategorija;
    }
    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }
    public String getPodkategorija() {
        return podkategorija;
    }
    public void setPodkategorija(String podkategorija) {
        this.podkategorija = podkategorija;
    }
}
