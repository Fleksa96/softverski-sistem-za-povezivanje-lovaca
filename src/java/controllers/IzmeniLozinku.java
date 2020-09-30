/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import util.DB;
import util.SessionUtils;
import util.dao.IzmeniLozinkuDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@Named(value = "izmeni")
@SessionScoped
public class IzmeniLozinku implements Serializable {

    private String lozinka;
    private String nova_lozinka;

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getNova_lozinka() {
        return nova_lozinka;
    }

    public void setNova_lozinka(String nova_lozinka) {
        this.nova_lozinka = nova_lozinka;
    }

    public void promeniLozinku() {
        if (!lozinka.equals(nova_lozinka)) {
            alert("Lozinke se ne poklapaju. Pokusajte ponovo.");
        }
        HttpSession session = SessionUtils.getSession();
        String korime = (String) session.getAttribute("username");
        IzmeniLozinkuDAO.izmeniLozinku(korime, nova_lozinka);
        alertReplace("Uspesno ste promenili svoju lozinku");
    }

    public void alert(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "');");
    }

    public void alertReplace(String poruka) {
        PrimeFaces.current().executeScript("alert('" + poruka + "'); location.replace(\"lovac.xhtml\");");
    }
}
