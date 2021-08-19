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
}
