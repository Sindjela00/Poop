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


//  pozoves funkciju Dajgradove kad se ucita html i on ce da popuni gradovi kao json i gradove uzimas kao gradovi[i].error
// .error jer nisam pravio posebnu klasu za ovo,mozda se promeni kasnije 

var gradovi = 0;
var tagovi = 0;

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
            document.getElementById("city-register").innerHTML += select;
            gradovi = 1;
        })
        .then(function(data) {
            var url_string = window.location.href;
            var url = new URL(url_string);
            var mesto = url.searchParams.get("mesto");
            document.getElementById("city-register").selectedIndex = mesto;
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
            } else {
                window.alert("Username ili sifra nisu ispravni.");
            }
        })
}

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function validateNumber(num) {
    return /^\d+$/.test(num);
}

function signup() {
    user = document.getElementById("username-register").value
    pass = document.getElementById("password-register").value
    ppass = document.getElementById("ppassword-register").value
    email = document.getElementById("email-register").value;
    mesto = document.getElementById("city-register").value;
    ime = document.getElementById("name-register").value;
    telefon = document.getElementById("tel-register").value;
    poslodavac = document.getElementById("poslodavac").checked;

    if ((ime == "") || (ime == null)) {
        window.alert("Unesite ime.");
        return;
    }
    if ((email == "") || (email == null)) {
        window.alert("Unesite email adresu.");
        return;
    }
    if (!validateEmail(email)) {
        window.alert("Neispravna email adresa");
        return;
    }
    if ((telefon == "") || (telefon == null)) {
        window.alert("Unesite broj telefona.");
        return;
    }
    if (!validateNumber(telefon)) {
        window.alert("Neispravan broj telefona.");
        return;
    }
    if ((pass == "") || (pass == null)) {
        window.alert("Unesite lozinku.");
        return;
    } else if ((ppass == "") || (ppass == null)) {
        window.alert("Ponovo unesite lozinku.");
        return;
    } else if (pass != ppass) {
        window.alert("Lozinke nisu iste");
        return;
    }


    var proba = '{"user":"' + user + '","pass":"' + pass + '","email":"' + email + '","mesto":' + mesto + ',"name":"' + ime + '","telefon":' + telefon + ',"person":"' + poslodavac + '"}';
    var odgovor = post("http://localhost:8080/signup", proba);
    odgovor.then(data => data.json())
        .then(response => {
            if (response[0].error == "OK") {
                window.location.replace("http://localhost:8080/")
            }
            window.alert(response[0].error);
        })

}

function oglasi() {
    var queryString = "http://localhost:8080/oglasi";
}

function selectCity(grad) {
    select = document.getElementById("city-register");
    options = select.getElementsByTagName("option");
    for (i = 0; i < options.length; i++) {
        if (options[i].childNodes[0].nodeValue == grad) {
            select.selectedIndex = i;
        }
    }
}

function skloniTelefon(i) {
    console.log(i);

    telefon = document.getElementById("profil-telefon" + i);
    dugme = document.getElementById("skloniTelefon" + i);

    document.getElementById("profil-telefon" + i).value = "";
    console.log("eeee" + document.getElementById("profil-telefon" + i).value);

    telefon.style.display = 'none';
    dugme.style.display = 'none';
}

function samoZaAdmina() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("user");
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                if (data.admin) {
                    elementi = document.getElementsByClassName("samoZaAdmina");
                    for (i = 0; i < elementi.length; i++) {
                        elementi[i].style.display = 'block';
                    }
                    if (((url_string.includes("profil")) && (id == 1)) || ((url_string.includes("profil")) && (id == null))) {
                        document.getElementById("brisanjeProfila").style.display = 'none';
                    }
                }
            }
        });
}

function posaljiMail(email) {
    location.href = "mailto:" + email;
}

var ind = 0;

function dodajTelefon(){
    ind++
    telefoni = document.getElementById("telefoni");
    stil = "style='width : 80%; float : left;'";
    btn = "<button type='button' class='btn btn-outline-primary dugmeTelefon' id='skloniTelefon" + ind + "' onclick='skloniTelefon(" + ind + ")' style='width: 15%; height: 42px; float : right; display : none;'> X </button>"
    txt = '<input type="text" id="profil-telefon' + ind + '" name="telefon" class="form-control" ' + stil + '> ' + btn + '';
    telefoni.innerHTML += txt;
}

