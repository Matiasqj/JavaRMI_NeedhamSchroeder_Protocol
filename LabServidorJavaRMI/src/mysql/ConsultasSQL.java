/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import swing.Server_Ventana;

/**
 *
 * @author Matias Quinteros
 * Contiene funciones para realizar consultas en SQL
 */
public class ConsultasSQL {
    Server_Ventana ventana_servidor;
    /**
     * Contructor
     * @param ventana 
     */
    public ConsultasSQL(Server_Ventana ventana) {
        ventana_servidor =ventana;
    }
    public ConsultasSQL() {
        
    }
    
    /**
     * Se encarga de verificar si los campos ingresados corresponden a los guardados en la BD
     * @param nombre
     * @param password
     * @return el id si los passwords y usuario son correctos, caso contrario retorna -1
     */
    public int Iniciar_sesion(String nombre,String password){
        
        String[] titulo = {"Id", "Nombre Usuario", "Contraseña"};
        String query = "SELECT * FROM usuario";//query a realizar
        try {
            //Establece la conexion con mysql
            ConexionMySQL mysql = new ConexionMySQL();
            Connection on = mysql.Conectar();
           if(on ==null)//si hay un error al conectar
               ventana_servidor.Actualizar_Log("Ocurrió un error al conectar con la BD mysql");
            
            Statement st = on.createStatement();//prepara el statement
            ResultSet rs = st.executeQuery(query);//ejecuta la consulta
            while (rs.next()) {//por todos los usuarios
                String[] fila;
                fila = new String[3];
                /**
                 * Capto los datos, id, nombre de usuario y apssword
                 */
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre_usuario");
                fila[2] = rs.getString("password");
                if(fila[1].equals(nombre) && fila[2].equals(password))//si el usuario y el password coinciden
                    return Integer.valueOf(fila[0]);//retorno un entero con el id
                
            }
           rs.close();//cierra el statement 
           on.close();//cierra la conexion
               return -1;//si no encontro el valor retorna -1
        }
        catch(SQLException ex) {//si existe error
            //JOptionPane.showMessageDialog(null, ex);
            Server_Ventana.getServer_Ventana().Actualizar_Log_Usuario_ErrorSQL(ex.getMessage());
        }
        //retorna -1 caso contrario
        return -1;
    }
    
}
