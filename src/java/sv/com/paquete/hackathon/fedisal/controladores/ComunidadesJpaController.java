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
import sv.com.paquete.hackathon.fedisal.entidades.Proyectos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.IllegalOrphanException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.NonexistentEntityException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.RollbackFailureException;
import sv.com.paquete.hackathon.fedisal.entidades.Comunidades;

/**
 *
 * @author HP
 */
public class ComunidadesJpaController implements Serializable {

    public ComunidadesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comunidades comunidades) throws RollbackFailureException, Exception {
        if (comunidades.getProyectosCollection() == null) {
            comunidades.setProyectosCollection(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Proyectos> attachedProyectosCollection = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionProyectosToAttach : comunidades.getProyectosCollection()) {
                proyectosCollectionProyectosToAttach = em.getReference(proyectosCollectionProyectosToAttach.getClass(), proyectosCollectionProyectosToAttach.getIdProyecto());
                attachedProyectosCollection.add(proyectosCollectionProyectosToAttach);
            }
            comunidades.setProyectosCollection(attachedProyectosCollection);
            em.persist(comunidades);
            for (Proyectos proyectosCollectionProyectos : comunidades.getProyectosCollection()) {
                Comunidades oldIdComunidadOfProyectosCollectionProyectos = proyectosCollectionProyectos.getIdComunidad();
                proyectosCollectionProyectos.setIdComunidad(comunidades);
                proyectosCollectionProyectos = em.merge(proyectosCollectionProyectos);
                if (oldIdComunidadOfProyectosCollectionProyectos != null) {
                    oldIdComunidadOfProyectosCollectionProyectos.getProyectosCollection().remove(proyectosCollectionProyectos);
                    oldIdComunidadOfProyectosCollectionProyectos = em.merge(oldIdComunidadOfProyectosCollectionProyectos);
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

    public void edit(Comunidades comunidades) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Comunidades persistentComunidades = em.find(Comunidades.class, comunidades.getIdComunidad());
            Collection<Proyectos> proyectosCollectionOld = persistentComunidades.getProyectosCollection();
            Collection<Proyectos> proyectosCollectionNew = comunidades.getProyectosCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyectos proyectosCollectionOldProyectos : proyectosCollectionOld) {
                if (!proyectosCollectionNew.contains(proyectosCollectionOldProyectos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyectos " + proyectosCollectionOldProyectos + " since its idComunidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyectos> attachedProyectosCollectionNew = new ArrayList<Proyectos>();
            for (Proyectos proyectosCollectionNewProyectosToAttach : proyectosCollectionNew) {
                proyectosCollectionNewProyectosToAttach = em.getReference(proyectosCollectionNewProyectosToAttach.getClass(), proyectosCollectionNewProyectosToAttach.getIdProyecto());
                attachedProyectosCollectionNew.add(proyectosCollectionNewProyectosToAttach);
            }
            proyectosCollectionNew = attachedProyectosCollectionNew;
            comunidades.setProyectosCollection(proyectosCollectionNew);
            comunidades = em.merge(comunidades);
            for (Proyectos proyectosCollectionNewProyectos : proyectosCollectionNew) {
                if (!proyectosCollectionOld.contains(proyectosCollectionNewProyectos)) {
                    Comunidades oldIdComunidadOfProyectosCollectionNewProyectos = proyectosCollectionNewProyectos.getIdComunidad();
                    proyectosCollectionNewProyectos.setIdComunidad(comunidades);
                    proyectosCollectionNewProyectos = em.merge(proyectosCollectionNewProyectos);
                    if (oldIdComunidadOfProyectosCollectionNewProyectos != null && !oldIdComunidadOfProyectosCollectionNewProyectos.equals(comunidades)) {
                        oldIdComunidadOfProyectosCollectionNewProyectos.getProyectosCollection().remove(proyectosCollectionNewProyectos);
                        oldIdComunidadOfProyectosCollectionNewProyectos = em.merge(oldIdComunidadOfProyectosCollectionNewProyectos);
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
                Integer id = comunidades.getIdComunidad();
                if (findComunidades(id) == null) {
                    throw new NonexistentEntityException("The comunidades with id " + id + " no longer exists.");
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
            Comunidades comunidades;
            try {
                comunidades = em.getReference(Comunidades.class, id);
                comunidades.getIdComunidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunidades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyectos> proyectosCollectionOrphanCheck = comunidades.getProyectosCollection();
            for (Proyectos proyectosCollectionOrphanCheckProyectos : proyectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comunidades (" + comunidades + ") cannot be destroyed since the Proyectos " + proyectosCollectionOrphanCheckProyectos + " in its proyectosCollection field has a non-nullable idComunidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comunidades);
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

    public List<Comunidades> findComunidadesEntities() {
        return findComunidadesEntities(true, -1, -1);
    }

    public List<Comunidades> findComunidadesEntities(int maxResults, int firstResult) {
        return findComunidadesEntities(false, maxResults, firstResult);
    }

    private List<Comunidades> findComunidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comunidades.class));
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

    public Comunidades findComunidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comunidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comunidades> rt = cq.from(Comunidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
