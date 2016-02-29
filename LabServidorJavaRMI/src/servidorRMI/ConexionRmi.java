/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorRMI;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConexionRmi: Se encarga de iniciar el servidor y establecer los registros que se utilizarán para establecer
 * el puerto de comunicación
 * @author Matias Quinteros
 */
public class ConexionRmi {
      private static Registry registry;
      /**
       * Retorna el registro que sera utilizado como direccion del puerto de comunicación
       * @return registro
       * @throws RemoteException 
       */
    public Registry getRegistry() throws RemoteException{
        //Se inicia RMIREGISTRY para el registro de objetos
        //llama a la funcion startRegistry(puerto de comunicación)
        startRegistry(1099);
        return registry;
    }
    /**
     * detener: quita el registro de la direccion del servidor
     * @return boolean : true: si no hay error y logra detener el servidor
     *                   false: si existe algun error
     * @throws RemoteException 
     */
    public boolean detener() throws RemoteException{
        try {
            //Se saca de rmiregistry el objeto "Lab"
            //Ya no esta disponible para ser consumido
            registry.unbind("Lab");
        } catch (NotBoundException ex) {
            Logger.getLogger(ConexionRmi.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (AccessException ex) {
            Logger.getLogger(ConexionRmi.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    /**
     * Crea un registro de acuerdo al puerto de entrada o bien toma un registro ya existemte
     * @param Puerto
     * @throws RemoteException 
     */
    private static void startRegistry(int Puerto) throws RemoteException{
        try{//si el registro fue creado
            registry = LocateRegistry.getRegistry(Puerto);
            registry.list();
        }
        catch(RemoteException e){//si no, crea un nuevo registro
            registry = LocateRegistry.createRegistry(Puerto);
            registry.list();
        }
    }
}
