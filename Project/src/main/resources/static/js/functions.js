/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var loadedCities = false;
var loadedJobs = false;
var loadedAds = false;
var cities;
var jobsCat;
var ads;
var data = {}
data.table = []

function loadHomePage() {
    loadCitiesFromJSON();
    loadJobsFromJSON();
    loadAdsJSON();
}

function loadLoginPage() {
    loadHeader();
}

function loadSignInPage() {
    loadHeader();
}

function loadHeader() {
    document.getElementById("header").innerHTML = "<nav class='navbar navbar-expand-lg'>" +
        "<div class='container'>" +
        "<a class='navbar-brand text-white' href='home.html'><i class='fas fa-user-md fa-lg mr-2'></i>Lako do posla</a>" +
        "<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#nvbCollapse' aria-controls='nvbCollapse'>" +
        "    <span class='navbar-toggler-icon'></span>" +
        "</button>" +
        "<div class='collapse navbar-collapse' id='nvbCollapse'>" +
        "    <ul class='navbar-nav ml-auto'>" +
        "       <li class='nav-item'>" +
        "           <a class='nav-link' href='#'>Features</a>" +
        "       </li>" +
        "       <li class='nav-item pl-1'>" +
        "            <a class='nav-link' href='signin.html'><i class='fa fa-user-plus fa-fw mr-1'></i>Registruj se</a>" +
        "        </li>" +
        "        <li class='nav-item pl-1'>" +
        "            <a class='nav-link' href='login.html'><i class='fas fa-sign-in-alt fa-fw mr-1'></i>Prijavi se</a>" +
        "        </li>" +
        "    </ul>" +
        "</div>" +
        " </div>" +
        "</nav>";
}

function loadCitiesFromJSON() {
    let path = "files/gradovi.json";
    jsonCities = new XMLHttpRequest();
    jsonCities.onreadystatechange = function() {
        if (jsonCities.readyState == 4 && jsonCities.status == 200) {
            jsonCities = jsonCities.responseText;
            loadCities();
        }
    };
    jsonCities.open("GET", path, true);
    jsonCities.send();
}

function loadJobsFromJSON() {
    let path = "files/oblasti-rada.json";
    jsonJobsCat = new XMLHttpRequest();
    jsonJobsCat.onreadystatechange = function() {
        if (jsonJobsCat.readyState == 4 && jsonJobsCat.status == 200) {
            jsonJobsCat = jsonJobsCat.responseText;
            loadJobs();
        }
    };
    jsonJobsCat.open("GET", path, true);
    jsonJobsCat.send();
}

function loadCities() {
    if (loadedCities == true)
        return;
    loadedCities = true;
    cities = JSON.parse(jsonCities)["cities"];
    let contentCities = "<option> Prikaži sve </option>";
    for (i = 0; i < cities.length; i++) {
        city = cities[i].city;
        contentCities += "<option >" + city + "</option>";
    }
    document.getElementById("grad").innerHTML = contentCities;
}

function loadJobs() {
    if (loadedJobs == true)
        return;
    loadedJobsCat = true;
    jobsCat = JSON.parse(jsonJobsCat)["oblasti"];
    let contentJobsCat = "<option> Prikaži sve </option>";
    for (i = 0; i < jobsCat.length; i++) {
        jobCat = jobsCat[i].oblast;
        contentJobsCat += "<option>" + jobCat + "</option>";
    }
    document.getElementById("radnaOblast").innerHTML = contentJobsCat;
}

function loadAdsJSON() {
    let path = "files/oglasi.json";
    jsonAds = new XMLHttpRequest();
    jsonAds.onreadystatechange = function() {
        if (jsonAds.readyState == 4 && jsonAds.status == 200) {
            jsonAds = jsonAds.responseText;
            loadAds();
        }
    };
    jsonAds.open("GET", path, true);
    jsonAds.send();
}

