/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Admin;
import beans.Drustvo;
import beans.Korisnik;
import java.io.Serializable;
import javafx.util.Pair;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import util.SessionUtils;
import util.dao.LoginDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@SessionScoped
@Named(value = "login")
public class Login implements Serializable {

    String korime;
    String lozinka;

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

    public String login() {
        Pair<Object, Integer> pair = LoginDAO.dohvatiKorisnika(korime, lozinka);
        int tip = 0;
        Korisnik k = null;
        Drustvo d = null;
        Admin a = null;
        if (pair != null) {
            
            tip = pair.getValue();
            switch(tip){
                case 1:
                    k = (Korisnik) pair.getKey();
                    break;
                case 2:
                    d = (Drustvo) pair.getKey();
                    break;
                case 3:
                    a = (Admin) pair.getKey();
                    break;
            }
        }
        HttpSession sesija = SessionUtils.getSession();
        sesija.setAttribute("username", korime);

        switch (tip) {
            case 0:
                alert("Korisnik ne postoji");
                return null;
            case 1:
                sesija.setAttribute("korisnik", k);
                return "lovac?faces-redirect=true";
            case 2:
                sesija.setAttribute("drustvo", d);
                return "lovacko_drustvo?faces-redirect=true";
            case 3:
                sesija.setAttribute("admin", a);
                return "administrator?faces-redirect=true";
            case 4:
                alert("Nalog nije verifikovan, verifikacioni mail je poslat na vasu email adresu");
                return null;
        }
        return null;
    }
    
    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "');");
    }
}