function popuniProfil() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("user");
    var userid = "";
    var telefoni;
    var txt;

    if (id != null) {
        userid = "?user=" + id;
    }
    json = $.getJSON("http://localhost:8080/profile" + userid, function() {})
        .done(function(data) {
            if (data != null) {
                id = data.id;
                document.getElementById("profil-korisnicko-ime").innerHTML = data.username;
                document.getElementsByName("ime")[0].value = data.ime;
                selectCity(data.mesto);
                document.getElementsByName("email")[0].value = data.email;
                document.getElementsByName("opis")[0].value = data.opis;
                document.getElementsByName("lozinka1")[0].value = "";
                prosecna = (data.petice * 5 + data.cetvorke * 4 + data.trojke * 3 + data.dvojke * 2 + data.jedinice * 1) / (data.petice + data.cetvorke + data.trojke + data.dvojke + data.jedinice);
                document.getElementById("prosecna").innerHTML += prosecna;
                document.getElementById("petice").innerHTML += data.petice;
                document.getElementById("cetvorke").innerHTML += data.cetvorke;
                document.getElementById("trojke").innerHTML += data.trojke;
                document.getElementById("dvojke").innerHTML += data.dvojke;
                document.getElementById("jedinice").innerHTML += data.jedinice;
                ocena();
                lozinke = document.getElementById("sakriveno");
                document.getElementById("kontakt").onclick = function() { posaljiMail(data.email); }
            }
        });

    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                console.log(data.id);
                if (data.poslodavac) {
                    document.getElementById("imeLabel").innerHTML = "Ime kompanije";
                } else {
                    document.getElementById("imeLabel").innerHTML = "Ime i Prezime";
                }
                if (id == data.id || id == null) {
                    document.getElementById("sakriveno").style.display = 'block';
                } else if ((id != data.id) && data.id != -1) {
                    document.getElementById("oceni").style.display = 'block';
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
                    ind = i;
                    console.log(dataT[i].error);
                    telefon = dataT[i].error;
                    stil = "style='width : 80%; float : left;'";
                    btn = "";
                    if (i > 0) {
                        btn = "<button type='button' class='btn btn-outline-primary dugmeTelefon' id='skloniTelefon" + i + "' onclick='skloniTelefon(" + i + ")' style='width: 15%; height: 42px; float : right; display : none;'> X </button>"
                    }
                    else {
                        btn = "<button type='button' class='btn btn-outline-primary dugmeTelefon' id='skloniTelefon" + i + "' onclick='dodajTelefon(" + id + ")' style='width: 15%; height: 42px; float : right; display : none;'> + </button>"
                    }
                    txt += '<input type="text" id="profil-telefon' + i + '" name="telefon" value="' + telefon + '" class="form-control" ' + stil + '> ' + btn + '';
                }
            } else {
                txt = '<div class="form-group"> <label class="form-control-label">Telefon</label> <input type="text" id="profil-telefon" name="telefon" value="" class="form-control"> </div>';
            }
            telefoni.innerHTML += txt;
        });
    sakrijOdKorisnika();
    samoZaAdmina();
}

function Sacuvaj() {
    var id;
    var username;

    ime = document.getElementById("profil-ime").value;
    grad = document.getElementById("city-register").value;
    email = document.getElementById("profil-email").value;
    telefoni = document.getElementsByName("telefon");
    opis = document.getElementById("profil-opis").value;
    lozinka1 = document.getElementById("profil-lozinka1").value;
    lozinka2 = document.getElementById("profil-lozinka2").value;
    lozinka3 = document.getElementById("profil-lozinka3").value;

    json = $.getJSON("http://localhost:8080/profile", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                id = data.id;
                username = data.username;
                if (lozinka2 != lozinka3) {
                    window.alert("Sifre nisu iste.");
                } else {
                    oldPassword = lozinka1;
                    newPassword = lozinka2;
                    string = '{"id":' + id + ',"ime":"' + ime + '","username":"' + username + '","email":"' + email + '","password1":"' + oldPassword + '","password2":"' + newPassword + '","opis":"' + opis + '","mesto":' + grad + '}';
                    fetch("http://localhost:8080/update", { method: "POST", body: string })
                        .then(function() {
                            sacuvajTelefone();
                        });
                }
            }
        });
}

