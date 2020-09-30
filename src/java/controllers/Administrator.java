/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Admin;
import beans.Korisnik;
import beans.Vest;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import util.SessionUtils;
import util.dao.AdministratorDAO;
import util.dao.KorisnickaStranaDAO;

/**
 *
 * @author Aleksaa
 */

@ManagedBean
@Named(value = "administrator")
@RequestScoped
public class Administrator {
    
    private List<Vest> vesti;
    private String ime;
    private String prezime;
    private String email;
    private String kategorija_vesti;

    public String getKategorija_vesti() {
        return kategorija_vesti;
    }

    public void setKategorija_vesti(String kategorija_vesti) {
        this.kategorija_vesti = kategorija_vesti;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    

    public List<Vest> getVesti() {
        return vesti;
    }

    public void setVesti(List<Vest> vesti) {
        this.vesti = vesti;
    }
    
    
    
    
    @PostConstruct
    public void init() {
        HttpSession session = SessionUtils.getSession();
        Admin a = (Admin) session.getAttribute("admin");
        ime = a.getIme();
        prezime = a.getPrezime();
        email = a.getEmail();
        
        vesti = AdministratorDAO.dohJavneVestiZaBrisanje();
        
    }
    
    
    public String dohvatiKategorijuVesti(int kat){
        return KorisnickaStranaDAO.dohKategorijuString(kat);
    }
    
    public String dohvatiAutora(int id_vesti){
        return KorisnickaStranaDAO.dohvatiAutoraVesti(id_vesti);
    }
    
    public void unesiKategoriju(){
        if(AdministratorDAO.unesiKategoriju(kategorija_vesti))
            alert("Uspesno uneta kategorija");
        else
            alert("Kategorija vec postoji");
    }
    
    public void obrisiVest(int id){
        AdministratorDAO.obrisiVest(id);
        alert("Vest je obrisana");
    }
    
    public void ponistiBrisanje(int id){
        AdministratorDAO.ponistiBrisanje(id);
        alert("Vest je sklonjena iz zahteva za brisanje");
    }
    
    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "'); location.reload(true);");
    }
}
