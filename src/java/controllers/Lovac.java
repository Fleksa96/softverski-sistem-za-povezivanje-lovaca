/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Drustvo;
import beans.Korisnik;
import beans.Vest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import util.SessionUtils;
import util.dao.KorisnickaStranaDAO;
import util.dao.LovacDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@Named(value = "lovac")
@javax.faces.view.ViewScoped
public class Lovac implements Serializable {

    private List<Vest> vesti;
    private List<UploadedFile> uploadSlike;
    private List<Korisnik> trazeni_korisnici;
    private List<Drustvo> trazena_drustva;
    private String kljucnaRec;
    private String ime;
    private String prezime;
    private Date datum;
    private String mobilni;
    private String mesto;
    private String drzava;
    private String email;
    private int id;
    private boolean izmeni = true;  //ovime zabranjujem izmenjivanje podataka;
    private boolean promene;
    private Date noviDatum;
    private String status;
    private List<String> lovista;
    private List<String> omiljena_lovista;
    private MenuModel ol_model;
    private List<String> zivotinje;
    private List<String> omiljene_zivotinje;
    private List<String> svi_korisnici;
    private List<String> odabrani_korisnici;
    private MenuModel oz_model;
    private boolean izmena_lovista;
    private boolean izmena_zivotinje;
    private boolean objavi;
    private String naslov;
    private String tekst;
    private List<String> sve_kategorije_vesti;
    private String kategorija;
    private String privatnost;
    private boolean grupa;
    private boolean pretraga;
    private boolean gotovUpload;
    private boolean prikaziIme;
    private boolean prikaziPrezime;
    private boolean prikaziMesto;
    private boolean prikaziDrzava;
    private boolean prikaziTelefon;
    private boolean prikaziEmail;
    private boolean prikaziDatumRodjenja;
    private boolean prikaziStatus;
    private boolean prikaziOZ;
    private boolean prikaziOL;
    private String radioButton;

    public List<Drustvo> getTrazena_drustva() {
        return trazena_drustva;
    }

    public void setTrazena_drustva(List<Drustvo> trazena_drustva) {
        this.trazena_drustva = trazena_drustva;
    }

    public List<String> getSve_kategorije_vesti() {
        return sve_kategorije_vesti;
    }

    public void setSve_kategorije_vesti(List<String> sve_kategorije_vesti) {
        this.sve_kategorije_vesti = sve_kategorije_vesti;
    }
    
    
    
    public String getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(String radioButton) {
        this.radioButton = radioButton;
    }
    

    public boolean isPrikaziPrezime() {
        return prikaziPrezime;
    }

    public void setPrikaziPrezime(boolean prikaziPrezime) {
        this.prikaziPrezime = prikaziPrezime;
    }

    public boolean isPrikaziMesto() {
        return prikaziMesto;
    }

    public void setPrikaziMesto(boolean prikaziMesto) {
        this.prikaziMesto = prikaziMesto;
    }

    public boolean isPrikaziDrzava() {
        return prikaziDrzava;
    }

    public void setPrikaziDrzava(boolean prikaziDrzava) {
        this.prikaziDrzava = prikaziDrzava;
    }

    public boolean isPrikaziTelefon() {
        return prikaziTelefon;
    }

    public void setPrikaziTelefon(boolean prikaziTelefon) {
        this.prikaziTelefon = prikaziTelefon;
    }

    public boolean isPrikaziEmail() {
        return prikaziEmail;
    }

    public void setPrikaziEmail(boolean prikaziEmail) {
        this.prikaziEmail = prikaziEmail;
    }

    public boolean isPrikaziDatumRodjenja() {
        return prikaziDatumRodjenja;
    }

    public void setPrikaziDatumRodjenja(boolean prikaziDatumRodjenja) {
        this.prikaziDatumRodjenja = prikaziDatumRodjenja;
    }

    public boolean isPrikaziStatus() {
        return prikaziStatus;
    }

    public void setPrikaziStatus(boolean prikaziStatus) {
        this.prikaziStatus = prikaziStatus;
    }

    public boolean isPrikaziOZ() {
        return prikaziOZ;
    }

    public void setPrikaziOZ(boolean prikaziOZ) {
        this.prikaziOZ = prikaziOZ;
    }

    public boolean isPrikaziOL() {
        return prikaziOL;
    }

    public void setPrikaziOL(boolean prikaziOL) {
        this.prikaziOL = prikaziOL;
    }
    
    
    
    

    public boolean isPrikaziIme() {
        return prikaziIme;
    }