function otkaziPromene() {
    document.getElementById("lozinke").style.display = 'none';
    document.getElementsByName("lozinka2")[0].value = '';
    document.getElementsByName("lozinka3")[0].value = '';
    document.getElementById("promeniLozinku").style.display = 'inline';
}


function dodajOglase() {
    txt = "";
    lista = document.getElementById("lista");

}

function Dajtagove() {
    json = $.getJSON("http://localhost:8080/tags", function() {
            console.log("zavrsio");
        })
        .done(function(data) {
            var select = "";
            for (i = 0; i < data.length; i++) {
                if (data[i].podkategorija == "") {
                    select += "<option value='" + data[i].id + "'>---- " + data[i].kategorija + " ----</option>";
                } else {
                    select += "<option value='" + data[i].id + "'>" + data[i].podkategorija + "</option>";
                }
            }
            select += "";
            document.getElementById("tagovi").innerHTML = select;
            tagovi = 1;
        })
        .then(function(data) {
            var url_string = window.location.href;
            var url = new URL(url_string);
            var tag = url.searchParams.get("tag");
            if (tag != null) {
                select = document.getElementById("tagovi");
                options = select.getElementsByTagName("option")
                console.log(options);
                for (i = 0; i < options.length; i++) {
                    if (options[i].value == tag) {
                        select.selectedIndex = i;

                    }
                }
            }
        });
}

var url_string = window.location.href;
var url = new URL(url_string);
var tag = url.searchParams.get("tag");
var mesto = url.searchParams.get("mesto");

function promeniurl() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var tag = url.searchParams.get("tag");
    var mesto = url.searchParams.get("mesto");
    selectgrad = document.getElementById("city-register").selectedIndex;
    selecttag = document.getElementById("tagovi").value;
    var novurl = "http://localhost:8080/oglasi?tag=" + selecttag + "&mesto=" + selectgrad;
    window.history.replaceState("aaaa", "bbbb", novurl);
}




function ucitajOglase() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var tag = url.searchParams.get("tag");
    var mesto = url.searchParams.get("mesto");
    var filter = document.getElementById("filter");
    if (tag == null) tag = 0;
    if (mesto == null) mesto = 0;
    json = $.getJSON("http://localhost:8080/potrazi?tag=" + tag + "&mesto=" + mesto, function() {
            console.log("zavrsio");
        })
        .done(function(ads) {
            var brOglasa = ads.length;
            if (brOglasa == 0) {
                document.getElementById("oglasi").innerHTML = "<h5 class='mt-0 font-weight-bold mb-2' style='color:#1abc9c'> Nema rezultata pretrage </h5>";
                return;
            }
            contentAds = "<ul class='list-group shadow' id='lista' style='background-color: #1abc9c;'>";
            for (i = 0; i < brOglasa; i++) {
                id = ads[i].id;
                naslov = ads[i].naslov;
                grad = ads[i].mesto;
                poslodavac = ads[i].ime;
                plata = ads[i].plata;
                opis = ads[i].opis;
                likes = ads[i].likes;
                dislikes = ads[i].dislikes;
                radniOdnos = ads[i].tip;
                if (radniOdnos == true)
                    radniOdnos = "na neodređeno vreme";
                else
                    radniOdnos = "na određeno vreme";

                console.log(grad.toLowerCase());

                if ((naslov.toLowerCase().includes(filter.value.toLowerCase())) || (grad.toLowerCase().includes(filter.value.toLowerCase())) || (opis.toLowerCase().includes(filter.value.toLowerCase()))) {
                    contentAds += "<li class='list-group-item'>" +
                        "<div class='media align-items-lg-center flex-column flex-lg-row p-3'>" +
                        "<div class='media-body order-2 order-lg-1'>" +
                        "<div style='float: left; width:85%'>" +
                        "<h5 class='mt-0 font-weight-bold mb-2' style='color:#1abc9c'>" + naslov + "</h5>" +
                        "<p class='font-italic text-muted mb-0 small' style='font-size: 16px;'><i class='fas fa-map-marker-alt' style='padding-top: 10px; padding-right: 5px; height:30px; width: 30px; '></i>" + grad + "</p>" +
                        "<p class='font-italic text-muted mb-0 small' style='font-size: 16px;'><i class='fas fa-address-card' style='padding-top: 10px; padding-right: 5px; height:30px; width: 30px; '></i>" + poslodavac + "</p>" +
                        "<p class='font-italic text-muted mb-0 small' style='font-size: 16px;'><i class='fas fa-file-contract' style='padding-top: 10px; padding-right: 5px; height:30px; width: 30px; '></i> Radni odnos " + radniOdnos + "</p>" +
                        "</div>" +
                        "<div class='d-flex align-items-center justify-content-between mt-1'>" +
                        "<div class='lajkDislajk' style='float: right; margin-left: 35px; margin-bottom: 10px; '>" +
                        "<span class='fas fa-thumbs-up' style='color : #0DB8DE;'> </span>" + "<span style='margin: 0px 50px 0 5px; color : #0DB8DE;'>" + likes + "</span>" +
                        "<span class='fas fa-thumbs-down' style='color : #0DB8DE'> </span>" + "<span style='margin-left: 5px; color : #0DB8DE;'>" + dislikes + "</span>" +
                        "</div>" +
                        "</div>" +
                        "<button id=dugmeDetalji class='btn btn-outline-primary' style='float: right; margin-right: 20px;' onclick='prebaciNaOglas(" + id +
                        ")'> Detaljnije </button>" +
                        "<div>" +
                        "<button onclick='izbrisiOglas(" + id + ");' class='btn btn-outline-primary samoZaAdmina' style='position: relative; float: right; bottom:-10px; '><i class='fas fa-trash'></i></button>" +
                        "</div>" +
                        "</div>" +
                        "</li>"
                }
            }
            contentAds += "</ul>";
            document.getElementById("oglasi").innerHTML = contentAds;
            samoZaAdmina();
        });
}


