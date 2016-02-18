/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Matias Quinteros
 */
public class ConexionMySQL {
    public String db = "dataserver_rmi";
    public String url = "jdbc:mysql://localhost/"+db;
    public String user = "root";
    public String pass = "";
    
      public ConexionMySQL()
    {
        
    }
    
    public Connection Conectar()
    {
        Connection link = null;
        try
        {
            //Cargamos el Driver MySQL
            Class.forName("org.gjt.mm.mysql.Driver");
            //Creamos un enlace hacia la base de datos
            link = DriverManager.getConnection(this.url, this.user, this.pass);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
    
    
}
