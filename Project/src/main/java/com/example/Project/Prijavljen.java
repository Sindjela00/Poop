package com.example.Project;

public class Prijavljen {
    Boolean prijavljen;
    Boolean poslodavac;
    Boolean admin;
    public Prijavljen(Boolean prijavljen, Boolean poslodavac, Boolean admin) {
        this.prijavljen = prijavljen;
        this.poslodavac = poslodavac;
        this.admin = admin;
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