    public void setPrikaziIme(boolean prikaziIme) {
        this.prikaziIme = prikaziIme;
    }
    
    
    
    
    

    public boolean isGotovUpload() {
        return gotovUpload;
    }

    public void setGotovUpload(boolean gotovUpload) {
        this.gotovUpload = gotovUpload;
    }
    
    
    
    
    

    
    public String getKljucnaRec() {
        return kljucnaRec;
    }

    public void setKljucnaRec(String kljucnaRec) {
        this.kljucnaRec = kljucnaRec;
    }

    public List<UploadedFile> getUploadSlike() {
        return uploadSlike;
    }

    public void setUploadSlike(List<UploadedFile> uploadSlike) {
        this.uploadSlike = uploadSlike;
    }
    
    
    

    public boolean isPretraga() {
        return pretraga;
    }

    public void setPretraga(boolean pretraga) {
        this.pretraga = pretraga;
    }

    public List<Korisnik> getTrazeni_korisnici() {
        return trazeni_korisnici;
    }

    public void setTrazeni_korisnici(List<Korisnik> trazeni_korisnici) {
        this.trazeni_korisnici = trazeni_korisnici;
    }
    
    
    

    public List<String> getSvi_korisnici() {
        return svi_korisnici;
    }

    public void setSvi_korisnici(List<String> svi_korisnici) {
        this.svi_korisnici = svi_korisnici;
    }

    public List<String> getOdabrani_korisnici() {
        return odabrani_korisnici;
    }

    public void setOdabrani_korisnici(List<String> odabrani_korisnici) {
        this.odabrani_korisnici = odabrani_korisnici;
    }
    
    public boolean isGrupa() {
        return grupa;
    }

    public void setGrupa(boolean grupa) {
        this.grupa = grupa;
    }
    
    
    
    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getPrivatnost() {
        return privatnost;
    }

    public void setPrivatnost(String privatnost) {
        this.privatnost = privatnost;
    }
    
    
    
    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public boolean isObjavi() {
        return objavi;
    }

    public void setObjavi(boolean objavi) {
        this.objavi = objavi;
    }
    
    public boolean isIzmena_zivotinje() {
        return izmena_zivotinje;
    }

    public void setIzmena_zivotinje(boolean izmena_zivotinje) {
        this.izmena_zivotinje = izmena_zivotinje;
    }

    public boolean isIzmena_lovista() {
        return izmena_lovista;
    }

    public void setIzmena_lovista(boolean izmena_lovista) {
        this.izmena_lovista = izmena_lovista;
    }

    public MenuModel getOl_model() {
        return ol_model;
    }

    public void setOl_model(MenuModel ol_model) {
        this.ol_model = ol_model;
    }

    public List<String> getZivotinje() {
        return zivotinje;
    }

    public void setZivotinje(List<String> zivotinje) {
        this.zivotinje = zivotinje;
    }

    public List<String> getOmiljene_zivotinje() {
        return omiljene_zivotinje;
    }

    public void setOmiljene_zivotinje(List<String> omiljene_zivotinje) {
        this.omiljene_zivotinje = omiljene_zivotinje;
    }

    public MenuModel getOz_model() {
        return oz_model;
    }

    public void setOz_model(MenuModel oz_model) {
        this.oz_model = oz_model;
    }

    public List<String> getLovista() {
        return lovista;
    }

    public void setLovista(List<String> lovista) {
        this.lovista = lovista;
    }

    public List<String> getOmiljena_lovista() {
        return omiljena_lovista;
    }

