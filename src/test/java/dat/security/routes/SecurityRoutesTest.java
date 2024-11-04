package dat.security.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dat.utils.ApiProperties;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class SecurityRoutesTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);
    private static Javalin app;
    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;        //skal bruges ved test af endpoints med security
    private static final String BASE_URL = "http://localhost:7007/api";


    @BeforeAll
    void beforeAll() {
        HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(ApiProperties.PORT);
    }

    @BeforeEach
    void setUp() {
        UserDTO[] users = SecurityPopulatorTester.createTestUser(emf);
        userDTO = users[0];
        adminDTO = users[1];

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        }
        catch (ValidationException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void tearDown() {


        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            //em.createQuery("DELETE FROM Room ").executeUpdate();
            //em.createQuery("DELETE FROM Hotel ").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    @DisplayName("Test endpoint /auth/test")
    void test() {
        given()
                .when()
                .get(BASE_URL + "/auth/test")
                .then()
                .statusCode(200)
                .body("msg", equalTo("Hello from Open"));
    }

    @Test
    @DisplayName("Test endpoint /auth/register/")
    void testRegister() {
        UserDTO test = new UserDTO("Tester123456","1234");

        given()
        .body(test)
                .when()
                .post(BASE_URL + "/auth/register")
                .then()
                .statusCode(201)
                .body("username", equalTo(test.getUsername()));


    }




}