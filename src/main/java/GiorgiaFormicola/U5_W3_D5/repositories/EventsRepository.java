package GiorgiaFormicola.U5_W3_D5.repositories;

import GiorgiaFormicola.U5_W3_D5.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Event, UUID> {
    boolean existsByDateAndLocation(LocalDate date, String location);

    boolean existsByTitle(String title);

    Page<Event> findAllByPromoter_Id(UUID promoterId, Pageable pageable);

    @Query("SELECT r.event FROM Reservation r WHERE r.customer.id = :customerId")
    Page<Event> findAllReservedByCustomer(UUID customerId, Pageable pageable);

}
