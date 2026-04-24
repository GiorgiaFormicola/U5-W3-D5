package GiorgiaFormicola.U5_W3_D5.repositories;

import GiorgiaFormicola.U5_W3_D5.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, UUID> {
    int countReservationsByEvent_Id(UUID eventId);

    boolean existsByCustomer_IdAndEvent_Id(UUID customerId, UUID eventId);

    boolean existsByCustomer_IdAndEvent_Date(UUID customerId, LocalDate eventDate);
}
