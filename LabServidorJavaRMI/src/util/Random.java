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
 */
public class Random {
    public int GenerarClaveSesion(){
    return ThreadLocalRandom.current().nextInt(1001, 2001);
    }
}
