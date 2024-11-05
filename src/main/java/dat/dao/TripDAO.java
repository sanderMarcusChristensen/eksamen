package dat.dao;

import dat.dto.TripDTO;
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
    public TripDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null ? new TripDTO(trip) : null;
        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);

            List<Trip> tripList = query.getResultList();
            List<TripDTO> tripDTOList = new ArrayList<>();
            for (Trip t : tripList) {
                tripDTOList.add(new TripDTO(t));
            }
            return tripDTOList;
        }
    }

    // could add more "security" to this later, right now it only checks on name;
    @Override
    public TripDTO create(TripDTO tripDTO) {
        Trip trip = new Trip(tripDTO); // Convert DTO to entity
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            /*

            // Check if a doctor with the same name already exists
            Trip existingDoctor = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", trip.getName())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingDoctor != null) {
                throw new ApiException(400, "Trip " + trip.getName() + " already exists.");
            }

             */


            em.persist(trip);
            em.getTransaction().commit();

            // Return the created DoctorDTO
            return new TripDTO(trip); // Convert entity back to DTO
        }
    }

    @Override
    public TripDTO update(Long id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip exsitingTrip = em.find(Trip.class, id);

            if (exsitingTrip == null) {
                em.getTransaction().rollback();
                return null;
            }

            if (tripDTO.getName() != null) {
                exsitingTrip.setName(tripDTO.getName());
            }
            if (tripDTO.getId() != null) {
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
            //assert exsitingTrip != null;
            return new TripDTO(exsitingTrip);
        } catch (Exception e) {
            throw new ApiException(500, "something went wrong while updating the doctor.");

        }

    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, id);

            List<Guide> guides = em.createQuery("SELECT g FROM Guide g JOIN g.trips t WHERE t.id = :tripId", Guide.class)
                    .setParameter("tripId", id)
                    .getResultList();

            for (Guide g : guides) {
                g.getTrips().remove(trip);
                em.merge(g);
            }

            em.remove(trip);
            em.getTransaction().commit();
        }

    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, tripId);    // could just be the "getById" method

            if (trip == null) {
                throw new ApiException(400, "Trip " + tripId + " does not exist.");
            }
            Guide guide = em.find(Guide.class, guideId);

            if (guide == null) {
                throw new ApiException(400, "Guide " + guideId + " does not exist.");
            }

            trip.setGuide(guide);
            em.getTransaction().commit();
        }

    }

    @Override
    public List<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {

            Guide guide = em.find(Guide.class, guideId);

            if (guide == null) {
                throw new ApiException(400, "Guide " + guideId + " does not exist.");
            }

           List<Trip> trips = guide.getTrips();
            List<TripDTO> tripDTOList = new ArrayList<>();
            for (Trip t : trips) {
                tripDTOList.add(new TripDTO(t));
            }

            return tripDTOList;

        }
    }
}
