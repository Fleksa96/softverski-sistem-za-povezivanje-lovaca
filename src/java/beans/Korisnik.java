/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Aleksaa
 */
public class Korisnik implements Serializable{
    private int id;
    private String ime;
    private String prezime;
    private String srednjeIme;
    private String mesto;
    private String drzava;
    private String brojClanskeKarte;
    private String imeLovackogDrustva;
    private String brMobilnog;
    private String email;
    private boolean status;
    private String korime;
    private String lozinka;
    private Date datumRodjenja;
    private int tip; //1-lovac, 2-lovacko drustvo, 3-admin, 4-gost
    private String key; //ovo je za aktivacioni link zastita
    private int vidljivost_podataka;
    private boolean prikaziMesto;
    private boolean prikaziDrzava;
    private boolean prikaziTelefon;
    private boolean prikaziEmail;
    private boolean prikaziDatumRodjenja;
    private boolean prikaziStatus;
    private boolean prikaziOZ;
    private boolean prikaziOL;
    private MenuModel oz_model;
    private MenuModel ol_model;
    

    public MenuModel getOz_model() {
        return oz_model;
    }

    public void setOz_model(MenuModel oz_model) {
        this.oz_model = oz_model;
    }

    public MenuModel getOl_model() {
        return ol_model;
    }

    public void setOl_model(MenuModel ol_model) {
        this.ol_model = ol_model;
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
    
    
    

    public int getVidljivost_podataka() {
        return vidljivost_podataka;
    }

    public void setVidljivost_podataka(int vidljivost_podataka) {
        this.vidljivost_podataka = vidljivost_podataka;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBrojClanskeKarte() {
        return brojClanskeKarte;
    }

    public void setBrojClanskeKarte(String brojClanskeKarte) {
        this.brojClanskeKarte = brojClanskeKarte;
    }

    public String getBrMobilnog() {
        return brMobilnog;
    }

    public void setBrMobilnog(String brMobilnog) {
        this.brMobilnog = brMobilnog;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    
    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImeLovackogDrustva() {
        return imeLovackogDrustva;
    }

    public void setImeLovackogDrustva(String imeLovackogDrustva) {
        this.imeLovackogDrustva = imeLovackogDrustva;
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
    
    
}
