/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.localHomeType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import util.SessionUtils;
import util.dao.RegistracijaDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@SessionScoped
@Named(value = "registracija")
public class Registracija implements Serializable {

    private String ime;
    private String prezime;
    private String srednjeIme;
    private String mesto;
    private String drzava;
    private String brojClanskeKarte;
    private String imeLovackogDrustva;
    private String brMobilnog;
    private String email;
    private String potvrda_email;
    private String korime;
    private String lozinka;
    private Date datumRodjenja;

    private String status;
    private boolean napraviLozinku;
    private boolean pregledPodataka;
    private String ponoviLozinku;

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    
    
    
    public String getPonoviLozinku() {
        return ponoviLozinku;
    }

    public void setPonoviLozinku(String ponoviLozinku) {
        this.ponoviLozinku = ponoviLozinku;
    }

    public boolean isPregledPodataka() {
        return pregledPodataka;
    }

    public void setPregledPodataka(boolean pregledPodataka) {
        this.pregledPodataka = pregledPodataka;
    }

    public boolean isNapraviLozinku() {
        return napraviLozinku;
    }

    public void setNapraviLozinku(boolean napraviLozinku) {
        this.napraviLozinku = napraviLozinku;
    }

    public String getPotvrda_email() {
        return potvrda_email;
    }

    public void setPotvrda_email(String potvrda_email) {
        this.potvrda_email = potvrda_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrednjeIme() {
        return srednjeIme;
    }

    public void setSrednjeIme(String srednjeIme) {
        this.srednjeIme = srednjeIme;
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

    public String getBrojClanskeKarte() {
        return brojClanskeKarte;
    }

    public void setBrojClanskeKarte(String brojClanskeKarte) {
        this.brojClanskeKarte = brojClanskeKarte;
    }

    public String getImeLovackogDrustva() {
        return imeLovackogDrustva;
    }

    public void setImeLovackogDrustva(String imeLovackogDrustva) {
        this.imeLovackogDrustva = imeLovackogDrustva;
    }

    public String getBrMobilnog() {
        return brMobilnog;
    }

    public void setBrMobilnog(String brMobilnog) {
        this.brMobilnog = brMobilnog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String potvrdi() {
        if (!email.equals(potvrda_email)) {
            alert("Polja email se ne poklapaju");
            return null;
        }
        HttpSession sesija = SessionUtils.getSession();
        sesija.setAttribute("username", "registracija");
        pregledPodataka = true;
        return "confirmation?faces-redirect=true";
    }

    public String napraviLozinku() {
        napraviLozinku = true;
        pregledPodataka = false;
        HttpSession sesija = SessionUtils.getSession();
        sesija.setAttribute("username", "registracija");
        return "confirmation?faces-redirect=true";
    }

    public String povratakNaIndex() {
        napraviLozinku = false;
        pregledPodataka = false;
        HttpSession sesija = SessionUtils.getSession();
        sesija.setAttribute("username", "registracija");
        return "index?faces-redirect=true";
    }

    public void submit() {
        if (!lozinka.equals(ponoviLozinku)) {
            alert("Polja lozinke se ne poklapaju");
            return;
        }
        HttpSession sesija = SessionUtils.getSession();
        sesija.setAttribute("username", "registracija");
        
        
        Korisnik k = new Korisnik();
        k.setDatumRodjenja(new java.sql.Date(datumRodjenja.getTime()));
        k.setDrzava(drzava);
        k.setEmail(email);
        k.setIme(ime);
        k.setImeLovackogDrustva(imeLovackogDrustva);
        k.setKorime(korime);
        k.setLozinka(lozinka);
        k.setMesto(mesto);
        k.setPrezime(prezime);
        k.setSrednjeIme(srednjeIme);
        k.setBrMobilnog(brMobilnog);
        k.setBrojClanskeKarte(brojClanskeKarte);
        if(status.equals("Neaktivan"))
            k.setStatus(false);
        else
            k.setStatus(true);
        k.setKey(getRandomString());
        
        int i = RegistracijaDAO.DodajLovca(k);
        
        switch(i){
            case 0:
                alert("korisnicko ime vec postoji");
                return;
            case 1:
                alert("email vec postoji");
                return;
            case 2:
                alert("vec postoji korisnik iz istog lovackog drustva sa istim brojem clanske karte");
                return;
            case 3:
                napraviLozinku = false;
                pregledPodataka = false;
                PosaljiEmail se = new PosaljiEmail(email, k.getKey());
                obrisiSve();
                alertReplace("Uspesno ste napravili nalog. Verifikacioni email je poslat na Vasu email adresu");
                
                return;
            default:
                alert("Greska");
                return;
        }
        
    }

    public void obrisiSve() {

        ime = "";
        prezime = "";
        srednjeIme = "";
        mesto = "";
        drzava = "";
        brojClanskeKarte = "";
        imeLovackogDrustva = "";
        brMobilnog = "";
        email = "";
        potvrda_email = "";
        korime = "";
        lozinka = "";
        status = "";
        ponoviLozinku = "";
        datumRodjenja = null;
    }

    protected String getRandomString() {
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * string.length());
            sb.append(string.charAt(index));
        }
        
        return sb.toString();

    }

    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "');");
    }
    
    
    public void alertReplace(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "'); location.replace(\"index.xhtml\");");
    }
}
