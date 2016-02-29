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
 * Clase que se encarga de establecer conexion con la BD
 */
public class ConexionMySQL {
    public String db = "dataserver_rmi";//nombre de la bd
    public String url = "jdbc:mysql://localhost/"+db;//direccion del jdbc
    public String user = "root";//usuario de la db
    public String pass = "";//pass de la db
    //constructor
      public ConexionMySQL()
    {
        
    }
    /***
     * Se encarga de conectar con la bd
     * @return nulo si no puede conectar, sino retorna un objeto del tipo Connection
     */
    public Connection Conectar()
    {
        Connection link = null;
        try
        {
            //Cargamos el Driver MySQL
            Class.forName("org.gjt.mm.mysql.Driver");
            //Creamos un enlace hacia la base de datos
            link = DriverManager.getConnection(this.url, this.user, this.pass);//establece la conexion
        }
        catch (Exception e)//si existe error al conectar con la bd
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
    
    
}
