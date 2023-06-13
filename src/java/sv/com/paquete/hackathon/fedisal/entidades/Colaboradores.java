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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "colaboradores", catalog = "desarrollosocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colaboradores.findAll", query = "SELECT c FROM Colaboradores c")
    , @NamedQuery(name = "Colaboradores.findByDui", query = "SELECT c FROM Colaboradores c WHERE c.dui = :dui")
    , @NamedQuery(name = "Colaboradores.findByNombresColaborador", query = "SELECT c FROM Colaboradores c WHERE c.nombresColaborador = :nombresColaborador")
    , @NamedQuery(name = "Colaboradores.findByApellidosColaborador", query = "SELECT c FROM Colaboradores c WHERE c.apellidosColaborador = :apellidosColaborador")
    , @NamedQuery(name = "Colaboradores.findByTelefono", query = "SELECT c FROM Colaboradores c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Colaboradores.findByDireccion", query = "SELECT c FROM Colaboradores c WHERE c.direccion = :direccion")})
public class Colaboradores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dui", nullable = false)
    private Integer dui;
    @Basic(optional = false)
    @Column(name = "nombresColaborador", nullable = false, length = 100)
    private String nombresColaborador;
    @Basic(optional = false)
    @Column(name = "apellidosColaborador", nullable = false, length = 100)
    private String apellidosColaborador;
    @Basic(optional = false)
    @Column(name = "telefono", nullable = false, length = 8)
    private String telefono;
    @Basic(optional = false)
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion", nullable = false)
    @ManyToOne(optional = false)
    private Instituciones idInstitucion;

    public Colaboradores() {
    }

    public Colaboradores(Integer dui) {
        this.dui = dui;
    }

    public Colaboradores(Integer dui, String nombresColaborador, String apellidosColaborador, String telefono, String direccion) {
        this.dui = dui;
        this.nombresColaborador = nombresColaborador;
        this.apellidosColaborador = apellidosColaborador;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Integer getDui() {
        return dui;
    }

    public void setDui(Integer dui) {
        this.dui = dui;
    }

    public String getNombresColaborador() {
        return nombresColaborador;
    }

    public void setNombresColaborador(String nombresColaborador) {
        this.nombresColaborador = nombresColaborador;
    }

    public String getApellidosColaborador() {
        return apellidosColaborador;
    }

    public void setApellidosColaborador(String apellidosColaborador) {
        this.apellidosColaborador = apellidosColaborador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instituciones getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Instituciones idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dui != null ? dui.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaboradores)) {
            return false;
        }
        Colaboradores other = (Colaboradores) object;
        if ((this.dui == null && other.dui != null) || (this.dui != null && !this.dui.equals(other.dui))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.paquete.hackathon.fedisal.entidades.Colaboradores[ dui=" + dui + " ]";
    }
    
}
