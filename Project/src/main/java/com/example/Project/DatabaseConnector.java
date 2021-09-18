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

    public void databaseInit() {
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
                    return result.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * public boolean nadjifirmu(String tip, String ime) { try { PreparedStatement
     * statement = connection.prepareStatement("SELECT * FROM firma WHERE " + tip +
     * "=?"); statement.setString(1, ime); ResultSet result =
     * statement.executeQuery(); System.out.println(statement.toString()); if
     * (result.next()) { System.out.println(result.getString(tip)); if
     * (result.getString(tip) != null) return true; } } catch (SQLException e) {
     * e.printStackTrace(); } return false; }
     */

    public Integer dajId(String ime) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT idKorisnik FROM Korisnik WHERE username=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println(result.getInt(1));
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
                // lista.add(new Oglas(result.getInt(0), result.getString(1), result.getInt(2),
                // result.getString(3),result.getString(4)));
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

    public boolean ubaciTelefon(Integer id, Integer broj) {
        try {
            PreparedStatement statement;
            statement = connection.prepareStatement("INSERT INTO telefoni  values (?,?)");
            statement.setInt(1, id);
            statement.setInt(2, broj);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> listaTelefona(Integer id) {
        List<Integer> lista = new ArrayList<Integer>();
        try {
            PreparedStatement statement;
            statement = connection.prepareStatement("SELECT * FROM telefoni WHERE idkorisnika=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                lista.add(result.getInt(1));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }

    public boolean ubaciUsera(String username, String pass, String ime, String email, Integer mesto, Integer broj,
            boolean person) {
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
            if (nadjiUser("ime", ime)) {
                Integer id = dajId(username);
                statement = connection.prepareStatement("INSERT INTO telefoni  values (?,?)");
                statement.setInt(1, id);
                statement.setInt(2, broj);
                statement.executeUpdate();
                return true;
            } else
                return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public List<Mesto> Daj_gradove() {
        List<Mesto> lista = new ArrayList<Mesto>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM Mesto");
            while (result.next()) {
                lista.add(new Mesto(result.getInt(1), result.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Tagovi> Daj_tagove() {
        List<Tagovi> lista = new ArrayList<Tagovi>();
        lista.add(new Tagovi(0, "Sve", ""));
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tags");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                lista.add(new Tagovi(0 - result.getInt(1), result.getString(2), ""));
                statement = connection.prepareStatement("SELECT * FROM podtags where idkategorije=?");
                statement.setInt(1, result.getInt(1));
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    lista.add(new Tagovi(res.getInt(1), result.getString(2), res.getString(3)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean proveriString(String string) {
        if (string.matches(".*[\\\0\'\"\b\n\r\t].*"))
            return false;

        return true;
    }


    public List<Oglas> Daj_Oglase(Integer tagovi, Integer mesto) {
        List<Oglas> lista = new ArrayList<Oglas>();

        PreparedStatement statement;
        try {
            if (tagovi < 0) {
                statement = connection.prepareStatement("Select id from podtags where idkategorije=?");
                statement.setInt(1, 0 - tagovi);
                ResultSet result = statement.executeQuery();
                System.out.println("prvo");
                while (result.next()) {
                    statement = connection.prepareStatement("Select idoglasa from tagovi where idtaga=?");
                    statement.setInt(1, result.getInt(1));
                    ResultSet res = statement.executeQuery();
                    System.out.println("drugo");
                    while (res.next()) {
                        if (mesto != 0) {
                            statement = connection.prepareStatement("Select * from oglas where idoglas=? and mesto=?");
                            statement.setInt(1, res.getInt(1));
                            statement.setInt(2, mesto);
                            ResultSet r = statement.executeQuery();
                            System.out.println("trece");
                            if (r.next())
                                lista.add(Daj_Oglas(res.getInt(1)));
                        } else
                            lista.add(Daj_Oglas(res.getInt(1)));
                    }
                }
            } else if (tagovi != 0) {
                statement = connection.prepareStatement("Select idoglasa from tagovi where idtaga=?");
                statement.setInt(1, tagovi);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    if (mesto != 0) {
                        statement = connection.prepareStatement("Select * from oglas where idoglas=? and mesto=?");
                        statement.setInt(1, res.getInt(1));
                        statement.setInt(2, mesto);
                        ResultSet r = statement.executeQuery();
                        if (r.next())
                            lista.add(Daj_Oglas(res.getInt(1)));
                    } else
                        lista.add(Daj_Oglas(res.getInt(1)));
                }
            } else {
                if (mesto != 0) {
                    statement = connection.prepareStatement("Select * from oglas where  mesto=?");
                    statement.setInt(1, mesto);
                    ResultSet r = statement.executeQuery();
                
                    while (r.next())
                        lista.add(Daj_Oglas(r.getInt(1)));
                } else {
                    statement = connection.prepareStatement("Select * from sveooglasu");
                    ResultSet r = statement.executeQuery();
                    
                    while (r.next()){
                        //System.out.println(r.getString(3));
                        lista.add(Daj_Oglas(r.getInt(1)));
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }

    public Oglas Daj_Oglas(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sveooglasu where idoglas=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Oglas(result.getInt(1), result.getString(2), result.getString(3), result.getBoolean(4),
                        result.getInt(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Integer dajIdMesta(String mesto) {
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

    public boolean proveriCoveka(String username) {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("Select username from korisnik where username=?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                    return true;
                }
                return false;
            } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public Korisnik Daj_Korisnika(Integer id) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("Select * from sveokorisniku where idkorisnik=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Korisnik(result.getInt(1), result.getString(2), result.getString(4), result.getString(3),
                        result.getString(5), result.getString(6), result.getString(7), result.getInt(8),
                        result.getInt(9), result.getInt(10), result.getInt(11), result.getInt(12));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<Korisnik> Daj_Korisnike(Integer idoglasa) {
        List<Korisnik> lista = new ArrayList<Korisnik>();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("Select * from prijave where idoglas=?");
            statement.setInt(1, idoglasa);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                PreparedStatement stm = connection.prepareStatement("Select * from sveokorisniku where idkorisnik=?");
                stm.setInt(result.getInt(1), 1);
                ResultSet res = stm.executeQuery();
                while (res.next()) {
                    lista.add(new Korisnik(res.getInt(1), res.getString(2), res.getString(4), res.getString(3), "",
                            result.getString(3), res.getString(7), res.getInt(8), res.getInt(9), res.getInt(10),
                            res.getInt(11), res.getInt(12)));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }
    public List<Korisnik> Daj_Poslodavce() {
        List<Korisnik> lista = new ArrayList<Korisnik>();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("Select * from Korisnik where poslodavac=1");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                PreparedStatement stm = connection.prepareStatement("Select * from sveokorisniku where idkorisnik=?");
                stm.setInt(1, result.getInt(1));
                ResultSet res = stm.executeQuery();
                while (res.next()) {
                    lista.add(new Korisnik(res.getInt(1), res.getString(2), res.getString(4), res.getString(3), "",
                            result.getString(3), res.getString(7), res.getInt(8), res.getInt(9), res.getInt(10),
                            res.getInt(11), res.getInt(12)));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }
    

    public String dajMail(String tip, String ime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Korisnik WHERE " + tip + "=?");
            statement.setString(1, ime);
            ResultSet result = statement.executeQuery();
            System.out.println(statement.toString());
            if (result.next()) {
                if (result.getString(tip) != null)
                    return result.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Integer id, String ime, String username, String passoword, String email, Integer mesto,
            String opis) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "Update korisnik set ime=? , username=? , password=? , email=? , idmesta = ? , opis = ? where idkorisnik=?");
            statement.setString(1, ime);
            if ((dajId(username) != null && dajId(username) != id) || dajId(username) == null) {
                statement.setString(2, username);
            } else {
                statement.setString(2, dajUser("idkorisnik", id.toString()));
            }
            statement.setString(3, passoword);
            if (dajMail("email", email) != null && dajMail("idkorisnik", id.toString()) == email
                    || dajMail("email", email) != null) {
                statement.setString(4, email);
            } else {
                statement.setString(4, dajMail("idkorisnik", id.toString()));
            }
            statement.setInt(5, mesto);
            statement.setString(6, opis);
            ResultSet result = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean proveriprijavu(Integer idcoveka, Integer idoglasa) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM prijave where idcovek=? and idoglas=?");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idoglasa);

            ResultSet result = statement.executeQuery();
            if (result.next())
                return true;
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean prijavise(Integer idcoveka, Integer idoglasa, String cv) {
        PreparedStatement statement;
        try {
            if (proveriprijavu(idcoveka, idoglasa))
                return false;
            statement = connection.prepareStatement("INSERT INTO prijave values (?,?,?)");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idoglasa);
            statement.setString(3, cv);
            ResultSet result = statement.executeQuery();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean proverilajk(Integer idcoveka, Integer idoglasa) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM lajkovi where idcoveka=? and idOglasa=?");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idoglasa);

            ResultSet result = statement.executeQuery();
            if (result.next())
                return true;
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean lajkuj(Integer idcoveka, Integer idoglasa, String like) {
        PreparedStatement statement;
        try {
            if (proverilajk(idcoveka, idoglasa)) {
                statement = connection.prepareStatement("DELETE FROM lajkovi WHERE idcoveka=? and idOglasa=?;");
                statement.setInt(1, idcoveka);
                statement.setInt(2, idoglasa);
                ResultSet result = statement.executeQuery();
                if (like == "izbrisi") {
                    return true;
                }
            }
            statement = connection.prepareStatement("INSERT INTO lajkovi values (?,?,?)");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idoglasa);
            if (like == "true") {
                statement.setBoolean(3, true);
            } else if (like == "false")
                statement.setBoolean(3, false);
            else
                return false;
            ResultSet result = statement.executeQuery();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean proveriocenu(Integer idcoveka, Integer idcoveka2) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM ocene where idcoveka=? and idocenjenog=?");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idcoveka2);

            ResultSet result = statement.executeQuery();
            if (result.next())
                return true;
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean oceni(Integer idcoveka, Integer idoglasa, Integer ocena) {
        PreparedStatement statement;
        try {
            if (proverilajk(idcoveka, idoglasa)) {
                statement = connection.prepareStatement("DELETE FROM ocena WHERE idcoveka=? and idOglasa=?;");
                statement.setInt(1, idcoveka);
                statement.setInt(2, idoglasa);
                ResultSet result = statement.executeQuery();
                if (ocena == -1) {
                    return true;
                }
            }
            statement = connection.prepareStatement("INSERT INTO ocena values (?,?,?)");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idoglasa);
            if (ocena == -1) {
                return false;
            }
            statement.setInt(3, ocena);
            ResultSet result = statement.executeQuery();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public Integer dataOcena(Integer idcoveka, Integer idcoveka2) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM ocene where idcoveka=? and idocenjenog=?");
            statement.setInt(1, idcoveka);
            statement.setInt(2, idcoveka2);

            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt(3);
            return -1;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public List<Oglas> Daj_Mojeoglase(Integer idcoveka) {
        PreparedStatement statement;
        List<Oglas> oglasi = new ArrayList<Oglas>();
        try {
            statement = connection.prepareStatement("SELECT idOglas FROM oglas where idcoveka=?");
            statement.setInt(1, idcoveka);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                statement = connection.prepareStatement("Select * from sveooglasu where idoglas=?");
                statement.setInt(1, res.getInt(1));
                ResultSet result = statement.executeQuery();
                if (result.next()) {

                    oglasi.add(new Oglas(result.getInt(1), result.getString(2), result.getString(3),
                            result.getBoolean(4), result.getInt(5), result.getString(6), result.getString(7),
                            result.getInt(8), result.getInt(9)));
                }
            }
            return oglasi;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<errorCode> Daj_telefone(Integer covek) {
        PreparedStatement statement;
        List<errorCode> telefoni = new ArrayList<errorCode>();
        try {
            statement = connection.prepareStatement("SELECT * FROM telefoni where idkorisnika=?");
            statement.setInt(1, covek);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                telefoni.add(new errorCode(res.getString(2)));
            }
            return telefoni;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<Korisnik> prijavljeni(Integer id) {
        PreparedStatement statement;
        List<Korisnik> korisnik = new ArrayList<Korisnik>();
        try {
            statement = connection.prepareStatement("SELECT idcovek,cv FROM prijave where idoglas=?");
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                statement = connection.prepareStatement("Select * from sveokorisniku where idkorisnik=?");
                statement.setInt(1, result.getInt(1));
                ResultSet res = statement.executeQuery();
                if (res.next()) {

                    korisnik.add(new Korisnik(res.getInt(1), res.getString(2), res.getString(4), res.getString(3), "",
                            result.getString(2), res.getString(7), res.getInt(8), res.getInt(9), res.getInt(10),
                            res.getInt(11), res.getInt(12)));
                }
            }
            return korisnik;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean jelovomoje(Integer idcoveka, Integer idoglasa) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM oglas where idoglas=? and idcoveka=?");
            statement.setInt(1, idoglasa);
            statement.setInt(2, idcoveka);
            ResultSet result = statement.executeQuery();
            if (result.next())
                return true;
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public Prijavljen logovan(String username){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT poslodavac,admin FROM korisnik where username=?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next())
                return new Prijavljen(true, result.getBoolean(1), result.getBoolean(2));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new Prijavljen(false,false,false);
    }

    public boolean IzbrisiOglas(Integer id){
        PreparedStatement statement;
        
        try {
            statement= connection.prepareStatement("Delete from prijave where idoglas=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement= connection.prepareStatement("Delete from poruke where idoglasa=? and odgovor<>0");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement= connection.prepareStatement("Delete from poruke where idoglasa=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement= connection.prepareStatement("Delete from lajkovi where idoglasa=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement= connection.prepareStatement("Delete from tagovi where idoglasa=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement= connection.prepareStatement("Delete from oglas where idoglas=?");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean dodajOglas(Integer covek,String naziv,Boolean tip,Integer plata,String opis,Integer mesto){
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("INSERT INTO oglas(idcoveka,naslov,tip,plata,opis,mesto) values(?,?,?,?,?,?)");
            statement.setInt(1, covek);
            statement.setString(2, naziv);
            statement.setBoolean(3, tip);
            statement.setInt(4, plata);
            statement.setString(5, opis);
            statement.setInt(6,mesto);
            statement.executeUpdate();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return false;
    }



}
