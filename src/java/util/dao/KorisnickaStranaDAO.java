/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Admin;
import beans.Drustvo;
import beans.Korisnik;
import beans.Vest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class KorisnickaStranaDAO {
    
    public static List<Vest> dohVesti(int id, boolean tip_korisnika, int kategorija){
        
        String query1 = "select * "     //dohvatam vesti dostupne svim lovcima
                + "from vesti "
                + "where privatnost in (?,4) and (arhivirana < 1 or (arhivirana > 0 and tip_korisnika = ? and id_korisnika = ?)) "
                + "order by datum desc";
        String query2 = "select DISTINCT(v.id), v.naziv, v.tekst, v.kategorija, v.datum, v.autor, v.id_korisnika, v.arhivirana, v.zahtev_za_brisanje, v.flag_obrisi " //dohvatam vesti koje su dostupne ovom korisniku jer je to neko selektovao
                + "from vesti v, nivo_vesti nv "
                + "where v.privatnost = 3 and ((v.arhivirana < 1 and nv.tip_korisnika = ? and nv.id_korisnika = ? and nv.id_vesti = v.id) or (v.tip_korisnika = ? and v.id_korisnika = ?)) ";
        //(arhivirana < 1 or (arhivirana > 0 and tip_korisnika = 0 and id_korisnika = ?)) ovo proveravam da li je arhivirana i ako jeste
        //onda samo korisnik moze da vidi ovu vest
        String query3 = "select slika "
                + "from slike_vest "
                + "where id_vesti = ?";
        ArrayList<Vest> vesti = new ArrayList<>();
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        ResultSet rs3 = null;
        try {
            
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            
            if(tip_korisnika)
                ps1.setInt(1, 2);
            else
                ps1.setInt(1, 1);
            ps1.setBoolean(2, tip_korisnika);
            ps1.setInt(3, id);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            while(rs.next()){
                Vest v = new Vest();
                v.setAutor(rs.getString("autor"));
                v.setDatum(rs.getTimestamp("datum"));
                v.setNaziv(rs.getString("naziv"));
                v.setTekst(rs.getString("tekst"));
                v.setKategorija(rs.getInt("kategorija"));
                v.setId(rs.getInt("id"));
                v.setId_korisnika(rs.getInt("id_korisnika"));
                v.setArhivirana(rs.getBoolean("arhivirana"));
                v.setZahtev_za_brisanje(rs.getBoolean("zahtev_za_brisanje"));
                v.setFlag_obrisi(rs.getBoolean("flag_obrisi"));
                //dohvatam iz baze slike ako ih ima u vesti
                ps3.setInt(1, v.getId());
                ps3.execute();
                rs3 = ps3.getResultSet();
                List<byte[]> slike = new ArrayList<>();
                while(rs3.next()){
                    slike.add(rs3.getBlob("slika").getBytes(1, (int) rs3.getBlob("slika").length()));
                }
                v.setSlike(slike);
                
                if(kategorija == 0 || kategorija == v.getKategorija())
                    vesti.add(v);
                else 
                    if(kategorija == 3 && v.isZahtev_za_brisanje())
                        vesti.add(v);
            }
            
            ps2.setBoolean(1, tip_korisnika);
            ps2.setInt(2, id);
            ps2.setBoolean(3, tip_korisnika);
            ps2.setInt(4, id);
            ps2.execute();
            
            rs = ps2.getResultSet();
            
            while(rs.next()){
                Vest v = new Vest();
                v.setAutor(rs.getString("autor"));
                v.setDatum(rs.getTimestamp("datum"));
                v.setNaziv(rs.getString("naziv"));
                v.setTekst(rs.getString("tekst"));
                v.setKategorija(rs.getInt("kategorija"));
                v.setId(rs.getInt("id"));
                v.setId_korisnika(rs.getInt("id_korisnika"));
                v.setArhivirana(rs.getBoolean("arhivirana"));
                v.setZahtev_za_brisanje(rs.getBoolean("zahtev_za_brisanje"));
                v.setFlag_obrisi(rs.getBoolean("flag_obrisi"));
                //dohvatam iz baze slike ako ih ima u vesti
                ps3.setInt(1, v.getId());
                ps3.execute();
                rs3 = ps3.getResultSet();
                List<byte[]> slike = new ArrayList<>();
                while(rs3.next()){
                    slike.add(rs3.getBlob("slika").getBytes(1, (int) rs3.getBlob("slika").length()));
                }
                if(slike != null)
                    v.setSlike(slike);
                
                if(kategorija == 0 || kategorija == v.getKategorija())
                    vesti.add(v);
                else 
                    if(kategorija == 3 && v.isZahtev_za_brisanje())
                        vesti.add(v);
            }
            Collections.sort(vesti, new Comparator<Vest>() {
                @Override
                public int compare(Vest v1, Vest v2){
                    return v2.getDatum().compareTo(v1.getDatum());
                }
            });
            return vesti;
            
            } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps1 != null) {
                    ps1.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        return vesti;
    }
    
    public static void arhivirajVest(int vest_id){
        String query = "update Vesti "
                + "set arhivirana = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            
            ps.setBoolean(1, true);
            ps.setInt(2, vest_id);
            ps.executeUpdate();
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void prikaziVest(int vest_id){
        String query = "update Vesti "
                + "set arhivirana = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            
            ps.setBoolean(1, false);
            ps.setInt(2, vest_id);
            ps.executeUpdate();
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String dohvatiAutoraVesti(int id_vesti) {
        
        String query1 = "select tip_korisnika, id_korisnika "
                + "from vesti "
                + "where id = ?";
        
        String query2 = "select ime, srednjeIme, prezime "
                + "from lovac "
                + "where id = ?";
        
        String query3 = "select ime "
                + "from lovacko_drustvo "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            
            ps1.setInt(1, id_vesti);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            int tip_korisnika = -1;
            int id_korisnika = 0;
            
            if(rs.next()){
                tip_korisnika = rs.getInt("tip_korisnika");
                id_korisnika = rs.getInt("id_korisnika");
            }
            
            if(tip_korisnika == 0){
                ps2.setInt(1, id_korisnika);
                ps2.execute();
                rs = ps2.getResultSet();
                String result = null;
                if(rs.next()){
                    result = "Autor: ";
                    result += rs.getString("ime");
                    result += " " + rs.getString("srednjeIme") + " " + rs.getString("prezime");
                }
                return result;
            } else{
                ps3.setInt(1, id_korisnika);
                ps3.execute();
                
                rs = ps3.getResultSet();
                String result = null;
                if(rs.next())
                    result = "Autor: " + rs.getString("ime");
                return result;
            }
                
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(ps2 != null)
                    ps2.close();
                if(ps3 != null)
                    ps3.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    
    public static List<Korisnik> pretraziKorisnike(String kljucnaRec) {
        String query1 = "select * "
                + "from lovac "
                + "where ime like ";
        
        String query2 = "select * "
                + "from lovac "
                + "where ime = ? and prezime like ";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ArrayList<Korisnik> lista = new ArrayList<>();
        try {
            
            
            
            String[] split = kljucnaRec.split("[\\s]");
            
            switch(split.length){
                case 1:
                    query1 += "'" + split[0] + "%'";
                    ps1 = con.prepareStatement(query1);
                    ps1.execute();
                    rs = ps1.getResultSet();
                    break;
                case 2:                                 
                    query2 += "'" + split[1] + "%'";
                    ps2 = con.prepareStatement(query2);
                    ps2.setString(1, split[0]);
                    ps2.execute();
                    rs = ps2.getResultSet();
                    break;
                default:
                    return null;
            }
            
            if(rs == null)
                return null;
            
            while(rs.next()){
                Korisnik k = new Korisnik();
                k.setIme(rs.getString("ime"));
                k.setSrednjeIme(rs.getString("srednjeIme"));
                k.setPrezime(rs.getString("prezime"));
                k.setDatumRodjenja(rs.getDate("datumRodjenja"));
                k.setBrMobilnog(rs.getString("brMobilnog"));
                k.setMesto(rs.getString("mesto"));
                k.setDrzava(rs.getString("drzava"));
                k.setEmail(rs.getString("email"));
                k.setStatus(rs.getBoolean("status"));
                
                Pair<MenuModel, List<String>> pair = LovacDAO.dohOmiljenaLovista(rs.getInt("id"));
                k.setOl_model(pair.getKey());

                
                Pair<MenuModel, List<String>> pair2 = KorisnickaStranaDAO.dohOmiljeneZivotinje(rs.getInt("id"), false);
                k.setOz_model(pair2.getKey());
                
                int val = rs.getInt("vidljivost_podataka");
                
                if((val>>2 & 0x1) == 1)
                    k.setPrikaziMesto(true);
                if((val>>3 & 0x1) == 1)
                    k.setPrikaziDrzava(true);
                if((val>>4 & 0x1) == 1)
                    k.setPrikaziTelefon(true);
                if((val>>5 & 0x1) == 1)
                    k.setPrikaziEmail(true);
                if((val>>6 & 0x1) == 1)
                    k.setPrikaziDatumRodjenja(true);
                if((val>>7 & 0x1) == 1)
                    k.setPrikaziStatus(true);
                if((val>>8 & 0x1) == 1)
                    k.setPrikaziOL(true);
                if((val>>9 & 0x1) == 1)
                    k.setPrikaziOZ(true);
                
                
                lista.add(k);
        
            }
            
            return lista;
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public static void objaviVest(String naslov, String tekst, int privatnost, int kategorija, boolean tip_korisnika, int id, String autor, List<String> odabrani_korisnici, List<UploadedFile> slike) {
        String query = "insert into vesti (naziv, tekst, kategorija, datum, autor, privatnost, arhivirana, tip_korisnika, id_korisnika) "
                + "values (?,?,?,?,?,?,?,?,?)";
        
        String query2 = "insert into nivo_vesti (id_vesti, tip_korisnika, id_korisnika)"
                + "values ";
        
        String query3 = "insert into slike_vest (id_vesti, slika) "
                + "values (?, ?)";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        try {
            
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, naslov);
            ps.setString(2, tekst);
            ps.setInt(3, kategorija);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, autor);
            ps.setInt(6, privatnost);
            ps.setBoolean(7, false);
            ps.setBoolean(8, tip_korisnika);
            ps.setInt(9, id);
            
            ps.executeUpdate();
            
            ResultSet rs_vest = ps.getGeneratedKeys();
            int id_vesti = 0;
            if(rs_vest.next())
                id_vesti = rs_vest.getInt(1);
            
            if(privatnost == 3){
                Pair<List<Integer>, List<Boolean>> pair = KorisnickaStranaDAO.dohIdOdabranihKorisnika(odabrani_korisnici);
                
                
                List<Boolean> tip = pair.getValue();
                List<Integer> korisnik = pair.getKey();
                
                for (int i = 0; i < korisnik.size(); i++) {
                    
                    query2 += "(" + id_vesti + ", " + tip.get(i) + ", " + korisnik.get(i) + "),";
                }
                query2 = query2.substring(0, query2.length()-1);
                
                ps2 = con.prepareStatement(query2);
                
                ps2.executeUpdate();
            
            }
            
            
            if(slike != null){
                ps3 = con.prepareStatement(query3);
                for (UploadedFile slika : slike) {
                    ps3.setInt(1, id_vesti);
                    ps3.setBlob(2, slika.getInputStream());
                    ps3.executeUpdate();
                }
            }
            
            } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static Pair<List<Integer>,List<Boolean>> dohIdOdabranihKorisnika(List<String> odabrani_korisnici){
        
        List<Integer> idOK = new ArrayList<>();
        List<Boolean> tip_korisnika = new ArrayList<>();
        String query1 = "select id "
                + "from lovac "
                + "where korime in ";
        
        String query2 = "select id "
                + "from lovacko_drustvo "
                + "where ime in ";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        boolean flag_lovac = false;
        boolean flag_lv = false;
        try {
            
            query2 += "(";
            query1 += "(";
            for (String korisnici : odabrani_korisnici) {
                if(korisnici.substring(0, 3).equals("LV")){
                    query2 += "'" + korisnici.substring(4) + "',";
                    flag_lv = true;
                } else{
                    String[] split = korisnici.split("[(]");
                    String korime = split[1];
                    query1 += "'" + korime.substring(0, korime.length()-1) + "',";
                    flag_lovac = true;
                }
            }
            
            if(flag_lv){
                //brisem zarez i zatvaram
                query2 = query2.substring(0, query2.length()-1);
                query2 += ")";

                ps2 = con.prepareStatement(query2);
                ps2.execute();

                rs = ps2.getResultSet();

                while(rs.next()){   //dodao sam sva lovacka drustva koja su selektovana
                    idOK.add(rs.getInt(1));
                    tip_korisnika.add(true);
                }
            }
            
            if(flag_lovac){
                //brisem zarez i zatvaram
                query1 = query1.substring(0, query1.length()-1);
                query1 += ")";
                
                ps1 = con.prepareStatement(query1);
                ps1.execute();

                rs = ps1.getResultSet();

                while(rs.next()){   //dodao sam sva lovacka drustva koja su selektovana
                    idOK.add(rs.getInt(1));
                    tip_korisnika.add(false);
                }
            }
            return new Pair<List<Integer>,List<Boolean>>(idOK,tip_korisnika);
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
        
    }
    
    public static List<String> dohvatiKategorijeVesti() {
        String query1 = "select kategorija "
                + "from kategorija_vesti "
                + "where id > 0 and kategorija <> ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setString(1, "brisanje");
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            while(rs.next())
                lista.add(rs.getString(1));
            
            return lista;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    public static int dohvatiKategoriju(String kategorija_vesti) {
        String query1 = "select id "
                + "from kategorija_vesti "
                + "where kategorija = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setString(1, kategorija_vesti);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            if(rs.next())
                return rs.getInt(1);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return 0;
    }

    public static String dohKategorijuString(int kat) {
        String query1 = "select kategorija "
                + "from kategorija_vesti "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setInt(1, kat);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            if(rs.next())
                return rs.getString(1);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return "Greska";
    }
    
    public static Pair<MenuModel, List<String>> dohOmiljeneZivotinje(int id, boolean tip_korisnika) {
        String query = "select z.naziv "
                + "from zivotinje z "
                + "where z.id in (select oz.id_zivotinje "
                + "from omiljene_zivotinje oz "
                + "where oz.id_korisnika = ? and oz.tip_korisnika = ?)";
        
        MenuModel oz_model = new DefaultMenuModel();
        List<String> omiljene_zivotinje = new ArrayList<>();
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            
            ps.setInt(1, id);
            ps.setBoolean(2, tip_korisnika);
            
            ps.execute();
            
            rs = ps.getResultSet();
            
            while(rs.next()){
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(rs.getString("naziv"));
                omiljene_zivotinje.add(rs.getString("naziv"));
                oz_model.addElement(item);
            }
            
            
            return new Pair<MenuModel,List<String>>(oz_model,omiljene_zivotinje);
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public static List<String> dohZivotinje() {
        String query = "select * "
                + "from zivotinje "
                + "where id > 0";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps = con.prepareStatement(query);
            
            ps.execute();
            
            rs = ps.getResultSet();
            
            while(rs.next())
                lista.add(rs.getString("naziv"));
            
            
            return lista;
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public static void generisiZahtevZaBrisanje(int vest_id) {
        String query1 = "update vesti"
                + " set zahtev_za_brisanje = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setBoolean(1, true);
            ps1.setInt(2, vest_id);
            ps1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void ponistiBrisanje(int vest_id) {
        String query1 = "update vesti"
                + " set zahtev_za_brisanje = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setBoolean(1, false);
            ps1.setInt(2, vest_id);
            ps1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void prigovorNaBrisanje(int vest_id) {
        String query1 = "update vesti"
                + " set flag_obrisi = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setBoolean(1, true);
            ps1.setInt(2, vest_id);
            ps1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static List<Drustvo> pretraziLovackaDrustva(String kljucnaRec) {
        String query1 = "select * "
                + "from lovacko_drustvo "
                + "where ime like ";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        ArrayList<Drustvo> lista = new ArrayList<>();
        try {
            
            query1 += "'" + kljucnaRec + "%'";
            ps1 = con.prepareStatement(query1);
            ps1.execute();
            rs = ps1.getResultSet();

            if(rs == null)
                return null;
            
            while(rs.next()){
                Drustvo d = new Drustvo();
                d.setIme(rs.getString("ime"));
                d.setEmail(rs.getString("email"));
                d.setStatus(rs.getBoolean("status"));
                d.setBroj_clanova(rs.getInt("broj_clanova"));
                d.setIznos_clanarine(rs.getInt("iznos_clanarine"));
                Pair<MenuModel, List<String>> pair2 = KorisnickaStranaDAO.dohOmiljeneZivotinje(rs.getInt("id"), true);
                d.setOz_model(pair2.getKey());
                
                int val = rs.getInt("vidljivost_podataka");
                
                
                if((val>>1 & 0x1) == 1)
                    d.setPrikaziEmail(true);
                if((val>>2 & 0x1) == 1)
                    d.setPrikaziStatus(true);
                if((val>>3 & 0x1) == 1)
                    d.setPrikaziBrojClanova(true);
                if((val>>4 & 0x1) == 1)
                    d.setPrikaziIznosClanarine(true);
                if((val>>5 & 0x1) == 1)
                    d.setPrikaziOZ(true);
                
                
                lista.add(d);
        
            }
            
            return lista;
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    public static List<byte[]> dohSlikeKorisnika(String korime, boolean tip_korisnika) {
        String query1 = "select slika "
                + "from slike "
                + "where tip_korisnika = ? and id_korisnika = ?";
        String query2 = "select id "
                + "from lovac "
                + "where korime = ?";
        String query3 = "select id "
                + "from lovacko_drustvo "
                + "where email = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        List<byte[]> slike = new ArrayList<>();
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            
            if(tip_korisnika){
                ps3.setString(1, korime);
                ps3.execute();
                rs = ps3.getResultSet();
            }
            else{
                ps2.setString(1, korime);
                ps2.execute();
                rs = ps2.getResultSet();
            }
            
            if(rs.next()){
                int id = rs.getInt("id");
                ps1.setBoolean(1, tip_korisnika);
                ps1.setInt(2, id);
                ps1.execute();
                
                rs = ps1.getResultSet();
            
                while(rs.next()){
                    slike.add(rs.getBlob("slika").getBytes(1, (int) rs.getBlob("slika").length()));
                }
            }
                
            
            return slike;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(LovacDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void objaviSlike(String korime, boolean tip_korisnika, List<UploadedFile> uploadSlike) {
        String query1 = "insert into slike (tip_korisnika, id_korisnika, slika) "
                + "values (?, ?, ?)";
        String query2 = "select id "
                + "from lovac "
                + "where korime = ?";
        String query3 = "select id "
                + "from lovacko_drustvo "
                + "where email = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        try {
            
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            if(tip_korisnika){
                ps3.setString(1, korime);
                ps3.execute();
                rs = ps3.getResultSet();
            }
            else{
                ps2.setString(1, korime);
                ps2.execute();
                rs = ps2.getResultSet();
            }
            int id = 0;
            if(rs.next())
                id = rs.getInt("id");
            
            if(uploadSlike != null){
                ps1 = con.prepareStatement(query1);
                for (UploadedFile slika : uploadSlike) {
                    ps1.setBoolean(1, tip_korisnika);
                    ps1.setInt(2, id);
                    ps1.setBlob(3, slika.getInputStream());
                    ps1.executeUpdate();
                }
            }
            
            } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(KorisnickaStranaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }

    public static List<Vest> dohJavneVesti() {
        String query1 = "select * "
                + "from vesti "
                + "where  privatnost = ?";
        String query3 = "select slika "
                + "from slike_vest "
                + "where id_vesti = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps3 = null;
        ArrayList<Vest> lista = new ArrayList<>();
        try {
            ps1 = con.prepareStatement(query1);
            ps3 = con.prepareStatement(query3);
            
            ps1.setInt(1, 4);
            ps1.execute();
            
            ResultSet rs = ps1.getResultSet();
            
            while(rs.next()){
                Vest v = new Vest();
                v.setAutor(rs.getString("autor"));
                v.setDatum(rs.getTimestamp("datum"));
                v.setNaziv(rs.getString("naziv"));
                v.setTekst(rs.getString("tekst"));
                v.setKategorija(rs.getInt("kategorija"));
                v.setId(rs.getInt("id"));
                v.setId_korisnika(rs.getInt("id_korisnika"));
                v.setArhivirana(rs.getBoolean("arhivirana"));
                v.setZahtev_za_brisanje(rs.getBoolean("zahtev_za_brisanje"));
                v.setFlag_obrisi(rs.getBoolean("flag_obrisi"));
                //dohvatam iz baze slike ako ih ima u vesti
                ps3.setInt(1, v.getId());
                ps3.execute();
                ResultSet rs3 = ps3.getResultSet();
                List<byte[]> slike = new ArrayList<>();
                while(rs3.next()){
                    slike.add(rs3.getBlob("slika").getBytes(1, (int) rs3.getBlob("slika").length()));
                }
                v.setSlike(slike);
                
                lista.add(v);
            }
            
            return lista;
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    public static List<Admin> dohAdministratore() {
        String query1 = "select * "
                + "from administrator "
                + "where  id > 0";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ArrayList<Admin> lista = new ArrayList<>();
        try {
            ps1 = con.prepareStatement(query1);
            ps1.execute();
            
            ResultSet rs = ps1.getResultSet();
            
            while(rs.next()){
                Admin a = new Admin();
                
                a.setEmail(rs.getString("email"));
                a.setIme(rs.getString("ime"));
                a.setPrezime(rs.getString("prezime"));
                
                lista.add(a);
            }
            
            return lista;
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.getInstance().putConnection(con);
                if (ps1 != null) {
                    ps1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    
}
