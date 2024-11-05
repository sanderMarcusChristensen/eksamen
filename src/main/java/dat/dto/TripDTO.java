package dat.dto;

import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Data

public class TripDTO {
    private Long id;
    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private String name;
    private int price;
    private Category category;
    @ToString.Exclude
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

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startPosition='" + startPosition + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                // Avoid printing guide to prevent recursive calls
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripDTO tripDTO)) return false;
        return price == tripDTO.price && Objects.equals(id, tripDTO.id) && Objects.equals(startTime, tripDTO.startTime) && Objects.equals(endTime, tripDTO.endTime) && Objects.equals(startPosition, tripDTO.startPosition) && Objects.equals(name, tripDTO.name) && category == tripDTO.category && Objects.equals(guide, tripDTO.guide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, startPosition, name, price, category, guide);
    }
}
