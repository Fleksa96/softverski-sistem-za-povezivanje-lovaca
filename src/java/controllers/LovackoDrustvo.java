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
import util.dao.LovackoDrustvoDAO;

/**
 *
 * @author Aleksaa
 */


@ManagedBean
@Named(value = "drustvo")
@javax.faces.view.ViewScoped
public class LovackoDrustvo implements Serializable{

    
    private String ime;
    private String email;
    private int id;
    private String status;
    private String broj_clanova;
    private String iznos_clanarine;
    
    private List<Vest> vesti;
    private MenuModel oz_model;
    private List<String> zivotinje;
    private List<String> omiljene_zivotinje;
    
    private List<Drustvo> trazena_drustva;
    private List<Korisnik> trazeni_korisnici;
    private String kljucnaRec;
    private String radioButtonSearch;
    
    private List<String> svi_korisnici;
    private List<String> odabrani_korisnici;
    
    private List<String> sve_kategorije_vesti;
    private String kategorija_vesti;
    
    private String naslov;
    private String privatnost;
    private String tekst;
    private List<UploadedFile> uploadSlike;
    private boolean gotovUpload;
    
    private boolean grupa;
    private boolean objavi;
    private boolean pretraga;
    private boolean izmeni = true;
    private boolean promene;
    
    
    private boolean prikaziIme;
    private boolean prikaziEmail;
    private boolean prikaziStatus;
    private boolean prikaziBrojClanova;
    private boolean prikaziIznosClanarine;
    private boolean prikaziOZ;
    private boolean izmena_zivotinje;

    public String getRadioButtonSearch() {
        return radioButtonSearch;
    }

    public void setRadioButtonSearch(String radioButtonSearch) {
        this.radioButtonSearch = radioButtonSearch;
    }

    public List<Drustvo> getTrazena_drustva() {
        return trazena_drustva;
    }

    public void setTrazena_drustva(List<Drustvo> trazena_drustva) {
        this.trazena_drustva = trazena_drustva;
    }
    
    
    

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public List<UploadedFile> getUploadSlike() {
        return uploadSlike;
    }

    public void setUploadSlike(List<UploadedFile> uploadSlike) {
        this.uploadSlike = uploadSlike;
    }

    public boolean isGotovUpload() {
        return gotovUpload;
    }

    public void setGotovUpload(boolean gotovUpload) {
        this.gotovUpload = gotovUpload;
    }
    
    
    

    public String getPrivatnost() {
        return privatnost;
    }

    public void setPrivatnost(String privatnost) {
        this.privatnost = privatnost;
    }

    public boolean isGrupa() {
        return grupa;
    }

    public void setGrupa(boolean grupa) {
        this.grupa = grupa;
    }
    
    
    

    public boolean isIzmena_zivotinje() {
        return izmena_zivotinje;
    }

