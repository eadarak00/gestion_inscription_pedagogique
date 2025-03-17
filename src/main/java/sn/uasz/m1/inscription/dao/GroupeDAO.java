package sn.uasz.m1.inscription.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
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

    public List<Groupe> findByFormationID(Formation formation) {
        if (formation == null || formation.getId() == null) {
            throw new IllegalArgumentException("La formation ne peut pas être null et doit avoir un ID valide.");
        }

        List<Groupe> groupes = new ArrayList<>();

        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            entityManager.getTransaction().begin();

            // Requête JPQL avec jointure explicite
            String jpql = "SELECT g FROM Groupe g JOIN FETCH g.formation f WHERE f.id = :formationId";
            groupes = entityManager.createQuery(jpql, Groupe.class)
                    .setParameter("formationId", formation.getId())
                    .getResultList();

            entityManager.getTransaction().commit();

            groupes.size();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void merge(Groupe groupe) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(groupe);
            transaction.commit();
        }
    }

    public List<Groupe> findGroupesByFormation(Long formationId, TypeGroupe type) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT g FROM Groupe g WHERE g.formation.id = :formationId AND g.type = :type", Groupe.class)
                    .setParameter("formationId", formationId)
                    .setParameter("type", type)
                    .getResultList();
        }
    }

    public void add(Groupe groupe) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(groupe); // 🔹 Ajoute un nouveau groupe
            transaction.commit();
        }
    }

    public List<Etudiant> findEtudiantsRepartis(Long formationId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            return entityManager.createQuery(
                    "SELECT DISTINCT e FROM Groupe g JOIN g.etudiants e WHERE g.formation.id = :formationId",
                    Etudiant.class)
                    .setParameter("formationId", formationId)
                    .getResultList();
        }
    }

    // public List<Etudiant> findEtudiantsByGroupeId(Long groupeId) {
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         // Charger le groupe par ID
    //         Groupe groupe = entityManager.find(Groupe.class, groupeId);

    //         if (groupe == null) {
    //             return Collections.emptyList();
    //         }

    //         // Retourner la liste des étudiants du groupe
    //         return groupe.getEtudiants();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erreur lors de la récupération des étudiants du groupe", e);
    //     }
    // }

    // public List<Etudiant> findEtudiantsByGroupe(Long groupeId) {
    //     try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
    //         String query = "SELECT e FROM Etudiant e JOIN e.groupes g WHERE g.id = :groupeId";
    //         return entityManager.createQuery(query, Etudiant.class)
    //                             .setParameter("groupeId", groupeId)
    //                             .getResultList();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erreur lors de la récupération des étudiants pour le groupe ID: " + groupeId, e);
    //     }
    // }

    public List<Etudiant> findEtudiantsByGroupe(Long groupeId) {
        try (EntityManager entityManager = DatabaseUtil.getEntityManager()) {
            String query = "SELECT DISTINCT e FROM Groupe g JOIN g.etudiants e WHERE g.id = :groupeId";
            return entityManager.createQuery(query, Etudiant.class)
                                .setParameter("groupeId", groupeId)
                                .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des étudiants pour le groupe ID: " + groupeId, e);
        }
    }
    
    


}
