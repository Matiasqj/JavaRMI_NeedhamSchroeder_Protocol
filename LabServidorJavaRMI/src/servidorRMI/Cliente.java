/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorRMI;

import interfaz.InterfazCliente;

/**
 *
 * @author Matias Quinteros
 */
public class Cliente implements Runnable{
    private String NombreCliente;
    private String pass;
    private int id;
    private InterfazCliente cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public InterfazCliente getCliente() {
        return cliente;
    }

    public void setCliente(InterfazCliente cliente) {
        this.cliente = cliente;
    }

    public void run() {

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
