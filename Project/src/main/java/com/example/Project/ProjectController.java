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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {
    
    DatabaseConnector db = new DatabaseConnector();

    @GetMapping("/")
    public String home(HttpServletRequest req,HttpServletResponse res){
        Cookie cookie;
        if((cookie = CookieManager.getCookie(req)) !=null){
            if(db.nadjiUser(CookieManager.getContent(cookie)))return "LogovanHome";
        }
        return "Home";
    }
    @GetMapping("/all")
    public @ResponseBody List<Oglas> all(){
        return db.sviOglasi();
    }
    @GetMapping("/signup")
    public String signup(HttpServletRequest req,HttpServletResponse res){
        if(CookieManager.getCookie(req)!=null){
            return "redirect:/";
        }
        return "signup";
    }
    @PostMapping("/signup")
    public @ResponseBody List<errorCode> register(@RequestBody String body,HttpServletRequest req,HttpServletResponse res){
        JSONObject js;
        List<errorCode> lista = new ArrayList<errorCode>();
        if(body!=null){
            try {
                js = new JSONObject(body);
                if(db.nadjiUser("username",js.getString("username")))lista.add(new errorCode("UsernameVecPostoji"));
                if(db.nadjiUser("email", js.getString("email")))lista.add(new errorCode("EmailVecPostoji"));
                if(lista.isEmpty()){
                    lista.add(new errorCode("OK"));
                }
                return lista;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lista.add(new errorCode("LosBody"));
        return lista;
    }
    @GetMapping("/signin")
    public String signin(HttpServletRequest req,HttpServletResponse res){
        if(CookieManager.getCookie(req)!=null)return "redirect:/";
        return "signin";
    }
    @PostMapping("/signin")
    public @ResponseBody List<errorCode> login(@RequestBody String body,HttpServletRequest req,HttpServletResponse res){
        JSONObject js;
        System.out.println(body);
        List<errorCode> lista = new ArrayList<errorCode>();
        if(body!=null){
            try {
                js = new JSONObject(body);
                if(db.nadjiUser("username",js.getString("username")))lista.add(new errorCode("PasswordIliUsernameNisuUredu"));
                if(db.nadjiUser("password", js.getString("password")))lista.add(new errorCode("PasswordIliUsernameNisuUredu"));
                if(lista.isEmpty()){
                    CookieManager.makeCookie(req, res, js.getString("username"));
                    lista.add(new errorCode("OK"));
                }
                return lista;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lista.add(new errorCode("losBody"));
        return lista;
    }

    @GetMapping("/profile")
    public String necijiProfil(@RequestParam String user,HttpServletRequest req,HttpServletResponse res){
        if((user == null && CookieManager.getCookie(req) != null ) || db.dajId(CookieManager.getCookie(req).getValue()).toString() == user){
            return "Mainprofile";
        }
        return "profile";
    }
}
