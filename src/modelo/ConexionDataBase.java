/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ASUS
 */
public class ConexionDataBase {
    
    public static final String URL = ("jdbc:mysql://127.0.0.1:3306/polieatsdatos?useSSL=false");
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1993";
    
    public static Connection getConection() throws Exception {
            Class.forName("com.mysql.jdbc.Driver");
            
           // JOptionPane.showMessageDialog(null, "Conexion Exitosa");
 
        return  (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
