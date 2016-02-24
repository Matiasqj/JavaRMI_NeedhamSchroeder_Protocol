/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author Matias Quinteros
 */
public interface InterfazServidor extends Remote {
    public int registrarCliente(InterfazCliente cliente, String Nombre, String password) throws RemoteException;
    public ArrayList clientes = new ArrayList();
    public void Nada()throws RemoteException;
    //mias
    public void enviarMensaje(String mensaje) throws RemoteException;
    public  ArrayList<String[]> enviarOnline() throws RemoteException;
    public String paso2(String usuario,String usuario_destino, String nonce) throws RemoteException;
    public  InterfazCliente cliente_mensaje(String usuario)throws RemoteException;
}
