/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Matias Quinteros
 */
public interface InterfazCliente extends Remote{
    public void notificar(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException;
    public void notificar_paso4(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException;   
    public void notificar_paso5(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException; 
    public void Establecer_comunicacion() throws RemoteException;
}