function ucitajdetaljno() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("id");

    json = $.getJSON("http://localhost:8080/sveooglasu?id=" + id, function() {
            console.log("zavrsio");
        })
        .done(function(ads) {
            id = ads.id;
            naslov = ads.naslov;
            grad = ads.mesto;
            poslodavac = ads.ime;
            plata = ads.plata;
            opis = ads.opis;
            likes = ads.likes;
            dislikes = ads.dislikes;
            radniOdnos = ads.tip;
            if (radniOdnos == true)
                radniOdnos = "na neodređeno vreme";
            else
                radniOdnos = "na određeno vreme";
            var contentAd =
                "<div>" +
                "<button onclick='izbrisiOglas(" + id + ");' class='btn btn-outline-primary samoZaAdmina' style='position: relative; float: right; bottom:-10px; '><i class='fas fa-trash'></i></button>" +
                "</div>" +

                "<div id='osnovniPodaci'>" +
                "<h3 id='imgOglasa' style='text-align: center;margin: 0; padding: 0;'> <span>Naziv oglasa: " + naslov + "</span></h3>" +
                "<br>" +
                "<div id='oglasRowLeft' style='float : left; width : 50%; margin-bottom : 30px;'>" +
                "<a href='http://localhost:8080/covek?id=" + ads.id + "'>" +
                "<h5 id='poslodavac' style='text-decoration: none;'><i class='fas fa-building'></i><span>Poslodavac: " + poslodavac + "</span></h5></a><br>" +
                "<p id='Lokacija'> <span><i class='fas fa-map-marker-alt'></i> Lokacija: " + grad + " </p></span>" +
                "<p id='radnoVreme'><span><i class='fas fa-clock'></i> Radni odnos: " + radniOdnos + "</p></span>" +
                "<span><i class='fas fa-coins'></i> Plata :</span><span id='plata'> " + plata + " </span>" +
                "</div>" +
                "<br>" +
                "<div id='oglasRowRight' style='float : right; width : 50%;'>" +
                "<div class='lajkDislajk' style='display: flex; float : right; width : 298px;'>" +
                "<span style='width : 145px;'> <span class='fas fa-thumbs-up' style='color : #1abc9c;'> </span>" + "<span style='margin: 0px 50px 0 5px; color : #1abc9c;'>" + likes + "</span></span>" +
                "<span style='width : 145px;'> <span class='fas fa-thumbs-down' style='color : #1abc9c'> </span>" + "<span style='margin-left: 5px; color : #1abc9c;'>" + dislikes + "</span></span>" +
                "</div>" +
                "<br><br>" +
                "<div id='LikeDislike' class='sakrij' style='display : none; float : right;'>" +
                "<button id='like' onclick='lajkuj(this.id);' class=' btn btn-outline-primary ' style='padding: 5px 10px;margin: 0px 5px; border-color : #1abc9c;'><i class='fas fa-thumbs-up '></i> Sviđa mi se</button>" +
                "<button id='dislike' onclick='lajkuj(this.id);' class=' btn btn-outline-primary ' style='padding: 5px 10px;margin: 0px 5px; border-color : #1abc9c;'><i class='fas fa-thumbs-down '></i> Ne sviđa mi se </button>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<br><br>" +
                "<div id='opisPoslaDiv' style='clear : both;'>" +
                "<h4><span style='text-align: center;'>Opis posla: <br></span></h4>" +
                "<p id='opisPoslaTekst'>" + opis + "</p>"
            "</div>"
            document.getElementById("stranicaOglasa").innerHTML = contentAd;
        })
        .then(function() {
            lajkovao();
        });

    ucitajPrijavljene();
    sakrijOdKorisnika();
    sakrijOdPoslodavca();
    sakrij();
    samoZaAdmina();
}

