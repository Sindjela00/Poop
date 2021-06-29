package com.example.Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    Connection connection = null;
    Statement statement;

    public DatabaseConnector(){//Ovo povezuje povezuje aplikaciju na bazu
        String dburl = "jdbc:mysql://localhost:3306/baza";
        try {
            connection = DriverManager.getConnection(dburl,"MyLogin", "123123");
            statement = connection.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public boolean nadjiUser(String tip,String ime){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE ?=?");
            statement.setString(1,tip);
            statement.setString(2,ime);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                if(result.getString("ime")!=null)return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    
    public Integer dajId(String ime){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE username=?");
            statement.setString(1,ime);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt("id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    
    }

    public List<Oglas> sviOglasi(){
        List<Oglas> lista = new ArrayList<Oglas>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM testtest");
            while(result.next()){
                lista.add(new Oglas(result.getInt(0),result.getString(1),result.getInt(2),result.getString(3),result.getString(4)));
            }
            return lista;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public boolean proveriSignin(String username,String pass){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("Select * from person where username=? AND pass=?");
            statement.setString(1,username);
            statement.setString(2,pass);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    public boolean ubaciUsera(String username,String pass,String ime,String email,String brojTelefona,String Place){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person (username , pass , ime , email , phone , place ) values (?,?,?,?,?)");
            statement.setString(1,username);
            statement.setString(2,pass);
            statement.setString(3,ime);
            statement.setString(4,email);
            statement.setString(5,brojTelefona);
            statement.setString(6,Place);
            if(nadjiUser("ime", ime)) return true;
            else return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}