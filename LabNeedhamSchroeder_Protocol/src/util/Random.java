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
 * Clase random retorna un valor aleatorio entre 0 y 1000 para el nonce del usuario
 */
public class Random {
     /**
     * Generar_nonce: genera un entero aleatorio entre 0 y 1000
     * @return entero aleatorio
     */
    public int Generar_nonce(){
    return ThreadLocalRandom.current().nextInt(0, 1000);
    }
}