function ucitajPrijavljene() {
    field = document.getElementById("prijavljeniKorisnici");
    url_string = window.location.href;
    url = new URL(url_string);
    oglasID = url.searchParams.get("id");
    txt = "";

    json = $.getJSON("http://localhost:8080/prijavljeni?id=" + oglasID, function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                field.style.display = 'block';
                for (i = 0; i < data.length; i++) {
                    txt +=
                        "<div class='prijavljenKorisnik'>" +
                        "<h5>" + data[i].ime + "</h5>" +
                        "<textarea> " + data[i].opis + " </textarea> <br>" +
                        "<button type='button' class='btn btn-outline-primary ' onclick='otvoriProfil(" + data[i].id + ")'>Poseti profil</button>" +
                        "<button type='button' class='btn btn-outline-primary samoZaAdmina' onclick='otkaziprijavu(" + data[i].id + "," + oglasID + ");'><i class='fas fa-trash'></i></button>" +
                        "</div>";
                }
                field.innerHTML = txt;
            }
        });
    samoZaAdmina();
}

function sakrij() {
    var sakriveno = document.getElementsByClassName("sakrij");
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                if (!data.prijavljen) {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'none';
                    }
                } else {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'block';
                    }
                }
            }
        });
}

function sakrijOdKorisnika() {
    var sakriveno = document.getElementsByClassName("sakrijOdKorisnika");
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                if ((!data.prijavljen) || (!data.poslodavac)) {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'none';
                    }
                } else {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'block';
                    }
                }
            }
        });
}

function sakrijOdPoslodavca() {
    var sakriveno = document.getElementsByClassName("sakrijOdPoslodavca");
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                if (!data.prijavljen || data.poslodavac == true) {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'none';
                    }
                } else {
                    for (i = 0; i < sakriveno.length; i++) {
                        sakriveno[i].style.display = 'block';
                    }
                }
            }
        });
}

function prebaciNaOglas(id) {
    window.location.replace("http://localhost:8080/oglas?id=" + id);
}

function provera() {
    json = $.getJSON("http://localhost:8080/login", function() {
            console.log("zavrsio");
        })
        .done(function(data) {
            data.prijavljen;
            data.poslodavac;
            data.admin;
        });
}

function filtrirajOglase() {
    var selectgrad = document.getElementById("city-register").selectedIndex;
    var selecttag = document.getElementById("tagovi").value;
    /*var novurl = "http://localhost:8080/oglasi?tag=" + selecttag + "&mesto=" + selectgrad;
    window.location.replace(novurl);*/
    filtriraj(selectgrad, selecttag)
}

function filtriraj(selectgrad, selecttag) {
    var novurl = "http://localhost:8080/oglasi?tag=" + selecttag + "&mesto=" + selectgrad;
    window.location.replace(novurl);
}

