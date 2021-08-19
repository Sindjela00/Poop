/*napravi oko 30ak gradova,10ak korisnika i 10ak oglasa,jedan poslodavac moze da ima vise oglasa*/
INSERT INTO MESTO(ime) values("Neko mesto");
INSERT INTO KORISNIK(Ime,Username,Password,Email,idmesta,Poslodavac,Admin) values ("admin","admin","admin","admin@admin.admin",1,true,false); /*Poslodavac menjas u zavisnosti da li moze da postavlja oglas,admin uvek false --*/
insert into oglas(idCoveka,Naslov,Tip,Plata,Opis,Mesto) values (1,"Test2",false,20,"ovo je proba1",1);