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
    public GuideDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return guide != null ? new GuideDTO(guide) : null;
        }
    }

    @Override
    public List<GuideDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<Guide> guideList = query.getResultList();
            List<GuideDTO> guideDTOList = new ArrayList<>();
            for (Guide g : guideList) {
                guideDTOList.add(new GuideDTO(g));
            }
            return guideDTOList;
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
            Guide guide = new Guide(guideDTO); // Convert DTO to entity
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                // Check if a doctor with the same name already exists
                Guide existingDoctor = em.createQuery("SELECT g FROM Guide g WHERE g.firstName = :name", Guide.class)
                        .setParameter("name", guide.getFirstName())
                        .getResultStream()
                        .findFirst()
                        .orElse(null);

                if (existingDoctor != null) {
                    throw new ApiException(400, "Guild " + guide.getFirstName() + " already exists.");
                }

                // Persist the new doctor
                em.persist(guide);
                em.getTransaction().commit();

                // Return the created DoctorDTO
                return new GuideDTO(guide); // Convert entity back to DTO
            } catch (Exception e) {

                throw new ApiException(500, "something went wrong while creating a guide.");
            }
    }

    @Override
    public GuideDTO update(Long id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = em.find(Guide.class, id);
            if(guide == null) {
                em.getTransaction().rollback();
                return null;
            }
            if(guide.getFirstName() != null) {
                guide.setFirstName(guideDTO.getFirstName());
            }
            if(guide.getLastName() != null) {
                guide.setLastName(guideDTO.getLastName());
            }
            if(guide.getEmail() != null){
                guide.setEmail(guideDTO.getEmail());
            }
            if(guide.getPhone() != 0){
                guide.setPhone(guideDTO.getPhone());
            }

            // needs a check on the trips on the guide
            em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);

        } catch (Exception e) {
            throw new ApiException(500, "something went wrong while updating a guide");
        }

    }

    @Override
    public void delete(Long id) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                // Clear ingredients associations to delete join table entries
                guide.getTrips().clear();
                em.remove(guide);
            }
            em.getTransaction().commit();
        }

    }
}
