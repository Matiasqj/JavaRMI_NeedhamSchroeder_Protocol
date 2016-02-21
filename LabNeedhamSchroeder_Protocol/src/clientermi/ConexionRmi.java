/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientermi;

import interfaz.InterfazCliente;
import interfaz.InterfazServidor;
import interfaz_implementaciones.ImplementacionCliente;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Matias Quinteros
 */
public class ConexionRmi {
    private static Registry registry;    
    private static InterfazServidor servidor;
    private static InterfazCliente cliente;
    
/************************************************************
 *Nombre de la funcion: iniciarRegistry                     *
 *Objetivo: inicia el registro para la conexion RMI         *
 *Parámetros: no tiene                                      *
 ************************************************************/

    public boolean iniciarRegistry() throws RemoteException{
        try{
            System.out.println("llegue aca en iniciar");
            //Se inicia RMIREGISTRY para el registro de objetos
            java.security.AllPermission a = new java.security.AllPermission();
            System.setProperty("java.security.policy", "rmi.policy");
            //startRegistry(direccion del registry,puerto de comunicación);
            startRegistry("127.0.1.1",1099);
            //Vamos al Registry y miramos el Objeto "Implementacion" para poder usarlo.
            servidor = (InterfazServidor)registry.lookup("Lab");
            System.out.println("paso");
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
 /***********************************************************
 *Nombre de la funcion: startRegistry                       *
 *Objetivo: Extencion de la anterior                        *
 *Parámetros: el host de la conexion y el puerto ocupado    *
 ************************************************************/
    
    private static void startRegistry(String host, int Puerto) throws RemoteException{
        try{
            registry = LocateRegistry.getRegistry(host, Puerto);
            registry.list();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
    //Con esto no es necesario hacer un lookup al objeto remoto cada vez que deseemos usarlo
    //basta con llamar a la instancia de la interfaz que fue llamada la primera vez.
    
/************************************************************
 *Nombre de la funcion: getServidor                         *
 *Objetivo: Objener el servidor                             *
 *Parámetros: no tiene                                      *
 ************************************************************/
    
    public InterfazServidor getServidor(){
        
        return servidor;
    
    }
    
    public InterfazCliente getCliente(){
    
        return cliente;
        
    }
    
/************************************************************
 *Nombre de la funcion: registrarCliente                    *
 *Objetivo: Ingresar a los clientes conectados al servidor  *
 *y asi mantenerlos notificados de los mensajes             *
 *Parámetros: no tiene                                      *
 ************************************************************/
    
    public void registrarCliente(String Nombre) throws RemoteException{
        
        cliente = new ImplementacionCliente();
        System.out.println("asasas");
        
        servidor.registrarCliente(cliente, "soyuno");
        
        System.out.println("nooo");
    }
}
