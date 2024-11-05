package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data

public class GuideDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
    @ToString.Exclude
    private List<Trip> trips;

    public GuideDTO(Long id, String firstName, String lastName, String email, int phone, int yearsOfExperience, List<Trip> trips) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
    }

    public GuideDTO(Long id, String firstName, String lastName, String email, int phone, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public GuideDTO(Guide guide){
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        this.trips = guide.getTrips();
    }

    @Override
    public String toString() {
        return "Guide{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", yearsOfExperience=" + yearsOfExperience +
                // Avoid printing trips to prevent recursive calls
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuideDTO guideDTO)) return false;
        return phone == guideDTO.phone && yearsOfExperience == guideDTO.yearsOfExperience && Objects.equals(id, guideDTO.id) && Objects.equals(firstName, guideDTO.firstName) && Objects.equals(lastName, guideDTO.lastName) && Objects.equals(email, guideDTO.email) && Objects.equals(trips, guideDTO.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, yearsOfExperience, trips);
    }
}
