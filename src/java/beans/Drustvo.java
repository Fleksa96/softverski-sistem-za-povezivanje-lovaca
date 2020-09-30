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
public class Drustvo implements Serializable{
    private int id;
    private String ime;
    private String email;
    private boolean status;
    private String lozinka;
    private int vidljivost_podataka;
    private boolean prikaziEmail;
    private boolean prikaziStatus;
    private boolean prikaziOZ;
    private boolean prikaziBrojClanova;
    private boolean prikaziIznosClanarine;
    private MenuModel oz_model;
    
    private int broj_clanova;
    private int iznos_clanarine;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getVidljivost_podataka() {
        return vidljivost_podataka;
    }

    public void setVidljivost_podataka(int vidljivost_podataka) {
        this.vidljivost_podataka = vidljivost_podataka;
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

    public boolean isPrikaziOZ() {
        return prikaziOZ;
    }

    public void setPrikaziOZ(boolean prikaziOZ) {
        this.prikaziOZ = prikaziOZ;
    }

    public MenuModel getOz_model() {
        return oz_model;
    }

    public void setOz_model(MenuModel oz_model) {
        this.oz_model = oz_model;
    }

    public int getBroj_clanova() {
        return broj_clanova;
    }

    public void setBroj_clanova(int broj_clanova) {
        this.broj_clanova = broj_clanova;
    }

    public int getIznos_clanarine() {
        return iznos_clanarine;
    }

    public void setIznos_clanarine(int iznos_clanarine) {
        this.iznos_clanarine = iznos_clanarine;
    }

    
    
}
