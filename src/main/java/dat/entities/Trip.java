package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dto.TripDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    /*

    public Trip(Long id, LocalDate startTime, LocalDate endTime, String startPosition, String name, int price, Category category){
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.id = id;

    }

     */

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


}
