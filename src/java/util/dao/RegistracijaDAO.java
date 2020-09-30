/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class RegistracijaDAO {
    
    public static final String kljuc = "encrypt_password";

    public static int DodajLovca(Korisnik k) {
        String query1 = "insert into lovac(ime, prezime, srednjeIme, mesto, drzava, brojClanskeKarte, imeLovackogDrustva, brMobilnog, email, status, datumRodjenja, korime, lozinka, verifikacija, poslednja_aktivnost)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,HEX(AES_ENCRYPT(?,?)),?,?)";
        //////korisnicko ime
        String query2 = "select * "
                + "from lovac "
                + "where korime = ?";
        String query3 = "select * "
                + "from administrator "
                + "where korime = ?";
        //////email
        String query5 = "select * "
                + "from lovacko_drustvo "
                + "where email = ?";
        String query6 = "select * "
                + "from lovac "
                + "where email = ?";
        String query7 = "select * "
                + "from administrator "
                + "where email = ?";
        ////udruzenje
        String query4 = "select * "
                + "from lovac "
                + "where brojClanskeKarte = ? and imeLovackogDrustva = ?";
        
        ///insert key into activation
        String query8 = "insert into activation(korime, kljuc) "
                + "values(?,?)";
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;
        ResultSet rs = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            ps4 = con.prepareStatement(query4);
            ps5 = con.prepareStatement(query5);
            ps6 = con.prepareStatement(query6);
            ps7 = con.prepareStatement(query7);
            ps8 = con.prepareStatement(query8);
            
            
/////////////provera da li postoji korisnicko ime u tabelama lovac i administrator/////   
            ps2.setString(1, k.getKorime());
            ps3.setString(1, k.getKorime());
            
            ps2.execute();
            ps3.execute();
            
            rs = ps2.getResultSet();
            
            if(rs.next()){
                return 0;
            }
            
            rs = ps3.getResultSet();
            
            if(rs.next()){
                return 0;
            }

    ////////provera da li postoje neki emailovi koji su isti
            ps5.setString(1, k.getEmail());
            ps6.setString(1, k.getEmail());
            ps7.setString(1, k.getEmail());
            
            ps5.execute();
            ps6.execute();
            ps7.execute();
            
            rs = ps5.getResultSet();
            
            if(rs.next()){
                return 1;
            }
            
            rs = ps6.getResultSet();
            
            if(rs.next()){
                return 1;
            }
            
            rs = ps7.getResultSet();
            
            if(rs.next()){
                return 1;
            }
            
    ////////provera da li postoji udruzenje koje vec ima osobu sa datom clanskom kartom
            if (k.getImeLovackogDrustva() != null && !k.getImeLovackogDrustva().isEmpty()) {
                ps4.setString(1, k.getBrojClanskeKarte());
                ps4.setString(2, k.getImeLovackogDrustva());
                ps4.execute();

                rs = ps4.getResultSet();

                if (rs.next()) {//lovacko drustvo ista licenca
                    return 2;
                }
            }
            //////ubacujem korisnika
            ps1.setString(1, k.getIme());
            ps1.setString(2, k.getPrezime());
            ps1.setString(3, k.getSrednjeIme());
            ps1.setString(4, k.getMesto());
            ps1.setString(5, k.getDrzava());
            ps1.setString(6, k.getBrojClanskeKarte());
            ps1.setString(7, k.getImeLovackogDrustva());
            ps1.setString(8, k.getBrMobilnog());
            ps1.setString(9, k.getEmail());
            ps1.setBoolean(10, k.getStatus());
            ps1.setDate(11, new Date(k.getDatumRodjenja().getTime()));
            ps1.setString(12, k.getKorime());
            ps1.setString(13, k.getLozinka());
            ps1.setString(14, kljuc);
            ps1.setBoolean(15, false);
            ps1.setDate(16, new Date(System.currentTimeMillis()));

            ps1.executeUpdate();
            
            /////////ubacujem u tabelu activation
            ps8.setString(1, k.getKorime());
            ps8.setString(2, k.getKey());
            
            ps8.executeUpdate();

            return 3;

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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    
}
