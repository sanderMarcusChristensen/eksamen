package dat.security.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.GuideDAO;
import dat.dao.TripDAO;
import dat.dto.GuideDTO;
import dat.dto.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dat.utils.ApiProperties;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class TripDAOTest {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/trip";
    private static TripDAO tripDAOdao = new TripDAO(emf);
    private static GuideDAO guideDAO = new GuideDAO(emf);
    private SecurityPopulatorTester populatorTester = new SecurityPopulatorTester(emf);
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);

    private List<TripDTO> dtoList = new ArrayList<>();
    LocalDate start1 = LocalDate.now();
    LocalDate end1 = LocalDate.of(2024, 12, 1);

    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;
    private TripDTO t1, t2, t3, t4;


    @BeforeAll
    static void init() {
        app = ApplicationConfig.startServer(ApiProperties.PORT);
    }

    @AfterEach
    void tearDown() {
        populatorTester.cleanUp();
    }

    @AfterAll
    static void afterAll() {
        ApplicationConfig.stopServer(app);
    }

    @BeforeEach
    void setUp() {
        dtoList = populatorTester.populate();
        t1 = dtoList.get(0);
        t2 = dtoList.get(1);
        t3 = dtoList.get(2);

        UserDTO[] users = SecurityPopulatorTester.createTestUser(emf);
        userDTO = users[0];
        adminDTO = users[1];

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Creating a doctor ")
    void create() {

        List<Trip> trips = new ArrayList<>();

        GuideDTO guide = new GuideDTO(null, "Thor", "Jens", "ThorEmail@gmail.com", 22334455, 1, trips);
        GuideDTO persistedGuide = guideDAO.create(guide);

        Guide g = new Guide(persistedGuide);

        TripDTO dto = new TripDTO(null, start1, end1, "KBH H", "Japan", 25000, Category.FOREST, g);

        assertThat(tripDAOdao.getAll(), hasSize(4));
        tripDAOdao.create(dto);
        assertThat(tripDAOdao.getAll(), hasSize(5));

    }


}
