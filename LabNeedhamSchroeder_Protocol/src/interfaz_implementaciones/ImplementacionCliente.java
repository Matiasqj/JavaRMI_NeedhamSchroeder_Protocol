package interfaz_implementaciones;

import interfaz.InterfazCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import swing.VentanaSecundaria;
import util.DES;

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
    
    public synchronized void notificar(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException{
        System.out.println("Implementacion cliente : mensaje recibido: "+mensaje);
        VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
        //me llega el mensaje de un cliente
        //reviso si el mensaje es autentico
        DES des = new DES();
        try{
          String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getMipass(), mensaje);
        System.out.println("mensaje desencriptado"+mensaje_desencriptado);
        Scanner s = new Scanner(mensaje_desencriptado);
        s.useDelimiter("#servidor#");
        int k=0;
        String usuario_emisor="";
        VentanaSecundaria.getVentanaSecundaria().setCk(s.next());
        System.out.println(VentanaSecundaria.getVentanaSecundaria().getCk());
        VentanaSecundaria.getVentanaSecundaria().setUsuario_emisor(cliente_que_envio);
            usuario_emisor=s.next();
            VentanaSecundaria.getVentanaSecundaria().setUsuario_que_envio(usuario_emisor);
                System.out.println(""+VentanaSecundaria.getVentanaSecundaria().getUsuario_que_envio());
      
         boolean comprobar = VentanaSecundaria.getVentanaSecundaria().ComprobarUsuario(usuario_emisor);
            System.out.println("aab"+comprobar);
         if(comprobar==true){
         //Se actualiza el log
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Recibido un mensaje : "+mensaje);
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Desencriptando mensaje...");
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Resultado: "+mensaje_desencriptado.replace("#servidor#", " , "));
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Ck: "+VentanaSecundaria.getVentanaSecundaria().getCk());
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Usuario: "+usuario_emisor+"\n");
             VentanaSecundaria.getVentanaSecundaria().EnableBotonRecibidopaso4();
         }
        }
        
        catch(Exception e){
        
        }
      
        
    }
       
}
