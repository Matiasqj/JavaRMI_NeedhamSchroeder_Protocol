package clientermi;

import java.util.logging.Level;
import java.util.logging.Logger;
import swing.VentanaPrincipal;



public class retardo implements Runnable{
    private VentanaPrincipal miVista;
    
    public retardo(VentanaPrincipal vista){
        miVista = vista;
    }   
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(retardo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