function usmeri(id) {
    var novurl = "http://localhost:8080/oglasi?tag=" + id + "&mesto=0";
    window.location.replace(novurl);
}

function srediNavbar() {
    var login = document.getElementById("navLogin");
    var profil = document.getElementById("navProfil");
    var userID;
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            console.log(data);
            if (data != null) {
                if (data.poslodavac) {
                    document.getElementById("navKreiraj").style.display = 'block';
                }
                url_string = window.location.href;
                url = new URL(url_string);
                if (url_string.includes("profil")) {
                    userID = url.searchParams.get("user");
                    console.log(userID, data.id);
                    if(data.prijavljen){
                        login.href = "http://localhost:8080/signout";
                        login.innerHTML = "Odjavi se";
                        if(userID == data.id || userID == null){
                            profil.style.display = 'none';
                            dugmici = document.getElementsByClassName("dugmeTelefon");
                            for (i = 0; i < dugmici.length; i++) {
                                dugmici[i].style.display = 'block';
                            }
                        }
                        else {
                            profil.style.display = 'block';
                        }
                    }
                    else {
                        login.href = "http://localhost:8080/signin";
                        login.innerHTML = "Prijavi se";
                        profil.style.display = 'none';
                    }
                }
                else if (!data.prijavljen) {
                    login.href = "http://localhost:8080/signin";
                    login.innerHTML = "Prijavi se";
                    profil.style.display = 'none';
                }
                else {
                    login.href = "http://localhost:8080/signout";
                    login.innerHTML = "Odjavi se";
                    profil.style.display = 'block';
                }
            }
        });
}

function dodajoglas() {
    naziv = document.getElementById("naziv-oglasa").value;
    var tip;
    if (document.getElementById("poslodavac").checked) {
        tip = true;
    } else {
        tip = false;
    }
    grad = document.getElementById("city-register").value;
    tag = document.getElementById("tagovi").value;
    plata = document.getElementById("plata").value;
    opis = document.getElementById("novioglas-opis").value;

    console.log(tag);

    if ((naziv == "") || (naziv == null)) {
        window.alert("Unesite naziv oglasa.");
        return;
    }
    if (tag == 0) {
        window.alert("Izaberite kategoriju posla.");
        return;
    }
    if ((plata == "") || (plata == null)) {
        window.alert("Unesite pocetnu platu.");
        return;
    }
    if (!validateNumber(plata)) {
        window.alert("Neispravano uneta plata.");
        return;
    }
    if ((opis == "") || (opis == null)) {
        window.alert("Unesite opis oglasa.");
        return;
    }


    string = '{"naslov":"' + naziv + '","tip":' + tip + ',"plata":' + plata + ',"opis":"' + opis + '","mesto":' + grad + ',"tag":' + tag + '}';
    fetch("http://localhost:8080/napravioglas", { method: "POST", body: string })
        .then(function() {
            window.location.replace("http://localhost:8080/profil");
        });
}

function dodajcv() {
    json = $.getJSON("http://localhost:8080/cv", function() {})
        .done(function(data) {
            document.getElementById("unesiCV").innerHTML = data.error;
        });
}

function promeniLozinku() {
    dugme = document.getElementById("promeniLozinku");
    lozinke = document.getElementById("lozinke");

    dugme.style.display = 'none';
    lozinke.style.display = 'block';
}

function oglasiPrijave() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("user");
    if (id != null) {
        id = "?id=" + id
    } else id = "";
    json = $.getJSON("http://localhost:8080/poslodavac" + id, function() {})
        .done(function(data) {
            if (data != null) {
                if (data.poslodavac) {
                    mojiOglasi();
                } else {
                    mojePrijave();
                }
            }
        });
}

function sakrijOdDrugih() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("user");
    json = $.getJSON("http://localhost:8080/login", function() {})
        .done(function(data) {
            if (data != null) {
                if ((data.prijavljen && ((id == data.id) || (id == null))) || data.admin) {
                    elementi = document.getElementsByClassName("sakrijOdDrugih");
                    for (i = 0; i < elementi.length; i++) {
                        elementi[i].style.display = 'block';
                    }
                }
            }
        });
}

