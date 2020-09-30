/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import controllers.PosaljiEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.krb5.internal.crypto.Aes128;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class PocetnaStranicaDAO {

    public static final String kljuc = "encrypt_password";
    
    public static boolean verifikujKorisnika(String email) {
        
        String query1 = "select korime, AES_DECRYPT(UNHEX(lozinka),?) "
                + "from lovac "
                + "where email = ?";
        
        String query2 = "select AES_DECRYPT(UNHEX(lozinka),?) "
                + "from lovacko_drustvo "
                + "where email = ?";
        
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        
        try {
            ps1 = con.prepareStatement(query1);
            ps2 = con.prepareStatement(query2);
            
            ps1.setString(1, kljuc);
            ps1.setString(2, email);
            ps1.execute();
            
            rs = ps1.getResultSet();
            
            if(rs.next()){
                String s = "Vase korisnicko ime i lozinka su: \n" + "Krisnicko ime: " + rs.getString("korime") + "\nLozinka: " + rs.getString(2);
                PosaljiEmail se = new PosaljiEmail(email, "***", s);
                return true;
            }
            
            ps1.setString(1, kljuc);
            ps1.setString(2, email);
            ps2.execute();
            
            rs = ps2.getResultSet();
            
            if(rs.next()){
                String s = "Vase korisnicko ime i lozinka su: \n" + "Email: " + email + "\nLozinka: " + rs.getString(1);
                PosaljiEmail se = new PosaljiEmail(email, "***", s);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DB.getInstance().putConnection(con);
                if(ps1 != null)
                    ps1.close();
                if(ps2 != null)
                    ps2.close();
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PocetnaStranicaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
}