function loadAds() {
    ads = JSON.parse(jsonAds)["oglasi"];
    let contentAds = "";
    for (i = 0; i < ads.length; i++) {
        naslov = ads[i].naslov;
        oblast = ads[i].oblast;
        grad = ads[i].grad;
        poslodavac = ads[i].poslodavac;
        plata = ads[i].plata;
        zaposlenje = ads[i].radniOdnos;
        vreme = ads[i].radnoVreme;
        opis = ads[i].opis;
        if (checkText(poslodavac) && checkCity(grad) && checkJobCat(oblast) && checkWorkTime(vreme) && checkTypeWork(zaposlenje)) {
            contentAds += "<div class='card col-md-5 oglas '>" +
                "<div class='card-header'>Kategorija: " + oblast + "</div>" +
                " <div class='card-body' style='background-color: #9999ff'>" +
                " <h5 class='card-title'>" + naslov + "</h5>" +
                " <h6 class='card-subtitle mb-2'><i class='fas fa-briefcase'></i> " + poslodavac + "</h6>" +
                " <h6 class='card-subtitle mb-2'><i class = 'fas fa-map-marker-alt' ></i> " + grad + "</h6>" +
                " <h6 class='card-subtitle mb-2'><i class='fas fa-coins'></i> " + plata + "</h6>" +
                " <h6 class='card-subtitle mb-2'><i class='fas fa-file-signature'></i> " + zaposlenje + "</h6>" +
                " <h6 class='card-subtitle mb-2'><i class='far fa-clock'></i> " + vreme + "</h6>" +
                " <a href='#' class='btn'>Pogledaj više</a>" +
                " </div></div>";
        }
    }
    document.getElementById("ads").innerHTML = contentAds;
}

function checkText(poslodavac) {
    let text = document.getElementById("poslodavac").value;
    let pattern = new RegExp(text);
    if (text == "")
        return true;
    if (pattern.test(poslodavac))
        return true;
    return false;
}

function checkWorkTime(vreme) {
    let selectedTime = document.getElementById('radnoVreme');
    if (selectedTime.selectedIndex == 0)
        return true;
    if (selectedTime.value == vreme)
        return true;
    return false;
}

function checkJobCat(oblast) {
    let selectedCat = document.getElementById('radnaOblast');
    if (selectedCat.selectedIndex == 0)
        return true;
    if (selectedCat.value == oblast)
        return true;
    return false;
}

function checkTypeWork(zaposlenje) {
    let selectedType = document.getElementById('radniOdnos');
    if (selectedType.selectedIndex == 0)
        return true;
    if (selectedType.value == zaposlenje)
        return true;
    return false;
}

function checkCity(grad) {
    let selectedCity = document.getElementById('grad');
    if (selectedCity.selectedIndex == 0)
        return true;
    if (selectedCity.value == grad)
        return true;
    return false;
}

$(document).ready(function() {
    json = $.getJSON("http://localhost:8080/cities", function() {
            console.log("zavrsio");
        })
        .done(function(data) {
            table = "<table>";
            for (let i = 0; i < data.length; i++) {
                table += "<tr><td>" + data[i].error + "</td></tr>";
            }
            table += "</table>";
        });
});

//  pozoves funkciju Dajgradove kad se ucita html i on ce da popuni gradovi kao json i gradove uzimas kao gradovi[i].error
// .error jer nisam pravio posebnu klasu za ovo,mozda se promeni kasnije 

var gradovi;

function Dajgradove() {
    json = $.getJSON("http://localhost:8080/cities", function() {
            console.log("zavrsio");
        })
        .done(function(data) {
            var select = "";
            for (i = 0; i < data.length; i++) {
                select += "<option value='" + data[i].id + "'>" + data[i].naziv + "</option>";
            }
            select += "";
            document.getElementById("profil-grad").innerHTML = select;
        });
}



function uradi() {

    var aaaa = '{"count":100,"ime":"' + document.getElementById("tekst").value + '"}';
    post("http://localhost:8080/add", aaaa);
}

