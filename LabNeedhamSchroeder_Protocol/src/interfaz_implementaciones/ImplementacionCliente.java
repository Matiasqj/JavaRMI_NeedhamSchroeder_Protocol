package interfaz_implementaciones;

import interfaz.InterfazCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matias Quinteros
 */
public class ImplementacionCliente extends UnicastRemoteObject implements InterfazCliente{
    
          public ImplementacionCliente() throws RemoteException{
        super();
    }
       /************************************************************
 *Nombre de la funcion: notificar                           *
 *Objetivo: llamar a la funcion aviso, la cual actualiza    *
 *y notifica los nuevos mensajes                            *
 *Parámetros: el mensaje                                    *
 ************************************************************/
    
    public void notificar(String mensaje) throws RemoteException{
        System.out.println("mensaje recibido: "+mensaje);
    }
       
}
