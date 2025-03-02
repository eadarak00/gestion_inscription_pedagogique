package sn.uasz.m1.inscription.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class UEDAO {

    // @Override
    // public UE save(UE o){
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         entityManager.getTransaction().begin();
    //         entityManager.persist(o);
    //         entityManager.getTransaction().commit();
    //         return o;
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erreur lors de la sauvegarde", e);
    //     }
    // }

    // @Override
    // public UE findById(Long id){
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
    //        return entityManager.find(UE.class, id); 
    //     }
    // }

    // @Override
    // public List<UE> findAll() {
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         return entityManager.createQuery("SELECT r FROM UE r",UE.class)
    //                 .getResultList();
    //     }
    // }

    // @Override
    // public UE update(Long id , UE o){
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()){
    //         entityManager.getTransaction().begin();
    //             UE existing = entityManager.find(UE.class, id);
    //             if(existing==null){
    //                 entityManager.getTransaction().rollback();
    //             throw new IllegalArgumentException("Aucun Ue trouvé avec l'ID : " + id);
    //             }
    //                 existing.setCode(o.getCode());
    //                 existing.setNom(o.getNom());
    //                 existing.setVolumeHoraire(o.getVolumeHoraire());
    //                 existing.setCoefficient(o.getCoefficient());
    //                 existing.setCredit(o.getCredit());
    //                 existing.setObligatoire(o.isObligatoire());

    //                 UE updated = entityManager.merge(existing);
    //                 entityManager.getTransaction().commit();
    //                 return updated;
            
    //             }  catch (Exception e) {
    //                 throw new RuntimeException("Erreur lors de la mise à jour", e);
    //             }
    // }

    // @Override
    // public void delete(Long id) {
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         entityManager.getTransaction().begin();
    //         UE ue = entityManager.find(UE.class, id);
    //         if (ue == null) {
    //             entityManager.getTransaction().rollback();
    //             throw new IllegalArgumentException("Aucun UE trouvé avec l'ID : " + id);
    //         }
    //         entityManager.remove(ue);
    //         entityManager.getTransaction().commit();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erreur lors de la suppression", e);
    //     }
    // }
    // public List<UE> trouverParEnseignant(Long id){
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         entityManager.getTransaction().begin();
    //         return entityManager.createQuery("SELECT u FROM UE u WHERE u.enseignant.id = :id", UE.class)
    //                             .setParameter("enseignantId",id )
    //                             .getResultList();

    //     }
    // }
}
