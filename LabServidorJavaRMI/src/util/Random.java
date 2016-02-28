/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Matias Quinteros
 * Clase random retorna un valor aleatorio entre 1001 y 2001 para clave de sesion del servidor
 */
public class Random {
    /**
     * GenerarClaveSesion: genera un entero aleatorio entre 1001 y 2001
     * @return entero aleatorio
     */
    public int GenerarClaveSesion(){
    return ThreadLocalRandom.current().nextInt(1001, 2001);
    }
}
