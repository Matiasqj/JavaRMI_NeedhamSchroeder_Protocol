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
 * Cliente: guarda los datos de los usuarios que se conectan al servidor
 */
public class Cliente{
    private String NombreCliente;//guarda el nombre de usuario
    private String pass;//guarda el pass
    private int id;//guarda el id
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


}
