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
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Trip> trips = new ArrayList<>();

    //cascade = CascadeType.ALL removes everything when deleted
    //orphanRemoval = true only removes the deleted part and orphaned the other


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

// ------------ What i would have done ------------

    /*
    public Guide(GuideDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.yearsOfExperience = dto.getYearsOfExperience();
        this.id = dto.getId();

        if (dto.getTrips() != null) {
            this.trips = dto.getTrips().stream()
                    .map(Trip::new)
                    .collect(Collectors.toList());
        }
    }
     */

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
