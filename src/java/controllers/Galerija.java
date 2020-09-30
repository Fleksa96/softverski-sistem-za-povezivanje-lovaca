/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import util.SessionUtils;
import util.dao.KorisnickaStranaDAO;

/**
 *
 * @author Aleksaa
 */

@ManagedBean
@Named(value = "galerija")
@RequestScoped
public class Galerija {
    
    private boolean tip_korisnika;
    private String korime;
    private List<byte[]> slike;
    private List<UploadedFile> uploadSlike;
    private boolean gotovUpload;

    public boolean isTip_korisnika() {
        return tip_korisnika;
    }

    public void setTip_korisnika(boolean tip_korisnika) {
        this.tip_korisnika = tip_korisnika;
    }

    public List<byte[]> getSlike() {
        return slike;
    }

    public void setSlike(List<byte[]> slike) {
        this.slike = slike;
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
    
    
    
    
    @PostConstruct
    public void init() {
        HttpSession session = SessionUtils.getSession();
        korime = (String) session.getAttribute("username");
        tip_korisnika = (boolean) session.getAttribute("tip_korisnika");
        
        slike = KorisnickaStranaDAO.dohSlikeKorisnika(korime, tip_korisnika);
        
        uploadSlike = new ArrayList<>();
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        uploadSlike.add(event.getFile());
        KorisnickaStranaDAO.objaviSlike(korime, tip_korisnika, uploadSlike);
    }
    
    public String povratakNaPocetnu(){
        if(tip_korisnika)
            return "lovacko_drustvo?faces_redirect=true";
        else
            return "lovac?faces_redirect=true";
    }
}
