/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Aleksaa
 */
public class Vest implements Serializable{
    public int id;
    public String naziv;
    public String tekst;
    public int kategorija;
    public Timestamp datum;
    public String autor;    //ime i prezime koje ce biti link ka njihovom profilu
    public int privatnost;  //1-lovci, 2 - lovacka drustva, 3 - grupa neka, 4- svi
    public int id_korisnika;
    public boolean arhivirana;
    public List<byte[]> slike;
    public boolean zahtev_za_brisanje;
    public boolean flag_obrisi;

    
    public boolean isZahtev_za_brisanje() {
        return zahtev_za_brisanje;
    }

    public void setZahtev_za_brisanje(boolean zahtev_za_brisanje) {
        this.zahtev_za_brisanje = zahtev_za_brisanje;
    }

    public boolean isFlag_obrisi() {
        return flag_obrisi;
    }

    public void setFlag_obrisi(boolean flag_obrisi) {
        this.flag_obrisi = flag_obrisi;
    }
    
    
    
    

    public List<byte[]> getSlike() {
        return slike;
    }

    public void setSlike(List<byte[]> slike) {
        this.slike = slike;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    
    
    public boolean isArhivirana() {
        return arhivirana;
    }

    public void setArhivirana(boolean arhivirana) {
        this.arhivirana = arhivirana;
    }

    public int getId_korisnika() {
        return id_korisnika;
    }

    public void setId_korisnika(int id_korisnika) {
        this.id_korisnika = id_korisnika;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getKategorija() {
        return kategorija;
    }

    public void setKategorija(int kategorija) {
        this.kategorija = kategorija;
    }

    
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPrivatnost() {
        return privatnost;
    }

    public void setPrivatnost(int privatnost) {
        this.privatnost = privatnost;
    }
    
    
}
