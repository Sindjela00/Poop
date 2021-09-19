package com.example.Project;

public class Prijavljen {
    Integer id;
    Boolean prijavljen;
    Boolean poslodavac;
    Boolean admin;
    public Prijavljen(Integer id, Boolean prijavljen, Boolean poslodavac, Boolean admin) {
        this.id = id;
        this.prijavljen = prijavljen;
        this.poslodavac = poslodavac;
        this.admin = admin;
    }

    
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getPrijavljen() {
        return prijavljen;
    }
    public void setPrijavljen(Boolean prijavljen) {
        this.prijavljen = prijavljen;
    }
    public Boolean getPoslodavac() {
        return poslodavac;
    }
    public void setPoslodavac(Boolean poslodavac) {
        this.poslodavac = poslodavac;
    }
    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

}
