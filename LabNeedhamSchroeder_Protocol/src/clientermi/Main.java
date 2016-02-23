/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientermi;

import swing.VentanaPrincipal;
import util.DES;

/**
 *
 * @author Matias Quinteros
 */
public class Main {
     public static void main(String args[]) {
         /*
         DES des = new DES();
         String llave ="piola";
         String texto_plano = "holamundo";
         String encriptado = des.encriptado(llave, texto_plano);
         System.out.println(""+encriptado);
         String desencriptado = des.desencriptado("piola", encriptado);
         if(desencriptado==null){
              System.out.println("error");
         
         
         }
         else{
             
             System.out.println("desencriptado:"+desencriptado);
            
         }*/

         
         new VentanaPrincipal().setVisible(true);
         
     }
}
