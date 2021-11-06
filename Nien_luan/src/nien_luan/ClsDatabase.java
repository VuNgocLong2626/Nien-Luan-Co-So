/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nien_luan;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author longs
 */
public class ClsDatabase {
    public static Connection con;
    public static boolean open(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nienluan", "root", "long2000");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;                    
        }
    }
    
    public static boolean close(){
        try {
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
