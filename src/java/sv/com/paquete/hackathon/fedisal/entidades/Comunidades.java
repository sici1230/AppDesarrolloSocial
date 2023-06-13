/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "comunidades", catalog = "desarrollosocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunidades.findAll", query = "SELECT c FROM Comunidades c")
    , @NamedQuery(name = "Comunidades.findByIdComunidad", query = "SELECT c FROM Comunidades c WHERE c.idComunidad = :idComunidad")
    , @NamedQuery(name = "Comunidades.findByNombreComunidad", query = "SELECT c FROM Comunidades c WHERE c.nombreComunidad = :nombreComunidad")
    , @NamedQuery(name = "Comunidades.findByMunicipio", query = "SELECT c FROM Comunidades c WHERE c.municipio = :municipio")})
public class Comunidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComunidad", nullable = false)
    private Integer idComunidad;
    @Basic(optional = false)
    @Column(name = "nombreComunidad", nullable = false, length = 100)
    private String nombreComunidad;
    @Basic(optional = false)
    @Column(name = "municipio", nullable = false, length = 50)
    private String municipio;
    @Basic(optional = false)
    @Lob
    @Column(name = "ubicacion", nullable = false)
    private Object ubicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idComunidad")
    private Collection<Proyectos> proyectosCollection;

    public Comunidades() {
    }

    public Comunidades(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public Comunidades(Integer idComunidad, String nombreComunidad, String municipio, Object ubicacion) {
        this.idComunidad = idComunidad;
        this.nombreComunidad = nombreComunidad;
        this.municipio = municipio;
        this.ubicacion = ubicacion;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Object getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Object ubicacion) {
        this.ubicacion = ubicacion;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComunidad != null ? idComunidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunidades)) {
            return false;
        }
        Comunidades other = (Comunidades) object;
        if ((this.idComunidad == null && other.idComunidad != null) || (this.idComunidad != null && !this.idComunidad.equals(other.idComunidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.paquete.hackathon.fedisal.entidades.Comunidades[ idComunidad=" + idComunidad + " ]";
    }
    
}
