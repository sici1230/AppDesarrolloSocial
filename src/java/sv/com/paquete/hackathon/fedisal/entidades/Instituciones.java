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
@Table(name = "instituciones", catalog = "desarrollosocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instituciones.findAll", query = "SELECT i FROM Instituciones i")
    , @NamedQuery(name = "Instituciones.findByIdInstitucion", query = "SELECT i FROM Instituciones i WHERE i.idInstitucion = :idInstitucion")
    , @NamedQuery(name = "Instituciones.findByNombreInstitucion", query = "SELECT i FROM Instituciones i WHERE i.nombreInstitucion = :nombreInstitucion")
    , @NamedQuery(name = "Instituciones.findByDireccion", query = "SELECT i FROM Instituciones i WHERE i.direccion = :direccion")
    , @NamedQuery(name = "Instituciones.findByTelefono", query = "SELECT i FROM Instituciones i WHERE i.telefono = :telefono")
    , @NamedQuery(name = "Instituciones.findByNombreRepresentante", query = "SELECT i FROM Instituciones i WHERE i.nombreRepresentante = :nombreRepresentante")})
public class Instituciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInstitucion", nullable = false)
    private Integer idInstitucion;
    @Basic(optional = false)
    @Column(name = "nombreInstitucion", nullable = false, length = 200)
    private String nombreInstitucion;
    @Basic(optional = false)
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono", nullable = false, length = 8)
    private String telefono;
    @Lob
    @Column(name = "acercaDe", length = 65535)
    private String acercaDe;
    @Basic(optional = false)
    @Column(name = "nombreRepresentante", nullable = false, length = 100)
    private String nombreRepresentante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInstitucion")
    private Collection<Colaboradores> colaboradoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInstitucion")
    private Collection<Proyectos> proyectosCollection;

    public Instituciones() {
    }

    public Instituciones(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Instituciones(Integer idInstitucion, String nombreInstitucion, String direccion, String telefono, String nombreRepresentante) {
        this.idInstitucion = idInstitucion;
        this.nombreInstitucion = nombreInstitucion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombreRepresentante = nombreRepresentante;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAcercaDe() {
        return acercaDe;
    }

    public void setAcercaDe(String acercaDe) {
        this.acercaDe = acercaDe;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    @XmlTransient
    public Collection<Colaboradores> getColaboradoresCollection() {
        return colaboradoresCollection;
    }

    public void setColaboradoresCollection(Collection<Colaboradores> colaboradoresCollection) {
        this.colaboradoresCollection = colaboradoresCollection;
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
        hash += (idInstitucion != null ? idInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instituciones)) {
            return false;
        }
        Instituciones other = (Instituciones) object;
        if ((this.idInstitucion == null && other.idInstitucion != null) || (this.idInstitucion != null && !this.idInstitucion.equals(other.idInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.paquete.hackathon.fedisal.entidades.Instituciones[ idInstitucion=" + idInstitucion + " ]";
    }
    
}
