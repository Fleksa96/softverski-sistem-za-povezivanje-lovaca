/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Drustvo;
import beans.Korisnik;
import beans.Vest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.primefaces.model.menu.MenuModel;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class LovackoDrustvoDAO {
    
    
    public static boolean sacuvajPodatke(Drustvo d, List<String> omiljene_zivotinje, boolean izmena_zivotinje) {
        String query1 = "update lovacko_drustvo "
                + "set ime = ?, email = ?, status = ?, broj_clanova = ?, iznos_clanarine = ? "
                + "where id = ?";
        
        
        //////email
        String query2 = "select * "
                + "from lovacko_drustvo "
                + "where email = ?";
        String query3 = "select * "
                + "from lovac "
                + "where email = ? and id <> ?";
        String query4 = "select * "
                + "from administrator "
                + "where email = ?";
       
        
        String query8 = "delete from omiljene_zivotinje "
                + "where id_korisnika = ? and tip_korisnika = ?";
        String query9 = "insert into omiljene_zivotinje "
                + "values ";
        String query10 = "select id "
                + "from zivotinje "
                + "where naziv in ";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        PreparedStatement ps9 = null;
        PreparedStatement ps10 = null;
        ResultSet rs = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            ps4 = con.prepareStatement(query4);
            ps8 = con.prepareStatement(query8);
            
            
            
            
            ////////provera da li postoje neki emailovi koji su isti
            ps2.setString(1, d.getEmail());
            ps3.setString(1, d.getEmail());
            ps3.setInt(2, d.getId());
            ps4.setString(1, d.getEmail());
            
            ps2.execute();
            ps3.execute();
            ps4.execute();
            
            rs = ps2.getResultSet();
            
            if(rs.next()){
                return false;
            }
            
            rs = ps3.getResultSet();
            
            if(rs.next()){
                return false;
            }
            rs = ps4.getResultSet();
            
            if(rs.next()){
                return false;
            }
            
            
            ps1.setString(1, d.getIme());
            ps1.setString(2, d.getEmail());
            ps1.setBoolean(3, d.isStatus());
            ps1.setInt(4, d.getBroj_clanova());
            ps1.setInt(5, d.getIznos_clanarine());
            ps1.setInt(6, d.getId());
            
            ps1.executeUpdate();
            
            
            
            if(izmena_zivotinje){
                //brisem prethodne omiljene zivotinje
                ps8.setInt(1, d.getId());
                ps8.setBoolean(2, true);
                ps8.executeUpdate();
                
                query10 += "(";
                for (String zivotinja : omiljene_zivotinje) {
                    query10 += "'" + zivotinja + "',";
                }
                query10 = query10.substring(0, query10.length()-1);   //brisem poslednji zarez
                query10 += ")";
                
                ps10 = con.prepareStatement(query10); //dohvatam id-ijeve zivotinja
                ps10.execute();
                
                rs = ps10.getResultSet();
                
                while(rs.next()){
                    query9 += "(" + d.getId() + ", " + rs.getInt(1) + ", " + true + "),";
                }
                query9 = query9.substring(0, query9.length()-1);   //brisem poslednji zarez
                
                ps9 = con.prepareStatement(query9);
                
                ps9.executeUpdate();
               
            }
            
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
                if (ps3 != null) {
                    ps3.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
                if (ps5 != null) {
                    ps5.close();
                }
                if (ps6 != null) {
                    ps6.close();
                }
                if (ps7 != null) {
                    ps7.close();
                }
                if (ps8 != null) {
                    ps8.close();
                }
                if (ps9 != null) {
                    ps9.close();
                }
                if (ps10 != null) {
                    ps10.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }

    public static int dohvatiVidljivostPodataka(int id) {
        String query1 = "select vidljivost_podataka "
                + "from lovacko_drustvo "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setInt(1, id);
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


    public static void sacuvajVidljivostPodataka(int id, int val) {
        String query1 = "update lovacko_drustvo "
                + "set vidljivost_podataka = ? "
                + "where id = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setInt(1, val);
            ps1.setInt(2, id);
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
    
    public static List<String> dohSveKorisnike(int id) {
        String query1 = "select * "
                + "from lovac "
                + "where id > 0";
        
        String query2 = "select * "
                + "from lovacko_drustvo "
                + "where id > 0 and id <> ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ArrayList<String> lista = new ArrayList<>();
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps2.setInt(1, id);
            ps1.execute();
            ps2.execute();
            
            rs = ps1.getResultSet();
            
            
            while(rs.next())
                lista.add("L: " + rs.getString("ime") + " " + rs.getString("srednjeIme").charAt(0) + ". " + rs.getString("prezime") + "(" +rs.getString("korime")+ ")");
            
            rs = ps2.getResultSet();
            
            
            while(rs.next())
                lista.add("LD: " + rs.getString("ime"));
            
            
            
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
    
}

