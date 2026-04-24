package GiorgiaFormicola.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@ToString
public class Reservation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "requestDate", nullable = false)
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    protected Reservation() {
    }

    public Reservation(Event event, User customer) {
        this.requestDate = LocalDate.now();
        this.event = event;
        this.customer = customer;
    }
}
