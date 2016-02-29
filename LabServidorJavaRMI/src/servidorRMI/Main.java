package servidorRMI;

import swing.Server_Ventana;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matias Quinteros
 */
public class Main {
     public static void main(String[] args) {
         //inicia la ventana principal del servidor
         Server_Ventana ventana;
         ventana= new Server_Ventana();
         ventana.setVisible(true);
     }
}
