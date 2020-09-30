/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class AktivacijaDAO {
    
    public static boolean dohKljuc(String kljuc){
        
        String query1 = "select korime "
                + "from activation "
                + "where kljuc = ?";
        
        String query2 = "select * "
                + "from lovac "
                + "where korime = ? and verifikacija = ?";
        
        String query3 = "select * "
                + "from lovac "
                + "where korime = ? and verifikacija = ? and poslednja_aktivnost between ? and ?";
        
        String query4 = "delete from lovac "
                + "where korime = ?";
        String query5 = "delete from activation "
                + "where korime = ?";
        
        String query6 = "update lovac "
                + "set verifikacija = ?, poslednja_aktivnost = ? "
                + "where korime = ?";
        
        String korime = null;
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        ResultSet rs = null;
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            ps3 = con.prepareStatement(query3);
            ps4 = con.prepareStatement(query4);
            ps5 = con.prepareStatement(query5);
            ps6 = con.prepareStatement(query6);
            
            ps1.setString(1, kljuc);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            if(rs.next())
                korime = rs.getString(1);
            else 
                return false;
            
            ps2.setString(1, korime);
            ps2.setBoolean(2, true);
            ///////////////////nalog je vec verifikovan, tako da svejedno samo cu ispisati da je uspesno verifikovan
            ps2.execute();
            
            rs = ps2.getResultSet();
            
            if(rs.next())
                return true;
            
            ps3.setString(1, korime);
            ps3.setBoolean(2, false);
            ps3.setDate(3, new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)));
            ps3.setDate(4, new Date(System.currentTimeMillis()));
            ////////////////nalog se prvi put verifikuje i ne sme biti stariji od 3 dana
            ps3.execute();
            
            rs = ps3.getResultSet();
            
            if(rs.next()){
                ps6.setBoolean(1, true);
                ps6.setDate(2, new Date(System.currentTimeMillis()));
                ps6.setString(3, korime);
                ps6.executeUpdate();
                return true;
            }
            
            //////////ukoliko nalog postoji a nije verifikovan u roku od 3 dana brisem ga iz baze i takodje ga brisem iz aktivacione tabele da nju ne bih prepunio
            ps4.setString(1, korime);
            ps4.executeUpdate();
            
            ps5.setString(1, korime);
            ps5.executeUpdate();
            
            
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(ps2 != null)
                    ps2.close();
                if(ps3 != null)
                    ps3.close();
                if(ps4 != null)
                    ps4.close();
                if(ps5 != null)
                    ps5.close();
                if(ps6 != null)
                    ps6.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AktivacijaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
}
