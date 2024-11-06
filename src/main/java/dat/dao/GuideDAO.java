package dat.dao;

import dat.dto.GuideDTO;
import dat.dto.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class GuideDAO implements IDAO<GuideDTO, Long>{
    private EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf){
        this.emf = emf;

    }

    @Override
    public GuideDTO getById(Long id) {                              //Task a id in
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);                 // Uses em.find search for the Trip with that id
            return guide != null ? new GuideDTO(guide) : null;      // Returns if not null
        }
    }

    @Override
    public List<GuideDTO> getAll() {                            // Finds all trips entity's in database
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<Guide> guideList = query.getResultList();       // Safes them to a list
            List<GuideDTO> guideDTOList = new ArrayList<>();
            for (Guide g : guideList) {                         // Loops over the list to convert to DTO
                guideDTOList.add(new GuideDTO(g));              // Adds them
            }
            return guideDTOList;                                // Retuens it
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
            Guide guide = new Guide(guideDTO);                          // Convert DTO to entity
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                                                                     // Check if a Guide with the same name already exists
                Guide existingDoctor = em.createQuery("SELECT g FROM Guide g WHERE g.firstName = :name", Guide.class)
                        .setParameter("name", guide.getFirstName())
                        .getResultStream()
                        .findFirst()
                        .orElse(null);

                if (existingDoctor != null) {
                    throw new ApiException(400, "Guild " + guide.getFirstName() + " already exists.");
                }

                                                                    // Persist the new guide
                em.persist(guide);
                em.getTransaction().commit();

                                                                    // Return the created GuideDTO
                return new GuideDTO(guide);                         // Convert entity back to DTO
            } catch (Exception e) {

                throw new ApiException(500, "something went wrong while creating a guide.");
            }
    }

    @Override
    public GuideDTO update(Long id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = em.find(Guide.class, id);     // Uses em.find search for the Guide with that id
            if(guide == null) {
                em.getTransaction().rollback();          // If there isn't a trip with that id, returns null
                return null;
            }
            if(guide.getFirstName() != null) {          // Making sure nothing is null
                guide.setFirstName(guideDTO.getFirstName());
            }
            if(guide.getLastName() != null) {             // Updating by setting the new data on the old data
                guide.setLastName(guideDTO.getLastName());
            }
            if(guide.getEmail() != null){
                guide.setEmail(guideDTO.getEmail());
            }
            if(guide.getPhone() != 0){
                guide.setPhone(guideDTO.getPhone());
            }

            em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);       // Returns the updated in DTO form

        } catch (Exception e) {
            throw new ApiException(500, "something went wrong while updating a guide");
        }

    }

    @Override
    public void delete(Long id) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = em.find(Guide.class, id);         // Uses em.find search for the Guide with that id
            if (guide != null) {
                guide.getTrips().clear();                   // Deletes the list of Trip from that Guide / or clears the list
                em.remove(guide);                           // Removes guide
            }
            em.getTransaction().commit();                   // Commit changes
        }

    }
}
