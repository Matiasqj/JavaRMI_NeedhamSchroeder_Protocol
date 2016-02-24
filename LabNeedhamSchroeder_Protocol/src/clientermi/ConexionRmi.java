/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientermi;

import interfaz.InterfazCliente;
import interfaz.InterfazServidor;
import interfaz_implementaciones.ImplementacionCliente;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

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
     * @return 
     * @throws java.rmi.RemoteException
 ************************************************************/

    public boolean iniciarRegistry() throws RemoteException{
        try{
            
            //Se inicia RMIREGISTRY para el registro de objetos
            java.security.AllPermission a = new java.security.AllPermission();
            System.setProperty("java.security.policy", "rmi.policy");
            //startRegistry(direccion del registry,puerto de comunicación);
            startRegistry("127.0.1.1",1099);
            //Vamos al Registry y miramos el Objeto "Implementacion" para poder usarlo.
            servidor = (InterfazServidor)registry.lookup("Lab");
            
            return true;
        }
        catch(RemoteException | NotBoundException e){
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
        }
    }
    //Con esto no es necesario hacer un lookup al objeto remoto cada vez que deseemos usarlo
    //basta con llamar a la instancia de la interfaz que fue llamada la primera vez.
    
/************************************************************
 *Nombre de la funcion: getServidor                         *
 *Objetivo: Objener el servidor                             *
 *Parámetros: no tiene                                      *
     * @return 
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
     * @param Nombre
 ************************************************************/
    
    public int registrarCliente(String Nombre, String password) throws RemoteException{
        
        cliente = new ImplementacionCliente();        
        int valor = servidor.registrarCliente(cliente, Nombre,password);
        return valor;
    }
    
    public  void enviarmensajeusuario() throws RemoteException{
        servidor.enviarMensaje("kkk");
    }
    public ArrayList<String[]> getOnlineUsers() throws RemoteException{
        return servidor.enviarOnline();
    } 
    
    public String getPaso2Results(String usuario,String usario_destino,String nonce) throws RemoteException{
        return servidor.paso2(usuario,usario_destino,nonce);
    }
    
    public void Notificar(InterfazCliente cliente_a_enviar,String mensaje ) throws RemoteException{
        cliente_a_enviar.notificar(mensaje,cliente);
    }
    
}
