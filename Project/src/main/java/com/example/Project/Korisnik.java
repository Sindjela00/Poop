package com.example.Project;

public class Korisnik {
    Integer id;
    String ime;
    String username;
    String email;
    String password;
    String opis;
    String mesto;
    Integer petice;
    Integer cetvorke;
    Integer trojke;
    Integer dvojke;
    Integer jedinice;
    public Korisnik(Integer id,String ime,String username,String email,String password,String opis,String mesto,Integer petice,Integer cetvorke,Integer trojke,Integer dvojke,Integer jedinice){
        this.id = id;
        this.ime = ime;
        this.username = username;
        this.email = email;
        this.password = password;
        this.opis = opis;
        this.mesto = mesto;
        this.petice = petice;
        this.cetvorke = cetvorke;
        this.trojke = trojke;
        this.dvojke = dvojke;
        this.jedinice = jedinice;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public Integer getPetice() {
        return petice;
    }
    public void setPetice(Integer petice) {
        this.petice = petice;
    }
    public Integer getCetvorke() {
        return cetvorke;
    }
    public void setCetvorke(Integer cetvorke) {
        this.cetvorke = cetvorke;
    }
    public Integer getTrojke() {
        return trojke;
    }
    public void setTrojke(Integer trojke) {
        this.trojke = trojke;
    }
    public Integer getDvojke() {
        return dvojke;
    }
    public void setDvojke(Integer dvojke) {
        this.dvojke = dvojke;
    }
    public Integer getJedinice() {
        return jedinice;
    }
    public void setJedinice(Integer jedinice) {
        this.jedinice = jedinice;
    }
    
}
