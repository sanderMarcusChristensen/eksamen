package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dto.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor



public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private Category category;

    // Many-to-One relationship with Guide
    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;


    public Trip(Long id, LocalDate startTime, LocalDate endTime, String startPosition, String name, int price, Category category, Guide guide){
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
        this.id = id;

    }

    public Trip(TripDTO dto) {
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.startPosition = dto.getStartPosition();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.guide = dto.getGuide();

    }

    // ------------ What i would have done ------------
/*
    public Trip(TripDTO dto) {
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.startPosition = dto.getStartPosition();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();

        if (dto.getGuide() != null) {
            this.guide = new Guide(dto.getGuide());
        }

    }
     */

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
        if (!(o instanceof Trip trip)) return false;
        return price == trip.price && Objects.equals(id, trip.id) && Objects.equals(startTime, trip.startTime) && Objects.equals(endTime, trip.endTime) && Objects.equals(startPosition, trip.startPosition) && Objects.equals(name, trip.name) && category == trip.category && Objects.equals(guide, trip.guide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, startPosition, name, price, category, guide);
    }
}
