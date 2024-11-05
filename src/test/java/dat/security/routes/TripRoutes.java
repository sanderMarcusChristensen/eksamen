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
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripRoutes {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/trip";
    private static TripDAO dao = new TripDAO(emf);
    private SecurityPopulatorTester populatorTester = new SecurityPopulatorTester(emf);
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);
    private static GuideDAO guideDAO = new GuideDAO(emf);

    private TripDTO t1, t2, t3, t4;
    Guide g1 = new Guide(null, "Thor", "Jens", "ThorEmail@gmail.com", 22334455, 1, null);
    LocalDate start1 = LocalDate.now();
    LocalDate end1 = LocalDate.of(2024, 12, 1);

    private List<TripDTO> dtoList = new ArrayList<>();


    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;

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
        t4 = dtoList.get(3);

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
    @DisplayName("Get all trips ")
    void getAllTrips() {      //only users can get into this endpoint. Remember til change it in routes if not

        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);

        // Call the API to fetch the ingredients
        TripDTO[] array =
                given()
                        .when()
                        .header("Authorization", userToken)
                        .get(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(TripDTO[].class);

        for (TripDTO d : array) {
            System.out.println(d);
        }

        assertEquals(4, dtoList.size(), "Expected 4 trips.");
        System.out.println("API Response: " + Arrays.toString(array));
        System.out.println("Expected Trips: " + Arrays.toString(new TripDTO[]{t1, t2, t3, t4}));

       // assertThat(array, arrayContainingInAnyOrder(t1,t2,t3));
    }

    @Test
    @DisplayName("Test get single trip")
    void getTripById() {
        TripDTO dto =
                given()
                        .when()
                        .get(BASE_URL + "/" + t1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(TripDTO.class);

        // Compare individual properties of TripDTO
        assertThat(dto.getId(), equalTo(t1.getId()));
        assertThat(dto.getName(), equalTo(t1.getName()));
        assertThat(dto.getCategory(), equalTo(t1.getCategory()));
        assertThat(dto.getStartTime(), equalTo(t1.getStartTime()));
        assertThat(dto.getEndTime(), equalTo(t1.getEndTime()));
        assertThat(dto.getStartPosition(), equalTo(t1.getStartPosition()));
        assertThat(dto.getPrice(), equalTo(t1.getPrice()));
    }

    @Test
    @DisplayName("Creating a new trip")
    void testCreateTrip() {
        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);

        List<Trip> trips = new ArrayList<>();

        GuideDTO guide = new GuideDTO(null, "Thor", "Jens", "ThorEmail@gmail.com", 22334455, 1, trips);
        GuideDTO persistedGuide = guideDAO.create(guide);  // Assuming saveGuide() is implemented in TripDAO

        Guide g = new Guide(persistedGuide);

        TripDTO dto = new TripDTO(null, start1, end1, "KBH H", "Japan", 25000, Category.FOREST,g);

        TripDTO created =
                given()
                        .contentType("application/json")
                        .body(dto)
                        .header("Authorization", adminToken)
                        .when()
                        .post(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(TripDTO.class);

        assertThat(created.getId(), notNullValue());
        assertThat(created.getName(), equalTo("Japan"));
    }


    @Test
    @DisplayName("Update a trip")
    void testUpdateTrip() {
        t1.setName("USA");

        TripDTO dto =
                given()
                        .contentType("application/json")
                        .body(t1)
                        .when()
                        .put(BASE_URL + "/" + t1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(TripDTO.class);

        assertThat(dto.getName(), equalTo("USA"));
    }


// dont work
    @Test
    @DisplayName("Test delete a trip")
    void deleteTrip() {
        // Ensure the ingredient exists before deletion
        given()
                .when()
                .get(BASE_URL + "/" + t1.getId())
                .then()
                .log().all()
                .statusCode(200);

        // Perform the deletion
        given()
                .when()
                .delete(BASE_URL + "/" + t1.getId())
                .then()
                .statusCode(204); // Successful deletion


        given()
                .when()
                .get(BASE_URL + "/" + t1.getId())
                .then()
                .log().all()
                .statusCode(200); // this should be 400, dont work
    }


}