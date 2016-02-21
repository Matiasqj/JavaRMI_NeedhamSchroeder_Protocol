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
 *
 * @author Matias Quinteros
 */
public class ConexionRmi {
      private static Registry registry;

    public Registry getRegistry() throws RemoteException{
        //Se inicia RMIREGISTRY para el registro de objetos
        //startRegistry(puerto de comunicación), no es necesario especificar la direccion
        startRegistry(1099);
        return registry;
    }

    public boolean detener() throws RemoteException{
        try {
            //Se saca de rmiregistry el objeto "Implementacion"
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
    private static void startRegistry(int Puerto) throws RemoteException{
        try{
            registry = LocateRegistry.getRegistry(Puerto);
            registry.list();
        }
        catch(RemoteException e){
            registry = LocateRegistry.createRegistry(Puerto);
            registry.list();
        }
    }
}
