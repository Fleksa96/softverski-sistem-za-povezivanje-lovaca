/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Admin;
import beans.Vest;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import util.SessionUtils;
import util.dao.KorisnickaStranaDAO;
import util.dao.PocetnaStranicaDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@Named(value = "homepage")
@SessionScoped
public class PocetnaStranica implements Serializable{
    private boolean login;
    private boolean registracija;
    private List<Vest> vesti;
    private List<Admin> admins;

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
    
    
    

    public List<Vest> getVesti() {
        return vesti;
    }

    public void setVesti(List<Vest> vesti) {
        this.vesti = vesti;
    }
    
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegistracija() {
        return registracija;
    }

    public void setRegistracija(boolean registracija) {
        this.registracija = registracija;
    }
    
    

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
    
    public void login(){
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", "homepage");
        login = !login;
        registracija = false;
    }
    
    public void registracija(){
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", "homepage");
        registracija = !registracija;
        login = false;
    }
    
//    Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%^&+=])(?=\\S+$).{8,}$");
//        Matcher m = pattern.matcher(password);
//        if (!m.matches()) {
//            return false;
//        }
    
    
    public void zaboravljenaLozinka(){
        if(PocetnaStranicaDAO.verifikujKorisnika(email))
            alertRefresh("Korisnicko ime i lozinka su poslati na Vas email");
        else
            alertRefresh("Uneli ste pogresnu email adresu");
        email = "";
            
    }
    
    public void alertRefresh(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "'); location.reload(true);");
    }
    
    @PostConstruct
    public void init() {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", "homepage");
        vesti = KorisnickaStranaDAO.dohJavneVesti();//dohvata mi sve javne vesti svih korisnika
        admins = KorisnickaStranaDAO.dohAdministratore();
    }
    
    public String dohvatiKategorijuVesti(int kat){
        return KorisnickaStranaDAO.dohKategorijuString(kat);
    }
    
    public String dohvatiAutora(int id_vesti){
        return KorisnickaStranaDAO.dohvatiAutoraVesti(id_vesti);
    }
    
    
}
