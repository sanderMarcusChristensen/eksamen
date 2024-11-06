package dat.controller;

import dat.config.HibernateConfig;
import dat.dao.TripDAO;
import dat.dto.TripDTO;
import dat.entities.Category;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TripController implements IController {

    private final TripDAO dao;      // I need methods from dao to create and update fx

    public TripController(TripDAO dao) {    // My controller needs to know what dao it's working with
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = new TripDAO(emf);    // My DAO needs to know what database its getting data to and from

    }

    @Override
    public void getAll(Context ctx) {

            List<TripDTO> trips = dao.getAll();     // Calls the getall in dao and get's the list

            if (trips.isEmpty()) {
                throw new ApiException(404, "Trips not found in list"); // If no trips is in the list
            }
            ctx.json(trips);    // Puts it out as Json
            ctx.status(200);    // Sends out status-code 200 for "everything good"
    }

    @Override
    public void getById(Context ctx) {

        try {
            Long id = Long.parseLong(ctx.pathParam("id"));      // Takes whatever id is put into the request
            TripDTO dto = dao.getById(id);                      // Takes the id into the dao - method getByid
            if (dto != null) {
                ctx.json(dto);    // Puts it out as Json
                ctx.status(200);   // Sends out status-code 200 for "everything good"
            } else {
                throw new ApiException(400, "Failed to get trips");
            }

        } catch (Exception e) {
            throw new ApiException(500, "Server error");
        }


    }

    @Override
    public void create(Context ctx) {

            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            TripDTO dto = dao.create(tripDTO);

            TripDTO checkedDTO = checkPoint(dto);

            ctx.res().setStatus(201);
            ctx.json(checkedDTO, TripDTO.class);

    }

    @Override
    public void update(Context ctx) {

        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO dto = ctx.bodyAsClass(TripDTO.class);

            TripDTO updatedDTO = dao.update(id, dto);
            TripDTO checkedDTO = checkPoint(updatedDTO);

            ctx.json(checkedDTO);
            ctx.status(200);

        } catch (Exception e) {
            throw new ApiException(500,"Something went wrong trying to update the trip");
        }

    }

    @Override
    public void delete(Context ctx) {

        try {

            Long id = Long.parseLong(ctx.pathParam("id"));

            if(id == 0){
                throw new ApiException(400,"id can't not be null");
            }
            dao.getById(id);
            ctx.res().setStatus(204);

        } catch (Exception e) {
            throw new ApiException(500, "Server error");
        }


    }

    public void addGuideToTrip(Context ctx){
        try {
            // Extract tripId and guideId from the URL parameters
            Long tripId = Long.parseLong(ctx.pathParam("tripId"));
            Long guideId = Long.parseLong(ctx.pathParam("guideId"));

            // Call the DAO method to add the guide to the trip
            dao.addGuideToTrip(tripId.intValue(), guideId.intValue());

            // Respond with 204 No Content
            ctx.status(204);

        } catch (Exception e) {
            throw new ApiException(500, "An unexpected error occurred: " + e.getMessage());
        }
    }

    // -------------------------- TASK 5 Streams - didn't make in time  --------------------------

    public void getTripsByCategory(Context ctx){
        try {
            String specialityParam = ctx.pathParam("category").toUpperCase();
            Category category = Category.valueOf(specialityParam); // Convert string to Speciality enum
            List<TripDTO> trips = dao.getByTripCategory(category);

            if (trips.isEmpty()) {
                throw new ApiException(404, "No doctors found for speciality: " + specialityParam);
            }

            ctx.json(trips);
            ctx.status(200);
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid speciality provided.");
        } catch (Exception e) {
            throw new ApiException(500, "Server error while retrieving doctors by speciality.");
        }
    }


    public TripDTO checkPoint ( TripDTO toCheck ){

        if (toCheck.getName() == null || toCheck.getName().isEmpty()) {
            throw new ApiException(400, "trip name is required to create");
        }
        if (toCheck.getEndTime() == null) {
            throw new ApiException(400, "trip endTime is required to create");
        }
        if (toCheck.getStartTime() == null) {
            throw new ApiException(400, "trip startTime is required to create");
        }
        if (toCheck.getPrice() >= 980) {
            throw new ApiException(400, "trip price is required to create");
        }
        if (toCheck.getCategory() == null) {
            throw new ApiException(400, "trip category is required to create");
        }
        if (toCheck.getGuide() == null) {
            throw new ApiException(400, "trip guide is required to create");
        }

        return toCheck;


    }



    }







