/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "login", catalog = "desarrollosocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l")
    , @NamedQuery(name = "Login.findByIdUsuario", query = "SELECT l FROM Login l WHERE l.idUsuario = :idUsuario")
    , @NamedQuery(name = "Login.findByNombreUsuario", query = "SELECT l FROM Login l WHERE l.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Login.findByEmail", query = "SELECT l FROM Login l WHERE l.email = :email")
    , @NamedQuery(name = "Login.findByTelefono", query = "SELECT l FROM Login l WHERE l.telefono = :telefono")
    , @NamedQuery(name = "Login.findByUsername", query = "SELECT l FROM Login l WHERE l.username = :username")
    , @NamedQuery(name = "Login.findByPassword", query = "SELECT l FROM Login l WHERE l.password = :password")
    , @NamedQuery(name = "Login.findByTipoNloginivel", query = "SELECT l FROM Login l WHERE l.tipoNloginivel = :tipoNloginivel")
    , @NamedQuery(name = "Login.findByEstatus", query = "SELECT l FROM Login l WHERE l.estatus = :estatus")
    , @NamedQuery(name = "Login.findByRegistradoPor", query = "SELECT l FROM Login l WHERE l.registradoPor = :registradoPor")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic(optional = false)
    @Column(name = "telefono", nullable = false, length = 50)
    private String telefono;
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Basic(optional = false)
    @Column(name = "tipo_nloginivel", nullable = false, length = 50)
    private String tipoNloginivel;
    @Basic(optional = false)
    @Column(name = "estatus", nullable = false, length = 50)
    private String estatus;
    @Basic(optional = false)
    @Column(name = "registrado_por", nullable = false, length = 50)
    private String registradoPor;

    public Login() {
    }

    public Login(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Login(Integer idUsuario, String nombreUsuario, String email, String telefono, String username, String password, String tipoNloginivel, String estatus, String registradoPor) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.tipoNloginivel = tipoNloginivel;
        this.estatus = estatus;
        this.registradoPor = registradoPor;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoNloginivel() {
        return tipoNloginivel;
    }

    public void setTipoNloginivel(String tipoNloginivel) {
        this.tipoNloginivel = tipoNloginivel;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.paquete.hackathon.fedisal.entidades.Login[ idUsuario=" + idUsuario + " ]";
    }
    
}
