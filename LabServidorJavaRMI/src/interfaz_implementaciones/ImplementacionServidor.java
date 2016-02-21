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
      
    public synchronized void registrarCliente(InterfazCliente cliente, String Nombre) throws RemoteException{
        System.out.println("Registrando un cliente");
          if (!(clientes.contains(cliente))) {
            System.out.println("Cliente registrado");
            clientes.add(cliente);
            //clientesNombre.addElement(Nombre);
            for (int i = 0;i < clientes.size();i++){
                InterfazCliente nextClient = (InterfazCliente)clientes.get(i);
                
            }
        }
    }
      
      
       public synchronized void enviarMensaje(String mensaje) throws RemoteException{
        //clientesNombre.addElement(Nombre);
           System.out.println("LLegue aca en el servidor");
        for (int i = 0; i <clientes.size();i++){
            InterfazCliente nextClient = (InterfazCliente)clientes.get(i);
               
            nextClient.notificar(mensaje);
        }  
    } 
      
       public synchronized void Nada() throws RemoteException{
           System.out.println("hola ");
      }
    
}