function otvoriOglas(id) {
    location.href = 'http://localhost:8080/oglas?id=' + id;
}

function izbrisiOglas(id) {
    location.href = 'http://localhost:8080/obrisioglas?id=' + id;
}

function otkaziPrijavu(id) {
    post('http://localhost:8080/izbaci?id=' + id, null);
}

function otkaziprijavu(covek, id) {
    post('http://localhost:8080/izbaci?idcoveka=' + covek + '&id=' + id, null);
}

function otvoriProfil(id) {
    location.href = 'http://localhost:8080/profil?user=' + id;
}

function mojiOglasi() {
    field = document.getElementById("profile-oglasi");
    var sakrij;
    txt = "";
    string = "";
    url_string = window.location.href;
    url = new URL(url_string);
    userID = url.searchParams.get("user");
    if (userID == null) {
        string = "http://localhost:8080/mojioglasi";
        sakrij = false;
    } else {
        string = "http://localhost:8080/mojioglasi?user=" + userID;
        sakrij = true;
    }

    json = $.getJSON(string, function() {})
        .done(function(data) {
            if (data != null) {
                for (i = 0; i < data.length; i++) {
                    naslov = data[i].naslov;
                    lokacija = data[i].mesto;
                    id = data[i].id;
                    plata = data[i].plata;
                    txt +=
                        "<div class='profile-oglas' style='padding: 5px 10px; background-color:#e3fffb ' >" +
                        "<button type='button' onclick='izbrisiOglas(" + id + ");' class='btn btn-outline-primary sakrijOdDrugih' style='float:right; display : none;'><i class='fas fa-trash'></i></button>" +
                        "<div style='position:relative; margin-bottom: 5px; height: 70%; width : 85%;'>" +
                        "<h5> Naziv oglasa: " + naslov + "</h5>" +
                        "<p style='clear : both;'><i class='fas fa-map-marker-alt'></i> <b>Lokacija:</b> " + lokacija + "<br>" +
                        "<i class='fas fa-coins'></i><b> Plata : </b>" + plata + " </p>" +
                        "</div>" +
                        "<button type='button' onclick='otvoriOglas(" + id + ");' class='btn btn-outline-primary' style='width : 100%; padding: auto; margin: auto;'>Detaljnije</button>" +
                        /*"<button type='button' onclick='izbrisiOglas(" + id + ");' class='btn btn-outline-primary sakrijOdKorisnika' style='float : right; width : 48%;'>Izbriši</button>" +*/
                        "</div>";
                }
                field.innerHTML = txt;
                if (sakrij) {
                    dugmici = document.getElementsByClassName("sakrijOdKorisnika");
                    document.getElementById("profil-ime").disabled = true;
                    document.getElementById("city-register").disabled = true;
                    document.getElementById("profil-email").disabled = true;
                    document.getElementById("profil-opis").disabled = true;
                    telefoni = document.getElementsByName("telefon");
                    for (i = 0; i < telefoni.length; i++) {
                        telefoni[i].disabled = true;
                    }
                    for (i = 0; i < dugmici.length; i++) {
                        dugmici[i].style.display = 'none';
                    }
                }
            }
        });
    sakrijOdDrugih();
}

function mojePrijave() {
    field = document.getElementById("profile-oglasi");
    txt = "";

    json = $.getJSON("http://localhost:8080/mojeprijave", function() {})
        .done(function(data) {
            if (data != null) {
                for (i = 0; i < data.length; i++) {
                    naslov = data[i].podkategorija;
                    poslodavac = data[i].kategorija;
                    //lokacija = data[i].mesto;
                    id = data[i].id;
                    txt +=
                        "<div class='profile-oglas'>" +
                        "<h5> Naziv oglasa: " + naslov + "</h5>" +
                        "<a href='http://localhost:8080/covek?id=" + id + "'>" +
                        "<p> Poslodavac: " + poslodavac + "</p>" +
                        "</a>" +
                        "<button type='button' onclick='otvoriOglas(" + id + ");' class='btn btn-outline-primary' style='float : left; width : 48%;'>Detaljnije</button>" +
                        "<button type='button' onclick='otkaziPrijavu(" + id + ");' class='btn btn-outline-primary' style='float : right; width : 48%;'>Otkaži prijavu</button>" +
                        "</div>";
                }
                field.innerHTML = txt;
            }
        });
}

