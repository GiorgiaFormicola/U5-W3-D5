package GiorgiaFormicola.U5_W3_D5.services;

import GiorgiaFormicola.U5_W3_D5.entities.Event;
import GiorgiaFormicola.U5_W3_D5.entities.Reservation;
import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W3_D5.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W3_D5.payloads.ReservationDTO;
import GiorgiaFormicola.U5_W3_D5.repositories.ReservationsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;
    private final EventsService eventsService;

    public Reservation save(User currentAutheticatedUser, ReservationDTO body) {
        Event eventFound = eventsService.findById(body.eventId());
        if (eventFound.getDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Error during reservation, event has already taken place");
        if (this.reservationsRepository.existsByCustomer_IdAndEvent_Id(currentAutheticatedUser.getId(), eventFound.getId()))
            throw new BadRequestException("Error during reservation, you have already made a reservation for the provided event");
        if (this.reservationsRepository.existsByCustomer_IdAndEvent_Date(currentAutheticatedUser.getId(), eventFound.getDate()))
            throw new BadRequestException("Error during reservation, you have already made another reservation on date " + eventFound.getDate());
        if (this.reservationsRepository.countReservationsByEvent_Id(eventFound.getId()) == eventFound.getAvailableSeats())
            throw new BadRequestException("Error during reservation, event is sold out");
        Reservation newReservation = new Reservation(eventFound, currentAutheticatedUser);
        Reservation savedReservation = this.reservationsRepository.save(newReservation);
        log.info("Reservation with id " + savedReservation.getId() + " successfully saved!");
        return savedReservation;
    }

    public Reservation findById(UUID reservationId) {
        return this.reservationsRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("reservation", reservationId));
    }

    public Reservation findByEventIdAndCustomerId(UUID eventId, UUID customerId) {
        return this.reservationsRepository.findByEvent_IdAndCustomer_Id(eventId, customerId).orElseThrow(() -> new NotFoundException(eventId, customerId));
    }

    public void findByEventIdAndCustomerIdAndDelete(User currentAuthenticatedUser, UUID eventId) {
        Event eventFound = this.eventsService.findById(eventId);
        Reservation found = this.findByEventIdAndCustomerId(eventFound.getId(), currentAuthenticatedUser.getId());
        if (found.getEvent().getDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Error during deleting the event, event has already taken place");
        this.reservationsRepository.delete(found);
    }
}
