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
 * Implementa la interfaz de cliente
 *
 * @author Matias Quinteros
 */
public class ImplementacionCliente extends UnicastRemoteObject implements InterfazCliente {

    public ImplementacionCliente() throws RemoteException {
        super();
    }

    /**
     * Realiza el paso 3 del protocolo N-H ,el usuario B, recibe el mensaje y la interfaz para responder posteriormente
     * @param mensaje
     * 
     * @param cliente_que_envio
     * @throws RemoteException
     */
    public synchronized void notificar(String mensaje, InterfazCliente cliente_que_envio) throws RemoteException {
        //me llega el mensaje de un cliente
        //reviso si el mensaje es autentico
       //carga la lista de usuarios online , se refresca para evitar errores
        VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
        DES des = new DES();
        
        try {//prueba
            //primero se desencripta el mensaje obtenido
            //llama al objeto des para desencriptar el mensaje obtenido, entrego mi pass como clave
            String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getMipass(), mensaje);
            
            //System.out.println("mensaje desencriptado" + mensaje_desencriptado);
            //como el string de texto plano tiene un formato con un delimitador, se limpia el mensaje:
            Scanner s = new Scanner(mensaje_desencriptado);//genero un scanner del msj
            s.useDelimiter("#servidor#");//quito el delimitador
            int k = 0;
            String usuario_emisor = "";
            VentanaSecundaria.getVentanaSecundaria().setCk(s.next());//setea el ck
            //System.out.println(VentanaSecundaria.getVentanaSecundaria().getCk());
            VentanaSecundaria.getVentanaSecundaria().setUsuario_emisor(cliente_que_envio);//Setea el usuario que envio(interfaz)
            usuario_emisor = s.next();//obtiene el nombre del usuario
            VentanaSecundaria.getVentanaSecundaria().setUsuario_que_envio(usuario_emisor);
            //System.out.println("" + VentanaSecundaria.getVentanaSecundaria().getUsuario_que_envio());
            //comprueba si el mensaje es de un usuario valido online
            boolean comprobar = VentanaSecundaria.getVentanaSecundaria().ComprobarUsuario(usuario_emisor);
            //System.out.println("aab" + comprobar);
            if (comprobar == true) {//actualiza los valores en el log
                //Se actualiza el log
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Un usuario se quiere comunicar");
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Recibido un mensaje : " + mensaje);
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Desencriptando mensaje...");
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Resultado: " + mensaje_desencriptado.replace("#servidor#", " , "));
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Ck: " + VentanaSecundaria.getVentanaSecundaria().getCk());
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Usuario: " + usuario_emisor + "\n");
                //como hubo exito habilita el boton para continuar con el paso
                VentanaSecundaria.getVentanaSecundaria().EnableBotonRecibidopaso4();
            }
        } catch (Exception e) {

        }

    }
    /**
     * Funcion que recibe un mensaje de un cliente que realizo el paso 4
     * @param mensaje
     * @param cliente_que_envio
     * @throws RemoteException 
     */
    public synchronized void notificar_paso4(String mensaje, InterfazCliente cliente_que_envio) throws RemoteException {
        //carga la lista de usuarios online
        VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
        DES des = new DES();
        try {//prueba
            //desencripto el mensaje recibido del paso 4 con la ck previamente obtenida
            String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getCk(), mensaje);
            if (mensaje_desencriptado != null) {//si no hubo error en la desencriptacion del msj
                /**
                 * Actualizo el log del servidor
                 */
                VentanaSecundaria.getVentanaSecundaria().setNonce_recibido(Integer.valueOf(mensaje_desencriptado));
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se recibió el mensaje de respuesta encriptado :" + mensaje);
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se desencripta el mensaje con la clave ck : " + VentanaSecundaria.getVentanaSecundaria().getCk());
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Obtenido como resultado el nonce B: " + mensaje_desencriptado);
                //como fue un exito muestra el boton 5 para continuar en el protocolo
                VentanaSecundaria.getVentanaSecundaria().EnableBotonRecibidopaso5();
            } else {//si hubo algun error notifica en el log
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("se recibió un mensaje que no se pudo desencriptar");
            }

        } catch (Exception e) {

        }
    }
    /**
     * Funcion que permite obtener la notificacion del paso 5 y comprobar el ck-1 
     * @param mensaje que se recibe
     * @param cliente_que_envio interfaz del cliente que la envio
     * @throws RemoteException 
     */
    public synchronized void notificar_paso5(String mensaje, InterfazCliente cliente_que_envio) throws RemoteException {
        //refresca la lista de usuarios online
        VentanaSecundaria.getVentanaSecundaria().CargarListaOnline(VentanaSecundaria.getVentanaSecundaria().nombre_usuario);
        DES des = new DES();
        try {//prueba
            //actualiza el log
            VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Se recibió un mensaje del paso 5");
            VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Mensaje recibido: " + mensaje);
            VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Desencriptando con ck");
            //desencripta el mensaje recibido
            String mensaje_desencriptado = des.desencriptado(VentanaSecundaria.getVentanaSecundaria().getCk(), mensaje);
            //Actualiza el log
            VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("mensaje desencriptado" + mensaje_desencriptado);
            if (mensaje_desencriptado != null) {//si el desencriptado fue correcto
                //se comprueba el mensaje y el nonce
                int mi_nonce = VentanaSecundaria.getVentanaSecundaria().mi_nonce;//obtengo mi nonce
                int nonce_recibido = Integer.valueOf(mensaje_desencriptado);//obtiene el nonce recibido
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Comprobando nonce-1 obtenido: " + nonce_recibido + " con mi nonce: " + mi_nonce);
                if (nonce_recibido == mi_nonce - 1) {//compruebo el nonce -1 si corresponde:
                    //Actualiza el Log
                    VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Nonce-1 aceptado");
                    VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Comunicación aceptada");
                    VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Enviando ACK a emisor");
                    VentanaSecundaria.getVentanaSecundaria().EnableBotonComenzar();
                    //Establece el boton para indicar que el paso 5 fue correcto
                    cliente_que_envio.Establecer_comunicacion();
                } else {//no corresponde el nonce calculado
                    VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("No se aceptó el nonce");
                }

            } else {
                VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("se recibió un mensaje que no se pudo desencriptar");
            }

        } catch (Exception e) {

        }
    }

    public void Establecer_comunicacion() throws RemoteException {
        VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Paso 5 completado y aceptado \n");
        VentanaSecundaria.getVentanaSecundaria().ActualizarLogCliente("Es posible establecer comunicación confiable\n");
        VentanaSecundaria.getVentanaSecundaria().EnableBotonComenzar();
    }

}
