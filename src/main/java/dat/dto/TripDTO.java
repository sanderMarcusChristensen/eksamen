package dat.dto;

import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Data;

import java.time.LocalDate;

@Data

public class TripDTO {
    private Long id;
    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private String name;
    private int price;
    private Category category;
    private Guide guide;

    // Default constructor
    public TripDTO() {
    }

    // Constructor with parameters
    public TripDTO(Long id, LocalDate startTime, LocalDate endTime, String startPosition, String name, int price, Category category, Guide guide) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    // Constructor that converts Trip to TripDTO
    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guide = trip.getGuide();
    }
}
