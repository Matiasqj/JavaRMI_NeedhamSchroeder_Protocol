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
             VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Un usuario se quiere comunicar");
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
    
    
     public synchronized void notificar_paso4(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException{
         VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
         DES des = new DES();
         try{
         String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getCk(), mensaje);
          if(mensaje_desencriptado!=null){
              VentanaSecundaria.getVentanaSecundaria().setNonce_recibido(Integer.valueOf(mensaje_desencriptado));
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se recibió el mensaje de respuesta encriptado :"+mensaje);
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se desencripta el mensaje con la clave ck : "+ VentanaSecundaria.getVentanaSecundaria().getCk());
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Obtenido como resultado el nonce B: "+mensaje_desencriptado);
            VentanaSecundaria.getVentanaSecundaria().EnableBotonRecibidopaso5();
          }
          else{
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("se recibió un mensaje que no se pudo desencriptar");
          }
              
         
         
         }
         catch(Exception e){
         
         }
     }
     
     
      public synchronized void notificar_paso5(String mensaje,InterfazCliente cliente_que_envio) throws RemoteException{
         
         VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
         DES des = new DES();
         try{
         VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se recibió un mensaje del paso 5");
         VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Mensaje recibido: "+mensaje);
         VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Desencriptando con ck");
         
         String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getCk(), mensaje);
         VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("mensaje desencriptado"+mensaje_desencriptado);
         if(mensaje_desencriptado!=null){
              //se comprueba el mensaje
           int mi_nonce = VentanaSecundaria.getVentanaSecundaria().mi_nonce;
           int nonce_recibido = Integer.valueOf(mensaje_desencriptado);
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Comprobando nonce-1 obtenido: "+nonce_recibido +" con mi nonce: "+mi_nonce);
           if(nonce_recibido==mi_nonce-1){
              VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Nonce-1 aceptado");
              VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Comunicación aceptada");
              VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Enviando ACK a emisor");
              VentanaSecundaria.getVentanaSecundaria().EnableBotonComenzar();
               cliente_que_envio.Establecer_comunicacion();
           }
           else{
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("No se aceptó el nonce");
           }    
          
           
          }
          else{
           VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("se recibió un mensaje que no se pudo desencriptar");
          }
              
         
         
         }
         catch(Exception e){
        
         }
     }
      
      public void Establecer_comunicacion() throws RemoteException{
        VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Paso 5 completado y aceptado \n");
        VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Es posible establecer comunicación confiable\n");
        VentanaSecundaria.getVentanaSecundaria().EnableBotonComenzar();
      }
     
     
     
    
}
