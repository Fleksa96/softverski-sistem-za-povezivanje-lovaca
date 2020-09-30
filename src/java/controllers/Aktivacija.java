/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.dao.AktivacijaDAO;

/**
 *
 * @author Aleksaa
 */
@ManagedBean
@Named(value = "activation")
@RequestScoped
public class Aktivacija implements Serializable {

    private String key;

    private boolean valid;
    private boolean invalid;

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @PostConstruct
    public void init() {
        key = getPassedParameter();
        valid = AktivacijaDAO.dohKljuc(key);
        invalid = !valid;
    }

    public String getPassedParameter() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        key = (String) facesContext.getExternalContext().
                getRequestParameterMap().get("key");
        return key;
    }
}
