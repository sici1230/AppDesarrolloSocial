/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.servicios;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.com.paquete.hackathon.fedisal.entidades.Login;

/**
 *
 * @author HP
 */
@Named(value = "sesion")
@RequestScoped
public class Iniciosesion implements Serializable{
    
    @Inject
    private Login login;

    public void iniciarsesion(){
        System.out.println(login.getUsername());
        System.out.println(login.getPassword());
        System.out.println("Prueba de llamado al metodo");
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
