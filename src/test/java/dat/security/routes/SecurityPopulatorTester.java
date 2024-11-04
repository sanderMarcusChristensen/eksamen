package dat.security.routes;

import dat.config.HibernateConfig;
import dat.dto.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.entities.Role;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SecurityPopulatorTester {



    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

    private TripDTO t1,t2,t3,t4;
    private Guide g1,g2;
    LocalDate start1,start2,start3,start4;
    LocalDate end1,end2,end3,end4;


    public SecurityPopulatorTester(EntityManagerFactory emf){
        this.emf = emf;

    }
    public static UserDTO[] createTestUser(EntityManagerFactory emf) {
        User user, admin;
        Role userRole, adminRole;

        user = new User("UserSander", "UserSander123");
        admin = new User("AdminSander", "AdminSander123");
        userRole = new Role("USER");
        adminRole = new Role("ADMIN");
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }
        UserDTO userDTO = new UserDTO(user.getUsername(), "UserSander123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "AdminSander123");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public void cleanUp(){

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createQuery("DELETE FROM Guide ").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TripDTO> populate() {
        /*

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

         */

        List<TripDTO> list = new ArrayList<>();

        // Initialize Trip
        t1 = new TripDTO(1L, start1, end1, "KBH H", "Japan", 25000, Category.CITY, g1);
        t2 = new TripDTO(2L, start2, end2, "Billund Lufthavn", "Australian", 27999, Category.FOREST, g1);
        t3 = new TripDTO(3L, start3, end3, "Roskilde Lufthavn", "Bali", 21999, Category.BEACH, g2);
        t4 = new TripDTO(4L, start4, end4, "KBH H", "Gili-T", 24999, Category.SEA, g2);

        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);

        Trip savedTrip1 = new Trip(t1);
        Trip savedTrip2 = new Trip(t2);
        Trip savedTrip3 = new Trip(t3);
        Trip savedTrip4 = new Trip(t4);



        // Add trips to guides
       // g1.getTrips().add(t1);
       // g1.getTrips().add(t2);
       // g2.getTrips().add(t3);
       // g2.getTrips().add(t4);


        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //em.persist(g1);
            //em.persist(g2);
            em.persist(savedTrip1);
            em.persist(savedTrip2);
            em.persist(savedTrip3);
            em.persist(savedTrip4);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return list;
    }


}
