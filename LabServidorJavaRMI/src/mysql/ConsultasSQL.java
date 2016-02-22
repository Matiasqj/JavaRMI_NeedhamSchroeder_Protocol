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
 */
public class ConsultasSQL {
    Server_Ventana ventana_servidor;
    public ConsultasSQL(Server_Ventana ventana) {
        ventana_servidor =ventana;
    }
    public ConsultasSQL() {
        
    }
    public int Iniciar_sesion(String nombre,String password){
          String[] titulo = {"Id", "Nombre Usuario", "Contraseña"};
        
        String query = "SELECT * FROM usuario";
        try {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection on = mysql.Conectar();
           
            
            Statement st = on.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String[] fila;
                fila = new String[3];

                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre_usuario");
                fila[2] = rs.getString("password");
                if(fila[1].equals(nombre) && fila[2].equals(password))
                    return Integer.valueOf(fila[0]);

            }
           rs.close();
           
               return -1;
        }
        catch(SQLException ex) {
            //JOptionPane.showMessageDialog(null, ex);
            Server_Ventana.getServer_Ventana().Actualizar_Log_Usuario_ErrorSQL(ex.getMessage());
        }
        
        return -1;
    }
    
}
