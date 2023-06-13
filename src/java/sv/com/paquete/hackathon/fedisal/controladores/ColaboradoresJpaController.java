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
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.PreexistingEntityException;
import sv.com.paquete.hackathon.fedisal.controladores.exceptions.RollbackFailureException;
import sv.com.paquete.hackathon.fedisal.entidades.Colaboradores;
import sv.com.paquete.hackathon.fedisal.entidades.Instituciones;

/**
 *
 * @author HP
 */
public class ColaboradoresJpaController implements Serializable {

    public ColaboradoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colaboradores colaboradores) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Instituciones idInstitucion = colaboradores.getIdInstitucion();
            if (idInstitucion != null) {
                idInstitucion = em.getReference(idInstitucion.getClass(), idInstitucion.getIdInstitucion());
                colaboradores.setIdInstitucion(idInstitucion);
            }
            em.persist(colaboradores);
            if (idInstitucion != null) {
                idInstitucion.getColaboradoresCollection().add(colaboradores);
                idInstitucion = em.merge(idInstitucion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findColaboradores(colaboradores.getDui()) != null) {
                throw new PreexistingEntityException("Colaboradores " + colaboradores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Colaboradores colaboradores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Colaboradores persistentColaboradores = em.find(Colaboradores.class, colaboradores.getDui());
            Instituciones idInstitucionOld = persistentColaboradores.getIdInstitucion();
            Instituciones idInstitucionNew = colaboradores.getIdInstitucion();
            if (idInstitucionNew != null) {
                idInstitucionNew = em.getReference(idInstitucionNew.getClass(), idInstitucionNew.getIdInstitucion());
                colaboradores.setIdInstitucion(idInstitucionNew);
            }
            colaboradores = em.merge(colaboradores);
            if (idInstitucionOld != null && !idInstitucionOld.equals(idInstitucionNew)) {
                idInstitucionOld.getColaboradoresCollection().remove(colaboradores);
                idInstitucionOld = em.merge(idInstitucionOld);
            }
            if (idInstitucionNew != null && !idInstitucionNew.equals(idInstitucionOld)) {
                idInstitucionNew.getColaboradoresCollection().add(colaboradores);
                idInstitucionNew = em.merge(idInstitucionNew);
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
                Integer id = colaboradores.getDui();
                if (findColaboradores(id) == null) {
                    throw new NonexistentEntityException("The colaboradores with id " + id + " no longer exists.");
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
            Colaboradores colaboradores;
            try {
                colaboradores = em.getReference(Colaboradores.class, id);
                colaboradores.getDui();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaboradores with id " + id + " no longer exists.", enfe);
            }
            Instituciones idInstitucion = colaboradores.getIdInstitucion();
            if (idInstitucion != null) {
                idInstitucion.getColaboradoresCollection().remove(colaboradores);
                idInstitucion = em.merge(idInstitucion);
            }
            em.remove(colaboradores);
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

    public List<Colaboradores> findColaboradoresEntities() {
        return findColaboradoresEntities(true, -1, -1);
    }

    public List<Colaboradores> findColaboradoresEntities(int maxResults, int firstResult) {
        return findColaboradoresEntities(false, maxResults, firstResult);
    }

    private List<Colaboradores> findColaboradoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colaboradores.class));
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

    public Colaboradores findColaboradores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colaboradores.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaboradoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colaboradores> rt = cq.from(Colaboradores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
