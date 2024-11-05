package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Data;

import java.util.List;

@Data

public class GuideDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
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
}