function poslodavci() {
    field = document.getElementById("poslodavci-box");
    txt = "<h1 class='dugme'> Lista poslodavaca : </h1>";
    json = $.getJSON("http://localhost:8080/poslodavcii", function() {})
        .done(function(data) {
            if (data != null) {
                for (i = 0; i < data.length; i++) {
                    ime = data[i].ime;
                    lokacija = data[i].mesto;
                    opis = data[i].opis
                    id = data[i].id;
                    txt +=
                        "<div class='poslodavac'>" +
                        "<span style='float : left;'><h5> Poslodavac : " + ime + "</h5></span>" +
                        "<span style='float : right;'> <i class='fas fa-map-marker-alt'></i> Lokacija: " + lokacija + "</span>" +
                        "<p style='clear : both;'> <b> Opis : " + opis + " </b></p>" +
                        "<br><br>" +
                        "<button type='button' onclick='otvoriProfil(" + id + ");' class='dugme btn btn-outline-primary' style='float : left; width : 48%;'>Detaljnije</button>" +
                        "<div class='col-lg-12 samoZaAdmina' style='width: 100px; position: absolute; padding-right : 15px; bottom : 8px;'>" +
                        "<button type='button' onclick='izbacipos(" + id + ")'class='btn btn-outline-primary sakrijOdKorisnika kanta'><i class='fas fa-trash'></i></button>" +
                        "</div>" +
                        "</div>";
                }
                field.innerHTML = txt;
                sakrijOdKorisnika();
                samoZaAdmina();
            }
        });
}

function lajkuj(lajk) {
    dugme = document.getElementById(lajk);
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("id");
    if (dugme.classList.contains("active")) {
        lajk = "izbrisi";
    }
    fetch("http://localhost:8080/like?id=" + ID + "&lajk=" + lajk, { method: "POST", body: null })
        .then(function() {
            window.location.replace(window.location.href);

        });
    lajkovao();
}

function lajkovao() {
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("id");
    dugme = document.getElementById("like");
    json = $.getJSON("http://localhost:8080/lajkovao?id=" + ID, function() {})
        .done(function(data) {
            if (data != null) {
                if (data.error == "nista") {
                    return null;
                } else {
                    document.getElementById(data.error).classList.add("active");
                }
            }
        });
}

function prijavise() {
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("id");
    post("http://localhost:8080/prijavi?id=" + ID, document.getElementById("unesiCV").value);
}

function izbrisiKorisnika(){
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("user");
    izbacipos(ID);
}

function izbacipos(id) {
    fetch("http://localhost:8080/izbrisiposlodavca?id=" + id, { method: "POST", body: null })
        .then(function() {
            window.location.replace("http://localhost:8080/poslodavci");
        });
}

function oceni() {
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("user");

    fetch("http://localhost:8080/oceni?id=" + ID + "&ocena=" + document.getElementById("izaberiOcenu").value, { method: "POST", body: null })
        .then(function() {
            window.location.replace(window.location.href);
        });
}



function ocena() {
    url_string = window.location.href;
    url = new URL(url_string);
    ID = url.searchParams.get("user");

    json = $.getJSON("http://localhost:8080/ocena?id=" + ID, function() {})
        .done(function(data) {
            if (data != null) {
                if (data.error == "-1") {
                    return null;
                } else {
                    document.getElementById("izaberiOcenu").innerHTML += "<option value='-1'>Izbrisi ocenu</option>";
                    document.getElementById("izaberiOcenu").selectedIndex = 5 - data.error;
                }
            }
        });

}

function sacuvajTelefone() {
    telefoni = document.getElementById("telefoni").getElementsByTagName("input");
    string = "{'telefoni':["
    for (i = 0; i < telefoni.length; i++) {
        if (telefoni[i].value != "") {
            string += "'" + telefoni[i].value + "'";
            if (i + 1 == telefoni.length) string += ",";
        }
    }
    string += "]}"
    fetch("http://localhost:8080/izmenitelefone", { method: "POST", body: string })
        .then(function() {
            window.location.replace(window.location.href);
        });
}