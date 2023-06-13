/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.paquete.hackathon.fedisal.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.com.paquete.hackathon.fedisal.entidades.Colaboradores;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.IllegalOrphanException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.NonexistentEntityException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.RollbackFailureException;
import sv.com.paquete.hackathon.fedisal.entidades.Instituciones;
import sv.com.paquete.hackathon.fedisal.entidades.Proyectos;

/**
 *
 * @author HP
 */
public class InstitucionesJpaController implements Serializable {

    public InstitucionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Instituciones instituciones) throws RollbackFailureException, Exception {
        if (instituciones.getColaboradoresCollection() == null) {
            instituciones.setColaboradoresCollection(new ArrayList<Colaboradores>());
        }
        if (instituciones.getProyectosCollection() == null) {
            instituciones.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Colaboradores> attachedColaboradoresCollection = new ArrayList<Colaboradores>();
            for (Colaboradores colaboradoresCollectionColaboradoresToAttach : instituciones.getColaboradoresCollection()) {
                colaboradoresCollectionColaboradoresToAttach = em.getReference(colaboradoresCollectionColaboradoresToAttach.getClass(), colaboradoresCollectionColaboradoresToAttach.getDui());
                attachedColaboradoresCollection.add(colaboradoresCollectionColaboradoresToAttach);
            }
            instituciones.setColaboradoresCollection(attachedColaboradoresCollection);
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : instituciones.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getIdProyecto());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            instituciones.setProyectosCollection(attachedProyectosCollection);
            em.persist(instituciones);
            for (Colaboradores colaboradoresCollectionColaboradores : instituciones.getColaboradoresCollection()) {
                Instituciones oldIdInstitucionOfColaboradoresCollectionColaboradores = colaboradoresCollectionColaboradores.getIdInstitucion();
                colaboradoresCollectionColaboradores.setIdInstitucion(instituciones);
                colaboradoresCollectionColaboradores = em.merge(colaboradoresCollectionColaboradores);
                if (oldIdInstitucionOfColaboradoresCollectionColaboradores != null) {
                    oldIdInstitucionOfColaboradoresCollectionColaboradores.getColaboradoresCollection().remove(colaboradoresCollectionColaboradores);
                    oldIdInstitucionOfColaboradoresCollectionColaboradores = em.merge(oldIdInstitucionOfColaboradoresCollectionColaboradores);
                }
            }
            for (Proyectos proyectosCollectionProyectos : instituciones.getProyectosCollection()) {
                Instituciones oldIdInstitucionOfProyectosCollectionProyectos = proyectosCollectionProyectos.getIdInstitucion();
                proyectosCollectionProyectos.setIdInstitucion(instituciones);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldIdInstitucionOfProyectosCollectionProyectos != null) {
                    oldIdInstitucionOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldIdInstitucionOfProyectosCollectionProyectos = em.merge(oldIdInstitucionOfProyectosCollectionProyectos);
                }
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

    public void edit(Instituciones instituciones) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Instituciones persistentInstituciones = em.find(Instituciones.class, instituciones.getIdInstitucion());
            Collection<Colaboradores> colaboradoresCollectionOld = persistentInstituciones.getColaboradoresCollection();
            Collection<Colaboradores> colaboradoresCollectionNew = instituciones.getColaboradoresCollection();
            Collection<Proyectos> proyectosCollectionOld = persistentInstituciones.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = instituciones.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Colaboradores colaboradoresCollectionOldColaboradores : colaboradoresCollectionOld) {
                if (!colaboradoresCollectionNew.contains(colaboradoresCollectionOldColaboradores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Colaboradores " + colaboradoresCollectionOldColaboradores + " since its idInstitucion field is not nullable.");
                }
            }
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyectos " + proyectosCollectionOldProyectos + " since its idInstitucion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Colaboradores> attachedColaboradoresCollectionNew = new ArrayList<Colaboradores>();
            for (Colaboradores colaboradoresCollectionNewColaboradoresToAttach : colaboradoresCollectionNew) {
                colaboradoresCollectionNewColaboradoresToAttach = em.getReference(colaboradoresCollectionNewColaboradoresToAttach.getClass(), colaboradoresCollectionNewColaboradoresToAttach.getDui());
                attachedColaboradoresCollectionNew.add(colaboradoresCollectionNewColaboradoresToAttach);
            }
            colaboradoresCollectionNew = attachedColaboradoresCollectionNew;
            instituciones.setColaboradoresCollection(colaboradoresCollectionNew);
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getIdProyecto());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            instituciones.setProyectosCollection(proyectosCollectionNew);
            instituciones = em.merge(instituciones);
            for (Colaboradores colaboradoresCollectionNewColaboradores : colaboradoresCollectionNew) {
                if (!colaboradoresCollectionOld.contains(colaboradoresCollectionNewColaboradores)) {
                    Instituciones oldIdInstitucionOfColaboradoresCollectionNewColaboradores = colaboradoresCollectionNewColaboradores.getIdInstitucion();
                    colaboradoresCollectionNewColaboradores.setIdInstitucion(instituciones);
                    colaboradoresCollectionNewColaboradores = em.merge(colaboradoresCollectionNewColaboradores);
                    if (oldIdInstitucionOfColaboradoresCollectionNewColaboradores != null && !oldIdInstitucionOfColaboradoresCollectionNewColaboradores.equals(instituciones)) {
                        oldIdInstitucionOfColaboradoresCollectionNewColaboradores.getColaboradoresCollection().remove(colaboradoresCollectionNewColaboradores);
                        oldIdInstitucionOfColaboradoresCollectionNewColaboradores = em.merge(oldIdInstitucionOfColaboradoresCollectionNewColaboradores);
                    }
                }
            }
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Instituciones oldIdInstitucionOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getIdInstitucion();
                    proyectosCollectionNewProyectos.setIdInstitucion(instituciones);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldIdInstitucionOfProyectosCollectionNewProyectos != null && !oldIdInstitucionOfProyectosCollectionNewProyectos.equals(instituciones)) {
                        oldIdInstitucionOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldIdInstitucionOfProyectosCollectionNewProyectos = em.merge(oldIdInstitucionOfProyectosCollectionNewProyectos);
                    }
                }
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
                Integer id = instituciones.getIdInstitucion();
                if (findInstituciones(id) == null) {
                    throw new NonexistentEntityException("The instituciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Instituciones instituciones;
            try {
                instituciones = em.getReference(Instituciones.class, id);
                instituciones.getIdInstitucion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The instituciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Colaboradores> colaboradoresCollectionOrphanCheck = instituciones.getColaboradoresCollection();
            for (Colaboradores colaboradoresCollectionOrphanCheckColaboradores : colaboradoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instituciones (" + instituciones + ") cannot be destroyed since the Colaboradores " + colaboradoresCollectionOrphanCheckColaboradores + " in its colaboradoresCollection field has a non-nullable idInstitucion field.");
            }
            Collection<Proyectos> proyectosCollectionOrphanCheck = instituciones.getProyectosCollection();
            for (Proyectos proyectosCollectionOrphanCheckProyectos : proyectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instituciones (" + instituciones + ") cannot be destroyed since the Proyectos " + proyectosCollectionOrphanCheckProyectos + " in its proyectosCollection field has a non-nullable idInstitucion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(instituciones);
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

    public List<Instituciones> findInstitucionesEntities() {
        return findInstitucionesEntities(true, -1, -1);
    }

    public List<Instituciones> findInstitucionesEntities(int maxResults, int firstResult) {
        return findInstitucionesEntities(false, maxResults, firstResult);
    }

    private List<Instituciones> findInstitucionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Instituciones.class));
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

    public Instituciones findInstituciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Instituciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstitucionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Instituciones> rt = cq.from(Instituciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
