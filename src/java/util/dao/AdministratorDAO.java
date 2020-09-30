/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Vest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class AdministratorDAO {
    
    public static List<Vest> dohJavneVestiZaBrisanje() {
        String query1 = "select * "
                + "from vesti "
                + "where  id > 0 and zahtev_za_brisanje = 1";
        
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

    public static boolean unesiKategoriju(String kategorija_vesti) {
        String query1 = "select * "
                + "from kategorija_vesti "
                + "where  kategorija  = ?";
        
        String query2 = "insert into kategorija_vesti(kategorija) "
                + "values (?)";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            
            ps1.setString(1, kategorija_vesti);
            ps1.execute();
            
            ResultSet rs = ps1.getResultSet();
            
            if(rs.next())
                return false;
            
            ps2.setString(1, kategorija_vesti);
            ps2.executeUpdate();
            
            return true;
            
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
        return false;
    }

    public static void obrisiVest(int id) {
        String query1 = "delete from vesti "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps1.setInt(1, id);
            ps1.executeUpdate();
            
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
    }

    public static void ponistiBrisanje(int id) {
        String query1 = "update vesti "
                + "set flag_obrisi = ?, zahtev_za_brisanje = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps1.setBoolean(1, false);
            ps1.setBoolean(2, false);
            ps1.setInt(3, id);
            ps1.executeUpdate();
            
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
    }
}
