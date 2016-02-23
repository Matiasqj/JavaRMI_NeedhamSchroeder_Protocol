/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz_implementaciones;

import interfaz.InterfazCliente;
import interfaz.InterfazServidor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mysql.ConsultasSQL;
import servidorRMI.Cliente;
import swing.Server_Ventana;

/**
 *
 * @author Matias Quinteros
 */
public class ImplementacionServidor extends UnicastRemoteObject implements InterfazServidor{
    public ArrayList clientes = new ArrayList();
    private Connection con;
    private Statement stm, stm1, stm2, stm3;
    private ResultSet rs, rs1, rs2, rs3;
    
    public ImplementacionServidor()throws RemoteException{
    
        super();
    }
      
    @Override
    public synchronized int registrarCliente(InterfazCliente cliente, String Nombre, String password) throws RemoteException{
        
          if (!(clientes.contains(cliente))) {
            
            
            //Server_Ventana ventana = getServer_Ventana();
            Server_Ventana.getServer_Ventana().Actualizar_Log_Usuario_Conexion(Nombre);
            ConsultasSQL sql = new ConsultasSQL();
            int retorno = sql.Iniciar_sesion(Nombre, password);
            if(retorno>0){
                
                clientes.add(cliente);
                Cliente client_nuevo = new Cliente();
                client_nuevo.setId(retorno);
                client_nuevo.setNombreCliente(Nombre);
                client_nuevo.setPass(password);
                Server_Ventana.getServer_Ventana().Online.add(client_nuevo);
            }
            return retorno;
                   
            //clientesNombre.addElement(Nombre);
            
            /*for (int i = 0;i < clientes.size();i++){
                InterfazCliente nextClient = (InterfazCliente)clientes.get(i);
                
            }*/
        }
          return -1;
    }
      
      
       public synchronized void enviarMensaje(String mensaje) throws RemoteException{
        //clientesNombre.addElement(Nombre);
           
       ArrayList<String[]> listaonline = new ArrayList<String[]>();
        int ubicacion=-1;
        for(int i = 0; i< Server_Ventana.getServer_Ventana().Online.size();i++){
            if(Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente().equals(mensaje))
                //con esto se quien lo envio
                ubicacion = i;
        }
        InterfazCliente nextClient = (InterfazCliente)clientes.get(ubicacion);
        
        nextClient.notificar("solo a ti ");
    } 
      
       public synchronized void Nada() throws RemoteException{
           System.out.println("hola ");
      }
       
    public synchronized ArrayList<String[]> enviarOnline() throws RemoteException{
        ArrayList<String[]> listaonline = new ArrayList<String[]>();
        int ubicacion=-1;
        for(int i = 0; i< Server_Ventana.getServer_Ventana().Online.size();i++){
            String[] datos = new String[2];
            datos[0]= String.valueOf(Server_Ventana.getServer_Ventana().Online.get(i).getId());
            datos[1]=Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente();
            listaonline.add(datos);
          
        }
        return listaonline;
    
    }
}
