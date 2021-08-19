package com.example.Project;

public class Mesto {
    Integer id;
    String Naziv;

    public Mesto(Integer id, String naslov) {
        this.id = id;
        this.Naziv = naslov;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return Naziv;
    }
    
    public void setNaslov(String naziv) {
        this.Naziv = naziv;
    }
}
