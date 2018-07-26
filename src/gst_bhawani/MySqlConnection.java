
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gst_bhawani;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */
public class MySqlConnection {
    
     //   private static final String username = "lokesh";
     //   private static final String password = "lokesh";
        private static final String username = "root";
        private static final String password = "sony";
        private static final String CONN_STRING="jdbc:mysql://localhost:3306/bhawanibill";    //Loading JDBC driver for SQL
     //   private static final String CONN_STRING="jdbc:mysql://192.168.43.232/bhawanibill";
        static Connection conn = null;
        MySqlConnection()
    {
       
        
        
        
        try
        {
            conn = DriverManager.getConnection(CONN_STRING,username,password); //Connection Established between netbeans and SQL
            System.out.println("Connected Database");                          //Database connection Successful message
        }
        catch(SQLException e)
        {
            System.out.println(e);
             JOptionPane.showMessageDialog(null, "Data Base Error...!!!!!!");
        }
    }
    public static void main(String args[])
    {
        MySqlConnection my = new MySqlConnection();
    }
}