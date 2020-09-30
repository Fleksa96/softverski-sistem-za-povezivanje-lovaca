/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Admin;
import beans.Drustvo;
import beans.Korisnik;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class LoginDAO {
    
    public static final String kljuc = "encrypt_password";
    
    public static Pair<Object, Integer> dohvatiKorisnika(String korime, String lozinka){
        String query1 = "Select * "
                + "from lovac "
                + "where korime = ? and lozinka = HEX(AES_ENCRYPT(?,?))";
        
        String query2 = "Select * "
                + "from lovacko_drustvo "
                + "where email = ? and lozinka = HEX(AES_ENCRYPT(?,?))";
        
        String query3 = "Select * "
                + "from administrator "
                + "where korime = ? and lozinka = HEX(AES_ENCRYPT(?,?))";
        
        String query4 = "Select * "
                + "from lovac "
                + "where korime = ? and verifikacija = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        ResultSet rs = null;
        try {
            
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            ps4 = con.prepareStatement(query4);
            
            Korisnik k = new Korisnik();
            Drustvo d = new Drustvo();
            Admin a = new Admin();
            
            ps1.setString(1, korime);
            ps1.setString(2, lozinka);
            ps1.setString(3, kljuc);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            if(rs.next()){
                
                ps4.setString(1, rs.getString("korime"));
                ps4.setBoolean(2, false);
                ps4.execute();
                ResultSet rs4 = ps4.getResultSet();
                //////////postoji korisnik ali jos uvek nije verifikovao svoj nalog////
                if(rs4.next()){
                    return new Pair<Object, Integer>(k, 4);
                }
                ////postoji korisnik i verifikovao je svoj nalog
                postaviVrednostiLovca(k, rs, 1);
                return new Pair<Object, Integer>(k, 1);
            }
            
            ps2.setString(1, korime);
            ps2.setString(2, lozinka);
            ps2.setString(3, kljuc);
            ps2.execute();
            
            rs = ps2.getResultSet();
            
            if(rs.next()){      //lovacko drustvo
                postaviVrednostiLovackogDrustva(d, rs, 2);
                return new Pair<Object, Integer>(d, 2);
            }
            
            ps3.setString(1, korime);
            ps3.setString(2, lozinka);
            ps3.setString(3, kljuc);
            ps3.execute();
            
            rs = ps3.getResultSet();
            
            if(rs.next()){  //admin
                postaviVrednostiAdmina(a, rs, 3);
                return new Pair<Object, Integer>(a, 3);
            }
            
            return null;
            
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
                if (ps3 != null) {
                    ps3.close();
                }
                if (ps4 != null) {
                    ps4.close();
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
    
    private static void postaviVrednostiAdmina(Admin a, ResultSet rs, int tip) throws SQLException{
        a.setIme(rs.getString("ime"));
        a.setPrezime(rs.getString("prezime"));
        a.setEmail(rs.getString("email"));
    }
    
    private static void postaviVrednostiLovca(Korisnik k, ResultSet rs, int tip) throws SQLException{
        k.setId(rs.getInt("id"));
        k.setKorime(rs.getString("korime"));
        k.setIme(rs.getString("ime"));
        k.setPrezime(rs.getString("prezime"));
        k.setDatumRodjenja(rs.getDate("datumRodjenja"));
        k.setBrMobilnog(rs.getString("brMobilnog"));
        k.setMesto(rs.getString("mesto"));
        k.setDrzava(rs.getString("drzava"));
        k.setEmail(rs.getString("email"));
        k.setLozinka(rs.getString("lozinka"));
        k.setStatus(rs.getBoolean("status"));
        
        k.setTip(tip);
    }
    
    private static void postaviVrednostiLovackogDrustva(Drustvo d, ResultSet rs, int tip) throws SQLException{
        d.setId(rs.getInt("id"));
        d.setIme(rs.getString("ime"));
        d.setEmail(rs.getString("email"));
        d.setLozinka(rs.getString("lozinka"));
        d.setStatus(rs.getBoolean("status"));
        d.setBroj_clanova(rs.getInt("broj_clanova"));
        d.setIznos_clanarine(rs.getInt("iznos_clanarine"));
    }
    
}