    public void setOmiljena_lovista(List<String> omiljena_lovista) {
        this.omiljena_lovista = omiljena_lovista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public boolean isPromene() {
        return promene;
    }

    public void setPromene(boolean promene) {
        this.promene = promene;
    }

    public boolean isIzmeni() {
        return izmeni;
    }

    public void setIzmeni(boolean izmeni) {
        this.izmeni = izmeni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vest> getVesti() {
        return vesti;
    }

    public void setVesti(List<Vest> vesti) {
        this.vesti = vesti;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getNoviDatum() {
        return noviDatum;
    }

    public void setNoviDatum(Date noviDatum) {
        this.noviDatum = noviDatum;
    }

    

    public String getMobilni() {
        return mobilni;
    }

    public void setMobilni(String mobilni) {
        this.mobilni = mobilni;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtils.getSession();
        Korisnik k = (Korisnik) session.getAttribute("korisnik");
        ime = k.getIme();
        prezime = k.getPrezime();
        mesto = k.getMesto();
        drzava = k.getDrzava();
        datum = new Date(k.getDatumRodjenja().getTime());
        mobilni = k.getBrMobilnog();
        email = k.getEmail();
        id = k.getId();
        if (k.getStatus()) {
            status = "Aktivan";
        } else {
            status = "Neaktivan";
        }
        sve_kategorije_vesti = KorisnickaStranaDAO.dohvatiKategorijeVesti();
        vesti = KorisnickaStranaDAO.dohVesti(id, false, 0);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        
        lovista = LovacDAO.dohLovista();
        Pair<MenuModel, List<String>> pair = LovacDAO.dohOmiljenaLovista(id);
        omiljena_lovista = pair.getValue();
        ol_model = pair.getKey();

        zivotinje = KorisnickaStranaDAO.dohZivotinje();
        Pair<MenuModel, List<String>> pair2 = KorisnickaStranaDAO.dohOmiljeneZivotinje(id, false);
        omiljene_zivotinje = pair2.getValue();
        oz_model = pair2.getKey();
        
        svi_korisnici = LovacDAO.dohSveKorisnike(id);
        uploadSlike = new ArrayList<>();
        
        postaviVidljivostPodataka(id);
        
        radioButton = "Korisnici"; //ovo je vezano za pretragu korisnika
    }

    public void izmeniPodatke() {
        izmeni = false;
        promene = true;
    }

    public void ponistiIzmenu() {
        HttpSession session = SessionUtils.getSession();
        Korisnik k = (Korisnik) session.getAttribute("korisnik");
        ime = k.getIme();
        prezime = k.getPrezime();
        mesto = k.getMesto();
        drzava = k.getDrzava();
        datum = new Date(k.getDatumRodjenja().getTime());
        mobilni = k.getBrMobilnog();
        email = k.getEmail();
        id = k.getId();
        if (k.getStatus()) {
            status = "Aktivan";
        } else {
            status = "Neaktivan";
        }
        postaviVidljivostPodataka(id);
        Pair<MenuModel, List<String>> pair = LovacDAO.dohOmiljenaLovista(id);
        omiljena_lovista = pair.getValue();
        ol_model = pair.getKey();
        
        Pair<MenuModel, List<String>> pair2 = KorisnickaStranaDAO.dohOmiljeneZivotinje(id, false);
        omiljene_zivotinje = pair2.getValue();
        oz_model = pair2.getKey();
        
        izmeni = true;
        promene = false;
        izmena_lovista = false;
        izmena_zivotinje = false;
    }

    public void sacuvajPodatke() {
        Korisnik k = new Korisnik();
        k.setIme(ime);
        k.setPrezime(prezime);
        k.setMesto(mesto);
        k.setDrzava(drzava);
        k.setBrMobilnog(mobilni);
        k.setEmail(email);
        if (noviDatum != null) {
            k.setDatumRodjenja(new java.sql.Date(noviDatum.getTime()));
            datum = noviDatum;
        } else {
            k.setDatumRodjenja(new java.sql.Date(datum.getTime()));
        }
        k.setId(id);
        if (status.equals("Neaktivan")) {
            k.setStatus(false);
        } else {
            k.setStatus(true);
        }
        ol_model.getElements().clear();
        for (String loviste : omiljena_lovista) {
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(loviste);
            ol_model.addElement(item);
        }
        
        oz_model.getElements().clear();
        for (String zivotinja : omiljene_zivotinje) {
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(zivotinja);
            oz_model.addElement(item);
        }
        sacuvajVidljivostPodataka();
        boolean flag = LovacDAO.sacuvajPodatke(k, omiljena_lovista, izmena_lovista, omiljene_zivotinje, izmena_zivotinje);
        izmena_lovista = false;
        izmena_zivotinje = false;
        if (!flag) {
            alert("Uneti e-mail vec postoji!");
        } else {
            SessionUtils.getSession().setAttribute("korisnik", k);
            izmeni = true;
            promene = false;
        }
    }
    
    public void submitVest(){
        int p = Integer.parseInt(privatnost);
        String[] split = tekst.split("[\n]");
        tekst = "";
        for (String s : split) {
            tekst += s + "<br />";
        }
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);
        KorisnickaStranaDAO.objaviVest(naslov, tekst, p, kat, false, id, ime + " " + prezime, odabrani_korisnici, uploadSlike);
        objavi = false;
        
    }
    
    public void pretraziKorisnike(){
        trazeni_korisnici = KorisnickaStranaDAO.pretraziKorisnike(kljucnaRec);
        trazena_drustva = KorisnickaStranaDAO.pretraziLovackaDrustva(kljucnaRec);
        if(trazeni_korisnici == null && trazena_drustva == null)
            alert("Ne postoji korisnik koga trazite");
        else{
            objavi = false;
            pretraga = true;
        }
    }
    
    
    public void handleFileUpload(FileUploadEvent event) {
        uploadSlike.add(event.getFile());
        gotovUpload = true;
    }
    
    public void objaviVest(){
        objavi = true;
    }

    public void zahtevZaBrisanje(int vest_id) {
        KorisnickaStranaDAO.generisiZahtevZaBrisanje(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);    //moram da posaljem trenutnu kategoriju
        vesti = KorisnickaStranaDAO.dohVesti(id, false, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    public void ponistiBrisanje(int vest_id) {
        KorisnickaStranaDAO.ponistiBrisanje(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);
        vesti = KorisnickaStranaDAO.dohVesti(id, false, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
//moram ponovo ovo da dohvatim
    }
    
    public void prigovorNaBrisanje(int vest_id) {
        KorisnickaStranaDAO.prigovorNaBrisanje(vest_id);
        alert("Ulozili ste prigovor na brisanje ove vesti.");
    }
    
    public String dohvatiAutora(int id_vesti){
        return KorisnickaStranaDAO.dohvatiAutoraVesti(id_vesti);
    }

    public boolean proveriAutora(int id_korisnika) {
        if (id_korisnika == id) {
            return true;
        }
        return false;
    }
    

    public void updateVesti(){
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);
        vesti = KorisnickaStranaDAO.dohVesti(id, false, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    public void arhiviraj(int vest_id) {
        KorisnickaStranaDAO.arhivirajVest(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);
        vesti = KorisnickaStranaDAO.dohVesti(id, false, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    

    public void prikazi(int vest_id) {
        KorisnickaStranaDAO.prikaziVest(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija);
        vesti = KorisnickaStranaDAO.dohVesti(id, false, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }

    public String prikaziDatum() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(datum);
    }

    public void postaviIzmenaFlagLovista() {
        izmena_lovista = true;
    }

    public void postaviIzmenaFlagZivotinje() {
        izmena_zivotinje = true;
    }
    
    public void updatePrivatnost(){
        if(privatnost.equals("3"))
            grupa = true;
        else
            grupa = false;
    }
    
    public String dohvatiKategorijuVesti(int kat){
        return KorisnickaStranaDAO.dohKategorijuString(kat);
    }

    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "');");
    }

    private void postaviVidljivostPodataka(int id) {
        int val = LovacDAO.dohvatiVidljivostPodataka(id);
        if((val & 0x1) == 1)
            prikaziIme = true;
        if((val>>1 & 0x1) == 1)
            prikaziPrezime = true;
        if((val>>2 & 0x1) == 1)
            prikaziMesto = true;
        if((val>>3 & 0x1) == 1)
            prikaziDrzava = true;
        if((val>>4 & 0x1) == 1)
            prikaziTelefon = true;
        if((val>>5 & 0x1) == 1)
            prikaziEmail = true;
        if((val>>6 & 0x1) == 1)
            prikaziDatumRodjenja = true;
        if((val>>7 & 0x1) == 1)
            prikaziStatus = true;
        if((val>>8 & 0x1) == 1)
            prikaziOL = true;
        if((val>>9 & 0x1) == 1)
            prikaziOZ = true;
        
    }
    
    private void sacuvajVidljivostPodataka(){
        int val = 0;
        if(prikaziIme)
            val |= 0x1;
        if(prikaziPrezime)
            val |= 0x1<<1;
        if(prikaziMesto)
            val |= 0x1<<2;
        if(prikaziDrzava)
            val |= 0x1<<3;
        if(prikaziTelefon)
            val |= 0x1<<4;
        if(prikaziEmail)
            val |= 0x1<<5;
        if(prikaziDatumRodjenja)
            val |= 0x1<<6;
        if(prikaziStatus)
            val |= 0x1<<7;
        if(prikaziOL)
            val |= 0x1<<8;
        if(prikaziOZ)
            val |= 0x1<<9;
        
        LovacDAO.sacuvajVidljivostPodataka(id, val);
    }

    
    public String pogledajGaleriju(){
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("tip_korisnika", false);
        return "galerijaSlika?faces-redirect=true";
    }
}
