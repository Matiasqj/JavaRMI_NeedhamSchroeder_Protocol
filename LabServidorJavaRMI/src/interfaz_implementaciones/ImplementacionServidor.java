/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz_implementaciones;

import interfaz.InterfazCliente;
import interfaz.InterfazServidor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mysql.ConsultasSQL;
import servidorRMI.Cliente;
import swing.Server_Ventana;
import util.DES;
import util.Random;

/**
 *
 * @author Matias Quinteros Implementacion de las funciones del servidor que
 * implementa la interfaz que utiliza tanto cliente como servidor
 */
public class ImplementacionServidor extends UnicastRemoteObject implements InterfazServidor {

    public ArrayList clientes = new ArrayList();//guarda la lista de clientes que se conectan al servidor
    private Connection con;//conexion resultante de funcion en clase ConexionMySQL

    public ImplementacionServidor() throws RemoteException {

        super();
    }

    /**
     * Registra un cliente que se conecta al servidor, la interfaz de la
     * comunicacion y ademas sus datos
     *
     * @param cliente
     * @param Nombre
     * @param password
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized int registrarCliente(InterfazCliente cliente, String Nombre, String password) throws RemoteException {

        if (!(clientes.contains(cliente))) {//si la interfaz cliente no se ha conectado
            //Actualiza el log
            Server_Ventana.getServer_Ventana().Actualizar_Log_Usuario_Conexion(Nombre);
            //Realiza verificacion con la bd si los datos ingresados corresponde a uno Online
            ConsultasSQL sql = new ConsultasSQL();
            int retorno = sql.Iniciar_sesion(Nombre, password);
            if (retorno >= 0) {//si el retorno corresponde a un id de la bd

                clientes.add(cliente);//añado el cliente 
                Cliente client_nuevo = new Cliente();
                /**
                 * Setea los valores del cliente que ingreso en el ArrayLisr
                 */
                client_nuevo.setId(retorno);
                client_nuevo.setNombreCliente(Nombre);
                client_nuevo.setPass(password);
                Server_Ventana.getServer_Ventana().Online.add(client_nuevo);
            }
            return retorno;//como fue una accion valida retorno su id para verificaciones

        }
        return -1;//caso contrario retorna -1
    }

    /**
     * Se encarga de retornar la interfaz de comunicacion de un cliente de
     * acuerdo a su nombre de usuario
     *
     * @param usuario
     * @return
     * @throws RemoteException
     */
    public synchronized InterfazCliente cliente_mensaje(String usuario) throws RemoteException {
        int ubicacion = -1;
        for (int i = 0; i < Server_Ventana.getServer_Ventana().Online.size(); i++) {//Busca en ls lista Online de usuarios guardados
            if (Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente().equals(usuario)) //si el usuario ingresado correponde 
            //retorno su ubicacion para buscar la interfaz guardada
            {
                ubicacion = i;
            }
        }
        InterfazCliente nextClient;
        //si la ubicacion fue encontrada != -1
        if (ubicacion != -1) {
            nextClient = (InterfazCliente) clientes.get(ubicacion); //nextclient corresponde a la interfaz en la ubicacion buscada
        } else {
            nextClient = null;//si no se encuentra la interfaz es nula
        }
        return nextClient;

    }

    /**
     * Funcion de prueba
     *
     * @param mensaje
     * @throws RemoteException
     */
    public synchronized void enviarMensaje(String mensaje) throws RemoteException {
        //clientesNombre.addElement(Nombre);
          /* 
         ArrayList<String[]> listaonline = new ArrayList<String[]>();
         int ubicacion=-1;
         for(int i = 0; i< Server_Ventana.getServer_Ventana().Online.size();i++){
         if(Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente().equals(mensaje))
         //con esto se quien lo envio
         ubicacion = i;
         }
         InterfazCliente nextClient = (InterfazCliente)clientes.get(ubicacion);
         */
    }

    /**
     * Funcion de prueba
     *
     * @throws RemoteException
     */
    public synchronized void Nada() throws RemoteException {
        System.out.println("hola ");
    }

    /**
     * Envia una lista de los nombres de usuarios conectados con el servidor y su id
     *
     * @return Arraylist String[] con nombres de usuarios conectados y su id
     * @throws RemoteException
     */
    public synchronized ArrayList<String[]> enviarOnline() throws RemoteException {
        ArrayList<String[]> listaonline = new ArrayList<String[]>();//lista de retorno
        
        for (int i = 0; i < Server_Ventana.getServer_Ventana().Online.size(); i++) {//por cada usuario online
            String[] datos = new String[2];
            //captura los datos
            datos[0] = String.valueOf(Server_Ventana.getServer_Ventana().Online.get(i).getId());
            datos[1] = Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente();
            listaonline.add(datos);//añade el nuevo valor a la lista

        }
        return listaonline;//retorna la lista
    }
    /**
     * Se encarga de realizar el paso2 del protocolo N-S con el servidor
     * @param usuario
     * @param usuario_destino
     * @param nonce
     * @return 
     */
    public synchronized String paso2(String usuario, String usuario_destino, String nonce) throws RemoteException{
        DES des = new DES();//objeto para DES
        Random rd = new Random();//objeto para generar el ck
        int ck = rd.GenerarClaveSesion();//genero la clave de sesion
        /**
         * Actualiza valores del Log
         */
        Server_Ventana.getServer_Ventana().Actualizar_Log("Recibido mensaje de: " + usuario);
        Server_Ventana.getServer_Ventana().Actualizar_Log("Generada Ck: " + ck + " para el usuario" + usuario);
        String plano_KB = "" + ck + "#servidor#" + usuario;//crea string para enviar de texto plano
        int posicion_destino = Server_Ventana.getServer_Ventana().Busca_Usuario_posicion(usuario_destino);//ubicacion en arraylist de usuario destino
        int posicion_usuario = Server_Ventana.getServer_Ventana().Busca_Usuario_posicion(usuario);//ubicacion en arraylist de usuario 
        if (posicion_destino == -1 || posicion_usuario == -1) {// si np se ecnotro retorna nulo
            return null;
        } else {
            //primero encripta la ck con el nombre de usuario para el usuario B
            String keyB = Server_Ventana.getServer_Ventana().Online.get(posicion_destino).getPass();//busca el pass del usuario b

            String encriptado_KB = des.encriptado(keyB, plano_KB);//encripta con des el texto plano usando su calve guardad en el servidor
            /**
             * Actualiza el servidor
             */
            Server_Ventana.getServer_Ventana().Actualizar_Log("Encriptando con clave de B");
            Server_Ventana.getServer_Ventana().Actualizar_Log("Obtenido: " + encriptado_KB);
            //Ahora encripta para el Usuario A
            //se le da formato al texto plano para A
            String plano_KA = "" + nonce + "#servidor#" + usuario_destino + "#servidor#" + String.valueOf(ck) + "#servidor#" + encriptado_KB;
            String mostrarKA = plano_KA.replace("#servidor#", " , ");//se quita el delimitador para mostrar en el log
            Server_Ventana.getServer_Ventana().Actualizar_Log("Encriptando texto plano a enviar : " + mostrarKA);
            //obtiene la pass del usuario A
            String keyA = Server_Ventana.getServer_Ventana().Online.get(posicion_usuario).getPass();
            String encriptado_KA = des.encriptado(keyA, plano_KA);//encripta el texto para A
            Server_Ventana.getServer_Ventana().Actualizar_Log("Enviando al usuario :" + usuario + " el texto encriptado: " + encriptado_KA);//actualiza el log
            return encriptado_KA;
        }

    }
    
    
    
    /**
     * Cerrar sesion: elimina la interfaz guardada del nombre de usuario recibido, y lo elimina de la lista online
     * @param usuario
     * @return -1 si no fue posible cerrar sesion, caso contrario si lo fue
     * @throws RemoteException 
     */
    @Override
    public synchronized int cerrarSesion(String usuario)throws RemoteException{
        int ubicacion = -1;
        //primero se busca la posicion guardada de ese usuario en la lista online
        for (int i = 0; i < Server_Ventana.getServer_Ventana().Online.size(); i++) {//Busca en ls lista Online de usuarios guardados
            if (Server_Ventana.getServer_Ventana().Online.get(i).getNombreCliente().equals(usuario)) //si el usuario ingresado correponde 
            //retorno su ubicacion para buscar la interfaz guardada
            {
                ubicacion = i;//elimina la ubicacion
            }
        }
        if(ubicacion!=-1){//si la ubicacion fue encontrada entonces es posible cerrar sesion
            Server_Ventana.getServer_Ventana().Actualizar_Log("El usuario: " + usuario+" ha cerrado sesión");
            //se remueven de las listas guardadas, la lista con las interfaces de cliente conectadas y la lista online
            clientes.remove(ubicacion);
            Server_Ventana.getServer_Ventana().Online.remove(ubicacion);
        }

        return ubicacion;
    
    }
}
