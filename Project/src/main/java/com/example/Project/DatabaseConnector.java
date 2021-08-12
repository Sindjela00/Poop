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

    public DatabaseConnector() {// Ovo povezuje povezuje aplikaciju na bazu
        String dburl = "jdbc:mysql://localhost:3306/baza";
        try {
            connection = DriverManager.getConnection(dburl, "MyLogin", "123123");
            statement = connection.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean nadjiUser(String tip, String ime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Korisnik WHERE " + tip + "=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            System.out.println(statement.toString());
            if (result.next()) {
                System.out.println(result.getString(tip));
                if (result.getString(tip) != null)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
    public boolean nadjifirmu(String tip, String ime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM firma WHERE " + tip + "=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            System.out.println(statement.toString());
            if (result.next()) {
                System.out.println(result.getString(tip));
                if (result.getString(tip) != null)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    public Integer dajId(String ime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Korisnik WHERE username=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public List<Oglas> sviOglasi() {
        List<Oglas> lista = new ArrayList<Oglas>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Oglasi");
            while (result.next()) {
                //lista.add(new Oglas(result.getInt(0), result.getString(1), result.getInt(2), result.getString(3),result.getString(4)));
            }
            return lista;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean proveriSignin(String username, String pass, boolean person) {
        PreparedStatement statement;
        try {
            
                statement = connection.prepareStatement("Select * from Korisnik where username=? AND password=?");
            

            statement.setString(1, username);
            statement.setString(2, pass);
            System.out.print(statement.toString());
            ResultSet result = statement.executeQuery();
            
            if (result.next()) {
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean ubaciUsera(String username, String pass, String ime, String email,boolean person) {
        try {
            PreparedStatement statement;
            
                statement = connection.prepareStatement(
                    "INSERT INTO korisnik (ime,username , password  , email , poslodavac , admin  ) values (?,?,?,?,?,?)");
            
            statement.setString(2, username);
            statement.setString(3, pass);
            statement.setString(1, ime);
            statement.setString(4, email);
            statement.setBoolean(5, person);
            statement.setBoolean(6, false);
            statement.executeUpdate();
            if (nadjiUser("ime", ime))
                return true;
            else
                return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public List<errorCode> Daj_gradove(){
        List<errorCode> lista = new ArrayList<errorCode>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Mesto");
            while (result.next()) {
                lista.add(new errorCode(result.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public List<errorCode> Daj_tagove(){
        List<errorCode> lista = new ArrayList<errorCode>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM tags");
            while (result.next()) {
                lista.add(new errorCode(result.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}