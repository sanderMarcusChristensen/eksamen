package dat.routes;

import dat.config.HibernateConfig;
import dat.controller.TripController;
import dat.dao.TripDAO;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    TripDAO dao = new TripDAO(emf);
    TripController controller = new TripController(dao);

    public EndpointGroup getTripRoutes() {
        return () -> {

            //CRUD
            get("/", controller::getAll, Role.USER);   // Only users can see all od them
            get("/{id}", controller::getById, Role.ANYONE, Role.ANYONE);
            post("/", controller::create, Role.ANYONE ); // only admins can update
            put("/{id}", controller::update,Role.ANYONE);
            delete("/{id}", controller::delete, Role.ANYONE);

            put("/{tripId}/guides/{guideId}", controller::addGuideToTrip); // Add an existing guide to and existing trip.




        };

    }


}
