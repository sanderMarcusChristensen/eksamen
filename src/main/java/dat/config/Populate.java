package dat.config;


import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Populate {

    private final EntityManagerFactory emf;
    private Trip t1,t2,t3,t4;
    private Guide g1,g2;
    LocalDate start1,start2,start3,start4;
    LocalDate end1,end2,end3,end4;


    public Populate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void populate() {

        start1 = LocalDate.now();
        start2 = LocalDate.of(2024, 11, 16);
        start3 = LocalDate.of(2024, 12, 15);
        start4 = LocalDate.of(2025, 1, 1);

        end1 = LocalDate.of(2024, 12, 1);
        end2 = LocalDate.of(2025, 1, 1);
        end3 = LocalDate.of(2025, 2, 5);
        end4 = LocalDate.of(2025, 1, 14);

        // Initialize Guide
        g1 = new Guide(null, "Sander", "Christensen", "Email@gmail.com", 55667788, 3, new ArrayList<>());
        g2 = new Guide(null, "Jon", "Doe", "Email@gmail.com", 11223344, 5, new ArrayList<>());

        // Initialize Trip
        t1 = new Trip(null, start1, end1, "KBH H", "Japan", 25000, Category.CITY, g1);
        t2 = new Trip(null, start2, end2, "Billund Lufthavn", "Australian", 27999, Category.FOREST, g1);
        t3 = new Trip(null, start3, end3, "Roskilde Lufthavn", "Bali", 21999, Category.BEACH, g2);
        t4 = new Trip(null, start4, end4, "KBH H", "Gili-T", 24999, Category.SEA, g2);

        // Add trips to guides
        g1.getTrips().add(t1);
        g1.getTrips().add(t2);
        g2.getTrips().add(t3);
        g2.getTrips().add(t4);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }








}





