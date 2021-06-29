package com.example.Project;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {
    public static Cookie getCookie(HttpServletRequest req){//Ova funkcija trazi cookie i vraca ga ako ga nadje odnosno null ako ga ne nadje
        Cookie[] cookies = req.getCookies();
        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
              if(cookies[i].getName().compareTo("session-id")==0){
                return cookies[i];
              }
            }
        }
        return null;
    }
    public static boolean makeCookie(HttpServletRequest req,HttpServletResponse res,String name,boolean rememberMe){//Ova funkcija proverava da li cookie postoji i ako ga nema pravi novi i return true,ako ga ima returna false;
        if(getCookie(req) == null){
            Cookie cookie = new Cookie("session-id",AES256.encrypt(name));
            if(rememberMe) cookie.setMaxAge(2147483647);
            else cookie.setMaxAge(60*15);
            res.addCookie(cookie);
            return true;
        }
        return false;
    }
    public static String getContent(Cookie cookie){//Ova funkcija desifruje Cookie content
        return AES256.decrypt(cookie.getValue());
    }
    public static boolean deleteCookie(HttpServletRequest req,HttpServletResponse res){//Ova funkcija brice cookie iz memorije
        Cookie cookie = getCookie(req);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return false;
    }
}
