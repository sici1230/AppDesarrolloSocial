/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.NonexistentEntityException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.RollbackFailureException;
import sv.com.paquete.hackathon.fedisal.entidades.Instituciones;
import sv.com.paquete.hackathon.fedisal.entidades.Comunidades;
import sv.com.paquete.hackathon.fedisal.entidades.Proyectos;

/**
 *
 * @author HP
 */
public class ProyectosJpaController implements Serializable {

    public ProyectosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyectos proyectos) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Instituciones idInstitucion = proyectos.getIdInstitucion();
            if (idInstitucion != null) {
                idInstitucion = em.getReference(idInstitucion.getClass(), idInstitucion.getIdInstitucion());
                proyectos.setIdInstitucion(idInstitucion);
            }
            Comunidades idComunidad = proyectos.getIdComunidad();
            if (idComunidad != null) {
                idComunidad = em.getReference(idComunidad.getClass(), idComunidad.getIdComunidad());
                proyectos.setIdComunidad(idComunidad);
            }
            em.persist(proyectos);
            if (idInstitucion != null) {
                idInstitucion.getProyectosCollection().add(proyectos);
                idInstitucion = em.merge(idInstitucion);
            }
            if (idComunidad != null) {
                idComunidad.getProyectosCollection().add(proyectos);
                idComunidad = em.merge(idComunidad);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyectos proyectos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyectos persistentProyectos = em.find(Proyectos.class, proyectos.getIdProyecto());
            Instituciones idInstitucionOld = persistentProyectos.getIdInstitucion();
            Instituciones idInstitucionNew = proyectos.getIdInstitucion();
            Comunidades idComunidadOld = persistentProyectos.getIdComunidad();
            Comunidades idComunidadNew = proyectos.getIdComunidad();
            if (idInstitucionNew != null) {
                idInstitucionNew = em.getReference(idInstitucionNew.getClass(), idInstitucionNew.getIdInstitucion());
                proyectos.setIdInstitucion(idInstitucionNew);
            }
            if (idComunidadNew != null) {
                idComunidadNew = em.getReference(idComunidadNew.getClass(), idComunidadNew.getIdComunidad());
                proyectos.setIdComunidad(idComunidadNew);
            }
            proyectos = em.merge(proyectos);
            if (idInstitucionOld != null && !idInstitucionOld.equals(idInstitucionNew)) {
                idInstitucionOld.getProyectosCollection().remove(proyectos);
                idInstitucionOld = em.merge(idInstitucionOld);
            }
            if (idInstitucionNew != null && !idInstitucionNew.equals(idInstitucionOld)) {
                idInstitucionNew.getProyectosCollection().add(proyectos);
                idInstitucionNew = em.merge(idInstitucionNew);
            }
            if (idComunidadOld != null && !idComunidadOld.equals(idComunidadNew)) {
                idComunidadOld.getProyectosCollection().remove(proyectos);
                idComunidadOld = em.merge(idComunidadOld);
            }
            if (idComunidadNew != null && !idComunidadNew.equals(idComunidadOld)) {
                idComunidadNew.getProyectosCollection().add(proyectos);
                idComunidadNew = em.merge(idComunidadNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyectos.getIdProyecto();
                if (findProyectos(id) == null) {
                    throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proyectos proyectos;
            try {
                proyectos = em.getReference(Proyectos.class, id);
                proyectos.getIdProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.", enfe);
            }
            Instituciones idInstitucion = proyectos.getIdInstitucion();
            if (idInstitucion != null) {
                idInstitucion.getProyectosCollection().remove(proyectos);
                idInstitucion = em.merge(idInstitucion);
            }
            Comunidades idComunidad = proyectos.getIdComunidad();
            if (idComunidad != null) {
                idComunidad.getProyectosCollection().remove(proyectos);
                idComunidad = em.merge(idComunidad);
            }
            em.remove(proyectos);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyectos> findProyectosEntities() {
        return findProyectosEntities(true, -1, -1);
    }

    public List<Proyectos> findProyectosEntities(int maxResults, int firstResult) {
        return findProyectosEntities(false, maxResults, firstResult);
    }

    private List<Proyectos> findProyectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyectos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proyectos findProyectos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyectos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyectos> rt = cq.from(Proyectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
