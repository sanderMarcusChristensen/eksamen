package dat.controller;

import dat.config.HibernateConfig;
import dat.dao.TripDAO;
import dat.dto.TripDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TripController implements IController {

    private final TripDAO dao;

    public TripController(TripDAO dao) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = new TripDAO(emf);


    }

    @Override
    public void getAll(Context ctx) {

            List<TripDTO> trips = dao.getAll();

            if (trips.isEmpty()) {
                throw new ApiException(404, "Trips not found in list");
            }
            ctx.json(trips);
            ctx.status(200);
    }

    @Override
    public void getById(Context ctx) {

        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO dto = dao.getById(id);
            if (dto != null) {
                ctx.json(dto);
                ctx.status(200);
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

            if (dto.getName() == null || tripDTO.getName().isEmpty()) {
                throw new ApiException(400, "trip name is required to create");
            }
            if (dto.getEndTime() == null) {
                throw new ApiException(400, "trip endTime is required to create");
            }
            if (dto.getStartTime() == null) {
                throw new ApiException(400, "trip startTime is required to create");
            }
            if (dto.getPrice() == 0) {
                throw new ApiException(400, "trip price is required to create");
            }
            if (dto.getCategory() == null) {
                throw new ApiException(400, "trip category is required to create");
            }
            if (dto.getGuide() == null) {
                throw new ApiException(400, "trip guide is required to create");
            }

            ctx.res().setStatus(201);
            ctx.json(dto, TripDTO.class);

    }

    @Override
    public void update(Context ctx) {

        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO dto = ctx.bodyAsClass(TripDTO.class);

            TripDTO updatedDTO = dao.update(id, dto);

            if (updatedDTO.getName() == null || updatedDTO.getName().isEmpty()) {
                throw new ApiException(400, "trip name is required to create");
            }
            if (updatedDTO.getEndTime() == null) {
                throw new ApiException(400, "trip endTime is required to create");
            }
            if (updatedDTO.getStartTime() == null) {
                throw new ApiException(400, "trip startTime is required to create");
            }
            if (updatedDTO.getPrice() == 0) {
                throw new ApiException(400, "trip price is required to create");
            }
            if (updatedDTO.getCategory() == null) {
                throw new ApiException(400, "trip category is required to create");
            }
            if (updatedDTO.getGuide() == null) {
                throw new ApiException(400, "trip guide is required to create");
            }

            if (updatedDTO != null) {
                ctx.json(updatedDTO);  // Return the updated doctor data as JSON
                ctx.status(200);
            }

        } catch (Exception e) {
            throw new ApiException(500,"Something went wrong trying to update the trip");
        }

    }

    @Override
    public void delete(Context ctx) {

        try {

            Long id = Long.parseLong(ctx.pathParam("id"));

            if(id == null || id == 0){
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

    }



    /*

    public void daoReturnCheck(Context ctx){

        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
        TripDTO dto = dao.create(tripDTO);

        if (dto.getName() == null || tripDTO.getName().isEmpty()) {
            throw new ApiException(400, "trip name is required to create");
        }
        if (dto.getEndTime() == null) {
            throw new ApiException(400, "trip endTime is required to create");
        }
        if (dto.getStartTime() == null) {
            throw new ApiException(400, "trip startTime is required to create");
        }
        if (dto.getPrice() == 0) {
            throw new ApiException(400, "trip price is required to create");
        }
        if (dto.getCategory() == null) {
            throw new ApiException(400, "trip category is required to create");
        }
        if (dto.getGuide() == null) {
            throw new ApiException(400, "trip guide is required to create");
        }
    }

     */



