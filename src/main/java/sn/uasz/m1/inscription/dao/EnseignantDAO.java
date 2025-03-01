package sn.uasz.m1.inscription.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class EnseignantDAO implements IDAO<Enseignant> {
    @Override
    public Enseignant save(Enseignant o){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
            return o;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde", e);
        }
    }

    @Override
    public Enseignant findById(Long id){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
           return entityManager.find(Enseignant.class, id); 
        }
    }

    @Override
    public List<Enseignant> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT r FROM Enseignant r", Enseignant.class)
                    .getResultList();
        }
    }

    @Override
    public Enseignant update(Long id , Enseignant o){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
            entityManager.getTransaction().begin();
                Enseignant existing = entityManager.find(Enseignant.class, id);
                if(existing==null){
                    entityManager.getTransaction().rollback();
                    throw new IllegalArgumentException("Aucun Enseignant trouvé avec l'ID : " + id);
                }
                    existing.setNom(o.getNom());
                    existing.setPrenom(o.getPrenom());
                    existing.setEmail(o.getEmail());
                    existing.setSpecialite(o.getSpecialite());

                    Enseignant updated = entityManager.merge(existing);
                    entityManager.getTransaction().commit();
                    return updated;
            
                }  catch (Exception e) {
                    throw new RuntimeException("Erreur lors de la mise à jour", e);
                }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            Enseignant enseignant = entityManager.find(Enseignant.class, id);
            if (enseignant == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Enseigna trouvé avec l'ID : " + id);
            }
            entityManager.remove(enseignant);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

}
