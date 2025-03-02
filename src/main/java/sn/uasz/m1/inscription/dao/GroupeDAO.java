package sn.uasz.m1.inscription.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.utils.DatabaseUtil;

public class GroupeDAO implements IDAO<Groupe> {

    @Override
    public Groupe save(Groupe o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
            return o;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du groupe", e);
        }
    }

    @Override
    public Groupe findById(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.find(Groupe.class, id);
        }
    }

    @Override
    public List<Groupe> findAll() {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class)
                    .getResultList();
        }
    }

    // public List<Groupe> findByFormationResponsable(ResponsablePedagogique
    // responsable) {
    // try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    // return entityManager.createQuery(
    // "SELECT g FROM Groupe g WHERE g.formation.responsable = :responsable",
    // Groupe.class)
    // .setParameter("responsable", responsable)
    // .getResultList();
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new ArrayList<>();
    // }
    // }

    public List<Groupe> findByFormationResponsable(ResponsablePedagogique responsable) {
        EntityManager entityManager = DatabaseUtil.getEntityManager();
        List<Groupe> groupes = new ArrayList<>();

        try {
            // Utilisation d'une jointure explicite pour plus de clarté
            String jpql = "SELECT g FROM Groupe g JOIN g.formation f WHERE f.responsable.id = :responsableId";

            // Exécution de la requête avec le paramètre du responsable
            groupes = entityManager.createQuery(jpql, Groupe.class)
                    .setParameter("responsableId", responsable.getId())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Vous pourriez vouloir logger l'exception pour des raisons de suivi
        } finally {
            entityManager.close(); // Assurez-vous de toujours fermer l'EntityManager
        }

        return groupes;
    }

    @Override
    public Groupe update(Long id, Groupe o) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Vérification de l'existence
            Groupe existing = entityManager.find(Groupe.class, id);
            if (existing == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Groupe trouvé avec l'ID : " + id);
            }

            // Mise à jour des champs
            existing.setCapacite(o.getCapacite());
            existing.setType(o.getType());

            Groupe updated = entityManager.merge(existing);
            entityManager.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            Groupe groupe = entityManager.find(Groupe.class, id);
            if (groupe == null) {
                entityManager.getTransaction().rollback();
                throw new IllegalArgumentException("Aucun Groupe trouvé avec l'ID : " + id);
            }
            entityManager.remove(groupe);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

}