function post(url, data) {
    return fetch(url, { method: "POST", body: data });
}


function login() {
    var proba = '{"user":"' + document.getElementById("user").value + '","pass":"' + document.getElementById("pass").value + '","rememberMe":' + document.getElementById("remember-me").checked + '}';
    var odgovor = post("http://localhost:8080/signin", proba);
    odgovor.then(data => data.json())
        .then(response => {
            if (response[0].error == "OK") {
                window.location.replace("http://localhost:8080/")
            }
            console.log(response[0].error);
        })
}

function signup() {
    var proba = '{"user":"' + document.getElementById("username-register").value + '","pass":"' + document.getElementById("password-register").value + '","email":"' + document.getElementById("email-register").value + '","mesto":' + document.getElementById("city-register").value + ',"name":"' + document.getElementById("name-register").value + '","telefon":' + document.getElementById("tel-register").value + ',"person":"' + document.getElementById("poslodavac").checked + '"}';
    var odgovor = post("http://localhost:8080/signup", proba);
    odgovor.then(data => data.json())
        .then(response => {
            if (response[0].error == "OK") {
                window.location.replace("http://localhost:8080/")
            }
            console.log(response[0].error);
        })
}

function oglasi() {
    var queryString = "http://localhost:8080/oglasi";
}

function selectCity(grad) {
    select = document.getElementById("profil-grad");
    options = select.getElementsByTagName("option");
    for (i = 0; i < options.length; i++){
        if(options[i].childNodes[0].nodeValue == grad){
            select.selectedIndex = i;
        }
    }
}

function popuniProfil() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("user");
    var userid;
    var telefoni;
    var txt;

    if (id != null) {
        userid = "?user=" + id;
    }

    console.log("zavrsio");
    json = $.getJSON("http://localhost:8080/profile" + userid, function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                document.getElementById("profil-korisnicko-ime").innerHTML = data.username;
                document.getElementsByName("ime")[0].value = data.ime;
                selectCity(data.mesto);
                document.getElementsByName("email")[0].value = data.email;
                document.getElementsByName("opis")[0].value = data.opis;
                document.getElementsByName("lozinka1")[0].value = data.password;
                
                lozinke = document.getElementById("sakriveno");
                if(data.password == "" || data.password == null) {
                    lozinke.style.display = 'none';
                }
            }
        });

    json = $.getJSON("http://localhost:8080/telefon" + userid, function() {
            console.log("zavrsio");
        })
        .done(function(dataT) {
        if (dataT != null) {
            telefoni = document.getElementById("telefoni");
            txt = "";
            for (i = 0; i < dataT.length; i++) {
                telefon = dataT[i].error;
                txt += '<div class="form-group"> <label class="form-control-label">Telefon</label> <input type="text" id="profil-telefon' + i + '" name="telefon' + i + '" value="' + telefon + '" class="form-control"> </div>';
            }
        }
        else {
            txt = '<div class="form-group"> <label class="form-control-label">Telefon</label> <input type="text" id="profil-telefon" name="telefon" value="" class="form-control"> </div>';
        }
        telefoni.innerHTML = txt;
    });
}

function Sacuvaj(){
    username = document.getElementById("profil-korisnicko-ime").value;
    ime = document.getElementsByName("ime")[0].value;
    grad = document.getElementById("profil-grad").value;
    email = document.getElementsByName("email")[0].value;
    //telefon = document.getElementsByName("telefon")[0];
    opis = document.getElementsByName("opis")[0].value;
    lozinka1 = document.getElementsByName("lozinka1")[0].value;
    lozinka2 = document.getElementsByName("lozinka2")[0].value;
    lozinka3 = document.getElementsByName("lozinka3")[0].value;

    // Provera da li je se podaci razlikuju sa onima u bazi i onda se azurira
}

function dodajOglase() {
    txt = "";
    lista = document.getElementById("lista");
    
}