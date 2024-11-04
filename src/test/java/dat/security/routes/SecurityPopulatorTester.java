package dat.security.routes;

import dat.config.HibernateConfig;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.entities.Role;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;




public class SecurityPopulatorTester {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();


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

}
