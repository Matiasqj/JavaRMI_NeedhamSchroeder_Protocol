/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Matias Quinteros
 */
public interface InterfazServidor extends Remote {
     
    public void registrarCliente(InterfazCliente cliente, String Nombre) throws RemoteException;
    public ArrayList clientes = new ArrayList();
    public void Nada() throws RemoteException;
    //mias
    public void enviarMensaje(String mensaje) throws RemoteException;
}
