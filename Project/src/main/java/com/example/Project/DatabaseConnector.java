package com.example.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.transform.ResultTransformer;



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
            databaseInit();
        }
    }

    public void databaseInit(){
        String dburl = "jdbc:mysql://localhost:3306/";
        try {
            Path init = Path.of("src/main/resources/schema.sql").toAbsolutePath();
            connection = DriverManager.getConnection(dburl, "MyLogin", "123123");
            statement = connection.createStatement();
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(init.toString()));
            sr.runScript(reader);
            statement.executeQuery("use baza");
        } catch (SQLException | IOException e) {
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

    public String dajUser(String tip, String ime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Korisnik WHERE " + tip + "=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            System.out.println(statement.toString());
            if (result.next()) {
                System.out.println(result.getString(tip));
                if (result.getString(tip) != null)
                    return result.getString("ime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                return result.getInt(1);
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

    public boolean ubaciUsera(String username, String pass, String ime, String email,Integer mesto,Integer broj,boolean person) {
        try {
            PreparedStatement statement;
            
                statement = connection.prepareStatement(
                    "INSERT INTO korisnik (ime,username , password  , email ,idmesta, poslodavac , admin  ) values (?,?,?,?,?,?,?)");
            
            statement.setString(2, username);
            statement.setString(3, pass);
            statement.setString(1, ime);
            statement.setString(4, email);
            statement.setInt(5, mesto);
            statement.setBoolean(6, person);
            statement.setBoolean(7, false);
            statement.executeUpdate();
            if (nadjiUser("ime", ime)){
                Integer id = dajId(username);
                statement = connection.prepareStatement(
                    "INSERT INTO telefoni  values (?,?)");
                    statement.setInt(1, id);
                    statement.setInt(2, broj);
                    statement.executeUpdate();
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public List<Mesto> Daj_gradove(){
        List<Mesto> lista = new ArrayList<Mesto>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Mesto");
            while (result.next()) {
                lista.add(new Mesto(result.getInt(1),result.getString(2)));
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
    public List<Oglas> Daj_Oglase(Integer id,String naslov,String poslodavac,String Mesto,Boolean vrsta){//glavna funkcija ima puno posla
        List<Oglas> lista = new ArrayList<Oglas>();
        try {
            String query=" where 1=1 ";
            if(id!= null)
                query += " and id ="+id.toString();
            if(naslov != null)
                query += " and naslov ='"+naslov+"'";
            if(poslodavac != null)
                query += " and ime ='"+poslodavac+"'";
            if(Mesto != null)
                query += " and mesto ='"+Mesto+"'";
            if(vrsta != null)
                query += " and tip ="+vrsta;   
            ResultSet result = statement.executeQuery("SELECT * FROM sveooglasu " + query);
            while (result.next()) {
                lista.add(new Oglas(result.getInt(1),result.getString(2),result.getString(3),result.getBoolean(4),result.getInt(5),result.getString(6),result.getString(7),result.getInt(8),result.getInt(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Integer dajIdMesta(String mesto){
        PreparedStatement statement;
            
        try {
            statement = connection.prepareStatement("Select idMesto from mesto where ime=?");
            statement.setString(1, mesto);
        ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }


    public boolean proveriCoveka(String username,Integer id){
        PreparedStatement statement;
            
        try {
            statement = connection.prepareStatement("Select username from korisnik where id=?");
            statement.setInt(1, id);
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
}