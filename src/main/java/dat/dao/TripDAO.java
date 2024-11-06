package dat.dao;

import dat.dto.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TripDAO implements IDAO<TripDTO, Long>, ITripGuideDAO {

    private EntityManagerFactory emf;

    public TripDAO(EntityManagerFactory emf) {
        this.emf = emf;

    }

    @Override
    public TripDTO getById(Long id) {                           //Task a id in
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);                // Uses em.find search for the Trip with that id
            return trip != null ? new TripDTO(trip) : null;     // Returns if not null
        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);    // Finds all trips entity's in database

            List<Trip> tripList = query.getResultList();    // Safes them to a list
            List<TripDTO> tripDTOList = new ArrayList<>();
            for (Trip t : tripList) {                       // Loops over the list to convert to DTO
                tripDTOList.add(new TripDTO(t));    // Adds them
            }
            return tripDTOList; // Returns to list
        }
    }

    // could add more "security" to this later, right now it only checks on name;
    @Override
    public TripDTO create(TripDTO tripDTO) {
        Trip trip = new Trip(tripDTO);                          // Convert the DTO to entity
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            /*
                                                    // Error on name-check before persisting it into database
            Trip existingDoctor = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", trip.getName())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingDoctor != null) {
                throw new ApiException(400, "Trip " + trip.getName() + " already exists.");
            }

             */

            em.persist(trip);                                   // Persist it into the database
            em.getTransaction().commit();


            return new TripDTO(trip);                       // Convert the entity to DTO and returns it
        }
    }

    @Override
    public TripDTO update(Long id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip exsitingTrip = em.find(Trip.class, id);        // Uses em.find search for the Trip with that id

            if (exsitingTrip == null) {
                em.getTransaction().rollback();
                return null;                                    // If there isn't a trip with that id, returns null
            }

            if (tripDTO.getName() != null) {                    // Making sure nothing is null
                exsitingTrip.setName(tripDTO.getName());
            }
            if (tripDTO.getId() != null) {                      // Updating by setting the new data on the old data
                exsitingTrip.setId(tripDTO.getId());
            }
            if (tripDTO.getEndTime() != null) {
                exsitingTrip.setEndTime(tripDTO.getEndTime());
            }
            if (tripDTO.getStartTime() != null) {
                exsitingTrip.setStartTime(tripDTO.getStartTime());
            }
            if (tripDTO.getPrice() != 0) {
                exsitingTrip.setPrice(tripDTO.getPrice());
            }
            if (tripDTO.getCategory() != null) {
                exsitingTrip.setCategory(tripDTO.getCategory());
            }
            if (tripDTO.getGuide() != null) {
                exsitingTrip.setGuide(tripDTO.getGuide());
            }

            em.getTransaction().commit();

            return new TripDTO(exsitingTrip);       // Returns the updated in DTO form
        } catch (Exception e) {
            throw new ApiException(500, "something went wrong while updating the doctor.");

        }

    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, id);    // Uses em.find search for the Trip with that id

            List<Guide> guides = em.createQuery("SELECT g FROM Guide g JOIN g.trips t WHERE t.id = :tripId", Guide.class)
                    .setParameter("tripId", id)
                    .getResultList();                 // Query selects all Guide entities that are associated with the Trip

            for (Guide g : guides) {
                g.getTrips().remove(trip);  // Removes the trip from the guide's trips list
                em.merge(g);                // Persist the updated guide with the trip removed
            }

            em.remove(trip);
            em.getTransaction().commit();
        }

    }

    // Adds a guide to a trip
    // tripId = What trip you want to add
    // guideId = What guide you want to add the trip to

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, tripId);         // Uses em.find search for the Trip with that id

            if (trip == null) {
                throw new ApiException(400, "Trip " + tripId + " does not exist.");
            }
            Guide guide = em.find(Guide.class, guideId);    // Uses em.find search for the Guide with that id

            if (guide == null) {
                throw new ApiException(400, "Guide " + guideId + " does not exist.");
            }

            trip.setGuide(guide);                           // Sets the guide id on that trip
            em.getTransaction().commit();
        }

    }


    @Override
    public List<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {

            Guide guide = em.find(Guide.class, guideId);   // Uses em.find search for the Guide with that id

            if (guide == null) {
                throw new ApiException(400, "Guide " + guideId + " does not exist.");
            }

            List<Trip> trips = guide.getTrips();
            List<TripDTO> tripDTOList = new ArrayList<>();  // Loops over the guides list of trips
            for (Trip t : trips) {
                tripDTOList.add(new TripDTO(t));        // Adds and converts them to DTO list
            }

            return tripDTOList;                         // returns that list.

        }
    }


// -------------------------- TASK 5 Streams - didn't make in time --------------------------


    public List<TripDTO> getByTripCategory(Category category) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Create a query to find trips by category
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t WHERE t.category = :category", Trip.class)
                    .setParameter("category", category)
                    .getResultList();

            List<TripDTO> dtoList = new ArrayList<>();

            for (Trip t : trips) {
                TripDTO dto = new TripDTO(t);   // Convert to DTo form entity
                dtoList.add(dto);

            }

            // Commit the transaction
            em.getTransaction().commit();

            return dtoList; // Return the list
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            throw new RuntimeException("Failed to retrieve doctors by speciality: " + e.getMessage());
        }


    }


}
