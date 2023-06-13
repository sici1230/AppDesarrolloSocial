/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "proyectos", catalog = "desarrollosocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p")
    , @NamedQuery(name = "Proyectos.findByIdProyecto", query = "SELECT p FROM Proyectos p WHERE p.idProyecto = :idProyecto")
    , @NamedQuery(name = "Proyectos.findByNombreProyecto", query = "SELECT p FROM Proyectos p WHERE p.nombreProyecto = :nombreProyecto")
    , @NamedQuery(name = "Proyectos.findByFechaPropuesta", query = "SELECT p FROM Proyectos p WHERE p.fechaPropuesta = :fechaPropuesta")
    , @NamedQuery(name = "Proyectos.findByFechaEjecutado", query = "SELECT p FROM Proyectos p WHERE p.fechaEjecutado = :fechaEjecutado")})
public class Proyectos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProyecto", nullable = false)
    private Integer idProyecto;
    @Basic(optional = false)
    @Column(name = "nombreProyecto", nullable = false, length = 100)
    private String nombreProyecto;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion", nullable = false, length = 65535)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fechaPropuesta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPropuesta;
    @Basic(optional = false)
    @Column(name = "fechaEjecutado", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecutado;
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion", nullable = false)
    @ManyToOne(optional = false)
    private Instituciones idInstitucion;
    @JoinColumn(name = "idComunidad", referencedColumnName = "idComunidad", nullable = false)
    @ManyToOne(optional = false)
    private Comunidades idComunidad;

    public Proyectos() {
    }

    public Proyectos(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyectos(Integer idProyecto, String nombreProyecto, String descripcion, Date fechaPropuesta, Date fechaEjecutado) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.descripcion = descripcion;
        this.fechaPropuesta = fechaPropuesta;
        this.fechaEjecutado = fechaEjecutado;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPropuesta() {
        return fechaPropuesta;
    }

    public void setFechaPropuesta(Date fechaPropuesta) {
        this.fechaPropuesta = fechaPropuesta;
    }

    public Date getFechaEjecutado() {
        return fechaEjecutado;
    }

    public void setFechaEjecutado(Date fechaEjecutado) {
        this.fechaEjecutado = fechaEjecutado;
    }

    public Instituciones getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Instituciones idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Comunidades getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Comunidades idComunidad) {
        this.idComunidad = idComunidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyectos)) {
            return false;
        }
        Proyectos other = (Proyectos) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.paquete.hackathon.fedisal.entidades.Proyectos[ idProyecto=" + idProyecto + " ]";
    }
    
}