    public void setIzmena_zivotinje(boolean izmena_zivotinje) {
        this.izmena_zivotinje = izmena_zivotinje;
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
    
    
    

    public boolean isIzmeni() {
        return izmeni;
    }

    public void setIzmeni(boolean izmeni) {
        this.izmeni = izmeni;
    }

    public boolean isPromene() {
        return promene;
    }

    public void setPromene(boolean promene) {
        this.promene = promene;
    }
    
    
    
    

    public boolean isPrikaziIme() {
        return prikaziIme;
    }

    public void setPrikaziIme(boolean prikaziIme) {
        this.prikaziIme = prikaziIme;
    }

    public boolean isPrikaziEmail() {
        return prikaziEmail;
    }

    public void setPrikaziEmail(boolean prikaziEmail) {
        this.prikaziEmail = prikaziEmail;
    }

    public boolean isPrikaziStatus() {
        return prikaziStatus;
    }

    public void setPrikaziStatus(boolean prikaziStatus) {
        this.prikaziStatus = prikaziStatus;
    }

    public boolean isPrikaziBrojClanova() {
        return prikaziBrojClanova;
    }

    public void setPrikaziBrojClanova(boolean prikaziBrojClanova) {
        this.prikaziBrojClanova = prikaziBrojClanova;
    }

    public boolean isPrikaziIznosClanarine() {
        return prikaziIznosClanarine;
    }

    public void setPrikaziIznosClanarine(boolean prikaziIznosClanarine) {
        this.prikaziIznosClanarine = prikaziIznosClanarine;
    }

    public boolean isPrikaziOZ() {
        return prikaziOZ;
    }

    public void setPrikaziOZ(boolean prikaziOZ) {
        this.prikaziOZ = prikaziOZ;
    }
    
    

    public List<String> getSve_kategorije_vesti() {
        return sve_kategorije_vesti;
    }

    public void setSve_kategorije_vesti(List<String> sve_kategorije_vesti) {
        this.sve_kategorije_vesti = sve_kategorije_vesti;
    }

    public String getKategorija_vesti() {
        return kategorija_vesti;
    }

    public void setKategorija_vesti(String kategorija_vesti) {
        this.kategorija_vesti = kategorija_vesti;
    }
    
    
    
    

    public boolean isObjavi() {
        return objavi;
    }

    public void setObjavi(boolean objavi) {
        this.objavi = objavi;
    }

    public boolean isPretraga() {
        return pretraga;
    }

    public void setPretraga(boolean pretraga) {
        this.pretraga = pretraga;
    }
    
    
    

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBroj_clanova() {
        return broj_clanova;
    }

    public void setBroj_clanova(String broj_clanova) {
        this.broj_clanova = broj_clanova;
    }

    public String getIznos_clanarine() {
        return iznos_clanarine;
    }

    public void setIznos_clanarine(String iznos_clanarine) {
        this.iznos_clanarine = iznos_clanarine;
    }

    

    public List<Vest> getVesti() {
        return vesti;
    }

    public void setVesti(List<Vest> vesti) {
        this.vesti = vesti;
    }

    public MenuModel getOz_model() {
        return oz_model;
    }

    public void setOz_model(MenuModel oz_model) {
        this.oz_model = oz_model;
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

    public List<Korisnik> getTrazeni_korisnici() {
        return trazeni_korisnici;
    }

    public void setTrazeni_korisnici(List<Korisnik> trazeni_korisnici) {
        this.trazeni_korisnici = trazeni_korisnici;
    }

    public String getKljucnaRec() {
        return kljucnaRec;
    }

    public void setKljucnaRec(String kljucnaRec) {
        this.kljucnaRec = kljucnaRec;
    }
    
    
    
    
    @PostConstruct
    public void init() {
        HttpSession session = SessionUtils.getSession();
        Drustvo d = (Drustvo) session.getAttribute("drustvo");
        ime = d.getIme();
        email = d.getEmail();
        id = d.getId();
        if (d.isStatus()) {
            status = "Privatno";
        } else {
            status = "Drzavno";
        }
        broj_clanova = Integer.toString(d.getBroj_clanova());
        iznos_clanarine = Integer.toString(d.getIznos_clanarine());
        Pair<MenuModel, List<String>> pair = KorisnickaStranaDAO.dohOmiljeneZivotinje(id, true);
        oz_model = pair.getKey();
        omiljene_zivotinje = pair.getValue();
        zivotinje = KorisnickaStranaDAO.dohZivotinje();
        postaviVidljivostPodataka(id);
        
        svi_korisnici = LovackoDrustvoDAO.dohSveKorisnike(id);
        
        uploadSlike = new ArrayList<>();
        sve_kategorije_vesti = KorisnickaStranaDAO.dohvatiKategorijeVesti();
        vesti = KorisnickaStranaDAO.dohVesti(id, true, 0);
    }
    
    public void izmeniPodatke() {
        izmeni = false;
        promene = true;
    }
    
    
    public void ponistiIzmenu() {
        HttpSession session = SessionUtils.getSession();
        Drustvo d = (Drustvo) session.getAttribute("drustvo");
        ime = d.getIme();
        email = d.getEmail();
        id = d.getId();
        if (d.isStatus()) {
            status = "Privatno";
        } else {
            status = "Drzavno";
        }
        broj_clanova = Integer.toString(d.getBroj_clanova());
        iznos_clanarine = Integer.toString(d.getIznos_clanarine());
        
        Pair<MenuModel, List<String>> pair = KorisnickaStranaDAO.dohOmiljeneZivotinje(id, true);
        omiljene_zivotinje = pair.getValue();
        oz_model = pair.getKey();
        
        postaviVidljivostPodataka(id);
        
        izmeni = true;
        promene = false;
        izmena_zivotinje = false;
    }

    public void sacuvajPodatke() {
        Drustvo d = new Drustvo();
        d.setIme(ime);
        d.setEmail(email);
        d.setId(id);
        if (status.equals("Drzavno")) {
            d.setStatus(false);
        } else {
            d.setStatus(true);
        }
        
        oz_model.getElements().clear();
        for (String zivotinja : omiljene_zivotinje) {
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(zivotinja);
            oz_model.addElement(item);
        }
        sacuvajVidljivostPodataka();
        boolean flag = LovackoDrustvoDAO.sacuvajPodatke(d, omiljene_zivotinje, izmena_zivotinje);
        izmena_zivotinje = false;
        if (!flag) {
            alert("Uneti e-mail vec postoji!");
        } else {
            SessionUtils.getSession().setAttribute("drustvo", d);
            izmeni = true;
            promene = false;
        }
    }
    
    
    public void objaviVest(){
        objavi = true;
    }
    
    public void zahtevZaBrisanje(int vest_id) {
        KorisnickaStranaDAO.generisiZahtevZaBrisanje(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);    //moram da posaljem trenutnu kategoriju
        vesti = KorisnickaStranaDAO.dohVesti(id, true, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    public void ponistiBrisanje(int vest_id) {
        KorisnickaStranaDAO.ponistiBrisanje(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);
        vesti = KorisnickaStranaDAO.dohVesti(id, true, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
//moram ponovo ovo da dohvatim
    }
    
    
    
    public void prigovorNaBrisanje(int vest_id) {
        KorisnickaStranaDAO.prigovorNaBrisanje(vest_id);
        alert("Ulozili ste prigovor na brisanje ove vesti.");
    }
    
    public void arhiviraj(int vest_id) {
        KorisnickaStranaDAO.arhivirajVest(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);
        vesti = KorisnickaStranaDAO.dohVesti(id, true, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    public String dohvatiKategorijuVesti(int kat){
        return KorisnickaStranaDAO.dohKategorijuString(kat);
    }

    public void prikazi(int vest_id) {
        KorisnickaStranaDAO.prikaziVest(vest_id);
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);
        vesti = KorisnickaStranaDAO.dohVesti(id, true, kat);    //0 za tip korisnika i 0 za kategoriju, tj. da mi dohvati sve vesti
        //moram ponovo ovo da dohvatim
    }
    
    public boolean proveriAutora(int id_korisnika) {
        if (id_korisnika == id) {
            return true;
        }
        return false;
    }
    
    public String dohvatiAutora(int id_vesti){
        return KorisnickaStranaDAO.dohvatiAutoraVesti(id_vesti);
    }
    
    public void updatePrivatnost(){
        if(privatnost.equals("3"))
            grupa = true;
        else
            grupa = false;
    }
    
    public void postaviIzmenaFlagZivotinje() {
        izmena_zivotinje = true;
    }
    
    public void updateVesti(){
        int kategorija = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);
        vesti = KorisnickaStranaDAO.dohVesti(id, true, kategorija);  //moram ponovo ovo da dohvatim
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
    
    public void submitVest(){
        int p = Integer.parseInt(privatnost);
        String[] split = tekst.split("[\n]");
        tekst = "";
        for (String s : split) {
            tekst += s + "<br />";
        }
        int kat = KorisnickaStranaDAO.dohvatiKategoriju(kategorija_vesti);
        KorisnickaStranaDAO.objaviVest(naslov, tekst, p, kat, true, id, ime, odabrani_korisnici, uploadSlike);
        objavi = false;
        
    }
    
    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "');");
    }
    
    private void postaviVidljivostPodataka(int id) {
        int val = LovackoDrustvoDAO.dohvatiVidljivostPodataka(id);
        if((val & 0x1) == 1)
            prikaziIme = true;
        if((val>>1 & 0x1) == 1)
            prikaziEmail = true;
        if((val>>2 & 0x1) == 1)
            prikaziStatus = true;
        if((val>>3 & 0x1) == 1)
            prikaziBrojClanova = true;
        if((val>>4 & 0x1) == 1)
            prikaziIznosClanarine = true;
        if((val>>5 & 0x1) == 1)
            prikaziOZ = true;
        
    }
    
    private void sacuvajVidljivostPodataka(){
        int val = 0;
        if(prikaziIme)
            val |= 0x1;
        if(prikaziEmail)
            val |= 0x1<<1;
        if(prikaziStatus)
            val |= 0x1<<2;
        if(prikaziBrojClanova)
            val |= 0x1<<3;
        if(prikaziIznosClanarine)
            val |= 0x1<<4;
        if(prikaziOZ)
            val |= 0x1<<5;
        
        LovackoDrustvoDAO.sacuvajVidljivostPodataka(id, val);
    }
    
    public String pogledajGaleriju(){
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("tip_korisnika", true);
        return "galerijaSlika?faces-redirect=true";
    }
}
