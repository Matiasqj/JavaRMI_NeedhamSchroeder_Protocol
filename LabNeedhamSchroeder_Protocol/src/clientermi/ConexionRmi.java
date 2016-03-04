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
import swing.VentanaSecundaria;

/**
 * ConexionRmi: Se encarga de iniciar el servidor y establecer los registros que se utilizarán para establecer
 * el puerto de comunicación
 * @author Matias Quinteros
 */
public class ConexionRmi {
    private static Registry registry;    
    private static InterfazServidor servidor;
    private static InterfazCliente cliente;
    

       /**
       * Retorna un boolean si logra conectarse con el registro del servidor
       * @return registro
       * @throws RemoteException 
       */
    public boolean iniciarRegistry() throws RemoteException{
        try{
            
            
            //pide los permisos necesarios
            java.security.AllPermission a = new java.security.AllPermission();
            System.setProperty("java.security.policy", "rmi.policy");
            //llama a la funcion startRegistry(puerto de comunicación), la ip no es necesaria
            startRegistry("127.0.1.1",1099);
            //Vamos al Registry y miramos el Objeto "Lab" para poder usarlo.
            servidor = (InterfazServidor)registry.lookup("Lab");//Actualiza la interfaz del servidor
            //retorna verdadero si encontro el registro
            return true;
        }
        catch(RemoteException | NotBoundException e){//cualquier error retorna false
            return false;
        }
    }
    

    /**
     * Obtiene el registro creado del servidor
     * @param host
     * @param Puerto
     * @throws RemoteException 
     */
    private static void startRegistry(String host, int Puerto) throws RemoteException{
        try{
            //obtiene el registro del servidor de acuerdo a la ip , y el valor del puerto por defecto 1099
            registry = LocateRegistry.getRegistry(host, Puerto);
            registry.list();
        }
        catch(RemoteException e){
        }
    }
    
    /**
     * Retorna interfaz del servidor
     * @return 
     */
    public InterfazServidor getServidor(){
        
        return servidor;
    
    }
    /**
     * Retorna  la interfaz del cliente
     * @return 
     */
    public InterfazCliente getCliente(){
    
        return cliente;
        
    }
    

    /**
     * RegistrarCliente: llama a la funcion de la implementacion del servidor para que este registre al usuario en el servidor
     * @param Nombre
     * @param password
     * @return
     * @throws RemoteException 
     */
    public int registrarCliente(String Nombre, String password) throws RemoteException{
        //crea la interfaz de cliente para el usuario
        cliente = new ImplementacionCliente();       
        //manda la interfaz de cliente, el nombre y el password
        int valor = servidor.registrarCliente(cliente, Nombre,password);
        //retorna un valor != de -1 si tuvo exito
        return valor;
    }
    /*
        Funcion  de prueb
    */
    public  void enviarmensajeusuario() throws RemoteException{
        servidor.enviarMensaje("prueba");
    }
    /**
     * LLama al servidor para que retorne la lista de usuarios online
     * @return lista con los usuarios online
     * @throws RemoteException 
     */
    public ArrayList<String[]> getOnlineUsers() throws RemoteException{
        return servidor.enviarOnline();
    } 
    /**
     * Le pide a la interfaz del servidor que ejecute el paso 2 del protocolo N-H
     * 
     * @param usuario
     * @param usario_destino
     * @param nonce
     * @return String encriptado que se recibe desde el servidor
     * @throws RemoteException 
     */
    public String getPaso2Results(String usuario,String usario_destino,String nonce) throws RemoteException{
        return servidor.paso2(usuario,usario_destino,nonce);
    }
    /**
     * Le envia a otro cliente un mensaje con un string y su interfaz para el pso 3
     * @param cliente_a_enviar
     * @param mensaje
     * @throws RemoteException 
     */
    public void Notificar(InterfazCliente cliente_a_enviar,String mensaje ) throws RemoteException{
        cliente_a_enviar.notificar(mensaje,cliente);//llama a implementacion cliente para que ejecute la funcion notificar
    }
    /**
     * Responde a otro cliente para que ejecute el paso 4
     * @param cliente_a_enviar
     * @param mensaje
     * @throws RemoteException 
     */
    public void ResponderPaso4(InterfazCliente cliente_a_enviar,String mensaje) throws RemoteException{
        cliente_a_enviar.notificar_paso4(mensaje, cliente_a_enviar);//llama a implementacion cliente para que ejecute la funcion paso 4
    }
    /**
     * Responde a otro cliente para el paso 5
     * @param cliente_a_enviar
     * @param mensaje
     * @throws RemoteException 
     */
    public void ResponderPaso5(InterfazCliente cliente_a_enviar,String mensaje) throws RemoteException{
        VentanaSecundaria.getVentanaSecundaria().cliente_a_enviar.notificar_paso5(mensaje, cliente_a_enviar);//llama a implementacion cliente para que ejecute la funcion paso 5
    }
    
    public int CerrarSesion(String usuario) throws RemoteException{
        int valor= servidor.cerrarSesion(usuario);
        return valor;
    } 
    
    
}
