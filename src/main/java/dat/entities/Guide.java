package dat.entities;

import dat.dto.GuideDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor


public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;

    // One-to-Many relationship with Trip
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Trip> trips = new ArrayList<>();
    /*

    public Guide(Long id, String firstName, String lastName, String email, int phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.id = id;
    }

     */

    public Guide(Long id, String firstName, String lastName, String email, int phone, int yearsOfExperience, List<Trip> trips) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
        this.id = id;
    }

    public Guide(GuideDTO dto){
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.yearsOfExperience = dto.getYearsOfExperience();
        this.trips = dto.getTrips();
        this.id = dto.getId();
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
        if (!(o instanceof Guide guide)) return false;
        return phone == guide.phone && yearsOfExperience == guide.yearsOfExperience && Objects.equals(id, guide.id) && Objects.equals(firstName, guide.firstName) && Objects.equals(lastName, guide.lastName) && Objects.equals(email, guide.email) && Objects.equals(trips, guide.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, yearsOfExperience, trips);
    }
}
