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
        start2 = LocalDate.of(2024,11,16);
        start3 = LocalDate.of(2024,12,15);
        start4 = LocalDate.of(2025,1,1);

        end1 = LocalDate.of(2024,12,1);
        end2 = LocalDate.of(2025,1,1);
        end3 = LocalDate.of(2025,2,5);
        end1 = LocalDate.of(2025,1,14);

        List<Trip> g1Trips = new ArrayList<>();

        g1 = new Guide(null,"Sander", "Christensen","Email@gamil.com",55667788, 3,g1Trips );


        g1Trips.add(t1);
        g1Trips.add(t2);

        List<Trip> g2Trips = new ArrayList<>();

        g2 = new Guide(null,"Jon","Doe","Email@gamil.com",11223344, 5, g2Trips );
        g2Trips.add(t3);
        g2Trips.add(t4);

        t1 = new Trip(null, start1,end1,"KBH H","Japan",25000, Category.CITY,g1);
        t2 = new Trip(null, start2,end2,"Billund Lufthavn","Australian",27999, Category.FOREST,g1);
        t3 = new Trip(null, start3,end3,"Roskilde Lufthavn","Bali",21999, Category.BEACH,g2);
        t4 = new Trip(null, start4,end4,"KBH H","Gili-T",24999, Category.SEA,g2);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(g1);
            em.persist(g2);
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.persist(t4);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            em.close();
        }









    }




}
