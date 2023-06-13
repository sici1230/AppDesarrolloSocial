/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.servicios;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sv.com.paquete.hackathon.fedisal.entidades.Login;

/**
 *
 * @author HP
 */
@Named
@RequestScoped
public class Iniciosesion implements Serializable{
    
    private Login sesion;

    public void iniciarsesion(){
        System.out.println("Prueba de llamado al metodo");
    }
    
    public Login getSesion() {
        return sesion;
    }

    public void setSesion(Login sesion) {
        this.sesion = sesion;
    }
    
    
}
