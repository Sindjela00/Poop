package com.example.Project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    DatabaseConnector db = new DatabaseConnector();

    @GetMapping("/")
    public String home(HttpServletRequest req, HttpServletResponse res) {
        Cookie cookie;
        if ((cookie = CookieManager.getCookie(req)) != null) {
            if (db.nadjiUser("username", CookieManager.getContent(cookie)))
                return "index";
        }
        return "index";
    }

    @GetMapping("/oglasi")
    public String oglasi(HttpServletRequest req, HttpServletResponse res, @RequestParam(required = false) String poslodavac,@RequestParam(required = false) String mesto,@RequestParam(required = false) String radno_vreme){
        Cookie cookie;
        if ((cookie = CookieManager.getCookie(req)) != null) {
            if (db.nadjiUser("username", CookieManager.getContent(cookie)))
                return "sviOglasi";
        }
        return "sviOglasi";
    }

    @GetMapping("/potrazi")
    public @ResponseBody List<Oglas> potrazi(@RequestParam(required = false) Integer id,@RequestParam(required = false) String naslov,@RequestParam(required = false) String poslodavac,@RequestParam(required = false) String mesto,@RequestParam(required = false) Boolean vrsta){
        List<Oglas> oglasi = db.Daj_Oglase(id,naslov,poslodavac,mesto,vrsta);
        if(!oglasi.isEmpty()){
            System.out.println("if");
            return oglasi;
        }
        System.out.println("prosao");
        oglasi.add(new Oglas(-1, null, null, false, null, null, null, null, null));
        return oglasi; 
    }
    


    @GetMapping("/all")
    public @ResponseBody List<Oglas> all() {
        return db.sviOglasi();
    }

    @GetMapping("/signup")
    public String signup(HttpServletRequest req, HttpServletResponse res) {
        if (CookieManager.getCookie(req) != null) {
            return "redirect:/signin";
        }
        return "signup";
    }

    @PostMapping("/signup")
    public @ResponseBody List<errorCode> register(@RequestBody String body, HttpServletRequest req,
            HttpServletResponse res) {
        JSONObject js;
        List<errorCode> lista = new ArrayList<errorCode>();
        try {
            js = new JSONObject(body);
            if (body != null) {
                if (db.nadjiUser("username", js.getString("user")))
                    lista.add(new errorCode("UsernameVecPostoji"));
                if (db.nadjiUser("email", js.getString("email")))
                    lista.add(new errorCode("EmailVecPostoji"));
                if (js.getString("pass") == null)
                    lista.add(new errorCode("NeOdgovarajucaSifra"));
                if (js.getString("name") == null)
                    lista.add(new errorCode("NemaIme"));
                if (js.getString("telefon") == null)
                    lista.add(new errorCode("nemabroj"));
                if (js.getString("person") == null)
                    lista.add(new errorCode("nijeseoznacio"));

                if (lista.isEmpty()) {
                    lista.add(new errorCode("OK"));
                    db.ubaciUsera(js.getString("user"), js.getString("pass"), js.getString("name"), js.getString("email"),js.getInt("mesto"),js.getInt("telefon"),js.getBoolean("person"));
                }
                return lista;
            }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        lista.add(new errorCode("LosBody"));
        return lista;
    }

    @GetMapping("/signin")
    public String signin(HttpServletRequest req, HttpServletResponse res) {
        if (CookieManager.getCookie(req) != null)
            return "redirect:/";
        return "signin";
    }

    @PostMapping("/signin")
    public @ResponseBody List<errorCode> login(@RequestBody String body, HttpServletRequest req,
            HttpServletResponse res) {
        JSONObject js;
        List<errorCode> lista = new ArrayList<errorCode>();
        try {
            js = new JSONObject(body);
                if ( body != null) {
                if (! db.proveriSignin(js.getString("user"), js.getString("pass"),js.getBoolean("rememberMe")))
                    lista.add(new errorCode("PasswordIliUsernameNisuUredu"));
                if (lista.isEmpty()) {
                    if (js.getBoolean("rememberMe") == true)
                        CookieManager.makeCookie(req, res, js.getString("user"), true);
                    else
                        CookieManager.makeCookie(req, res, js.getString("user"), false);
                    lista.add(new errorCode("OK"));
                }
                return lista;
            }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        lista.add(new errorCode("losBody"));
        return lista;
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest req, HttpServletResponse res) {
        Cookie cookie;
        if ((cookie = CookieManager.getCookie(req)) != null)
            CookieManager.deleteCookie(req, res);
        return "redirect:/";
    }

    @GetMapping("/profil")
    public String Profil(@RequestParam(required = false) String user, HttpServletRequest req,
            HttpServletResponse res) {
        if (CookieManager.getCookie(req) == null && user == null)
            return "redirect:/";
        else
            return "profil";
    }
    @GetMapping("/profile")
    public @ResponseBody Korisnik nazivProfila(@RequestParam(required = false) Integer user,HttpServletRequest req, HttpServletResponse res){
        Cookie cookie= CookieManager.getCookie(req);
        //Korisnik k =  db.Daj_Korisnika(user);
        if (cookie == null && user == null)
            return null;
        if (cookie != null && user == null){
            Korisnik k =  db.Daj_Korisnika(db.dajId(CookieManager.getContent(cookie)));
            return k;
        }
        if(cookie!=null){
            if (db.dajId(CookieManager.getContent(cookie)) == user){
                Korisnik k =  db.Daj_Korisnika(user);
                return k;
            }
        }
        if (user != null){
            Korisnik k =  db.Daj_Korisnika(user);
            if(k!=null)
                k.password="";
            return k;
        } 
            
        return null;
    }

    @PostMapping("/update")
    public @ResponseBody String update (@RequestBody String body, HttpServletRequest req,
    HttpServletResponse res){
        JSONObject js;
        try {
            js = new JSONObject(body);
                if ( body != null) {
                db.update(js.getInt("id"),js.getString("ime"),js.getString("username"),js.getString("password"), js.getString("email"), js.getInt("mesto"), js.getString("opis"));
                return "OK";
            }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return "BAD";
    }

    @GetMapping("/cities")
    public @ResponseBody List<Mesto> gradovi(HttpServletRequest req, HttpServletResponse res){
        List<Mesto> lista = db.Daj_gradove();
        if(lista.isEmpty()){
            lista.add(new Mesto(-1,"Nema gradova"));
        }
        return lista;
    }

    @GetMapping("/tags")
    public @ResponseBody List<errorCode> tagovi(HttpServletRequest req, HttpServletResponse res){
        List<errorCode> lista = db.Daj_tagove();
        if(lista.isEmpty()){
            lista.add(new errorCode("Nema tagova"));
        }
        return lista;
    }
    @PostMapping("/prijavi")
    public @ResponseBody String prijavise(@RequestParam(required = false) Integer oglas, @RequestBody String body,HttpServletRequest req, HttpServletResponse res){
        if(CookieManager.getCookie(req)!=null){
            if(db.prijavise(db.dajId(CookieManager.getContent(CookieManager.getCookie(req))), oglas, body))
            return "OK";
        }
        return "false";
    }
    
    @PostMapping("/like")
    public @ResponseBody String lajkuj(@RequestParam(required = false) Integer oglas, @RequestBody String body,HttpServletRequest req, HttpServletResponse res) {
        if(CookieManager.getCookie(req)!=null){
            if(db.lajkuj(db.dajId(CookieManager.getContent(CookieManager.getCookie(req))), oglas, body))
            return "OK";
        }
        return "false";
    }
    @PostMapping("/oceni")
    public @ResponseBody String oceni(@RequestParam(required = false) Integer covek, @RequestBody String body,HttpServletRequest req, HttpServletResponse res){
        if(CookieManager.getCookie(req)!=null){
            if(db.lajkuj(db.dajId(CookieManager.getContent(CookieManager.getCookie(req))), covek, body))
            return "OK";
        }
        return "false";
    }
    @GetMapping("/ocena")
    public @ResponseBody String ocena(@RequestParam(required = false) Integer covek, @RequestBody String body,HttpServletRequest req, HttpServletResponse res){
        Integer ocena = -1;
        if(CookieManager.getCookie(req)!=null){
            ocena= db.dataOcena(db.dajId(CookieManager.getContent(CookieManager.getCookie(req))), covek);
            if(ocena == null){
                ocena=-1;
            }
        }
        return ocena.toString();
    }


    @GetMapping("/oglas")
        public String oglas(HttpServletRequest req, HttpServletResponse res){
            return "stranicaOglasa";
        }
    @GetMapping("/sveooglasu")
    public @ResponseBody Oglas oglasu(@RequestParam Integer id,HttpServletRequest req, HttpServletResponse res){
        Oglas oglas = db.Daj_Oglas(id);
        return oglas;
    }

    @GetMapping("/mojioglasi")
    public @ResponseBody List<Oglas> mojioglasi(@RequestParam(required = false) Integer user,HttpServletRequest req, HttpServletResponse res){
        Cookie cookie= CookieManager.getCookie(req);
        List<Oglas> k;
        if (cookie == null && user == null)
            return null;
        if (cookie != null && user == null){
            k =  db.Daj_Mojeoglase(db.dajId(CookieManager.getContent(cookie)));
        }
        else{
            k = db.Daj_Mojeoglase(user);
        }
        if(k != null){
            return k;
        }
        return null;
    }
    @GetMapping("/telefon")
    public @ResponseBody List<errorCode> telefon(@RequestParam(required = false) Integer user,HttpServletRequest req, HttpServletResponse res){
        Cookie cookie= CookieManager.getCookie(req);
        List<errorCode> k;
        if (cookie == null && user == null)
            return null;
        if (cookie != null && user == null){
            k =  db.Daj_telefone(db.dajId(CookieManager.getContent(cookie)));
        }
        else{
            k = db.Daj_telefone(user);
        }
        if(k != null){
            return k;
        }
        return null;
    }

}

