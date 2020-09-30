/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Aleksaa
 */
public class IzmeniLozinkuDAO {

    public static final String kljuc = "encrypt_password";
    
    public static void izmeniLozinku(String korime, String nova_lozinka) {
        String query1 = "update lovac"
                + " set lozinka = HEX(AES_ENCRYPT(?,?)) "
                + "where korime = ?";
        
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        try {
            ps1 = con.prepareStatement(query1);
            
            ps1.setString(1, nova_lozinka);
            ps1.setString(2, kljuc);
            ps1.setString(3, korime);
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
    
}
