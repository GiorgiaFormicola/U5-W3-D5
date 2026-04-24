package GiorgiaFormicola.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "location"})})
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "promoter_id", nullable = false)
    private User promoter;


    protected Event() {
    }

    public Event(String title, String description, LocalDate date, String location, int availableSeats, User promoter) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
        this.promoter = promoter;
    }

}
