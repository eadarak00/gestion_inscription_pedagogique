package sn.uasz.m1.inscription.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.Ue;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class UeDAO implements IDAO<Ue>{

    @Override
    public Ue save(Ue o){
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
    public Ue findById(Long id){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
           return entityManager.find(Ue.class, id); 
        }
    }

    @Override
    public List<Ue> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT r FROM Ue r",Ue.class)
                    .getResultList();
        }
    }

    @Override
    public Ue update(Long id , Ue o){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
            entityManager.getTransaction().begin();
                Ue existing = entityManager.find(Ue.class, id);
                if(existing==null){
                    entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Ue trouvé avec l'ID : " + id);
                }
                    existing.setCode(o.getCode());
                    existing.setNom(o.getNom());
                    existing.setVolumeHoraire(o.getVolumeHoraire());
                    existing.setCoefficient(o.getCoefficient());
                    existing.setCredit(o.getCredit());
                    existing.setObligatoire(o.isObligatoire());

                    Ue updated = entityManager.merge(existing);
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
            Ue ue = entityManager.find(Ue.class, id);
            if (ue == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Ue trouvé avec l'ID : " + id);
            }
            entityManager.remove(ue);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }
    public List<Ue> trouverParEnseignant(Long id){
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            return entityManager.createQuery("SELECT u FROM Ue u WHERE u.enseignant.id = :id", Ue.class)
                                .setParameter("enseignantId",id )
                                .getResultList();

        }
    }
}
