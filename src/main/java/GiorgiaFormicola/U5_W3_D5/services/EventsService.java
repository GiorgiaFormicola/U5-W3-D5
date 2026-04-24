package GiorgiaFormicola.U5_W3_D5.services;

import GiorgiaFormicola.U5_W3_D5.entities.Event;
import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W3_D5.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W3_D5.exceptions.UnauthorizedException;
import GiorgiaFormicola.U5_W3_D5.payloads.EventDTO;
import GiorgiaFormicola.U5_W3_D5.repositories.EventsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class EventsService {
    private final EventsRepository eventsRepository;

    public Event save(User currentAuthenticatedUser, EventDTO body) {
        if (eventsRepository.existsByTitle(body.title()) && eventsRepository.existsByDateAndLocation(body.date(), body.location()))
            throw new BadRequestException("Event with title '" + body.title() + "' on date " + body.date() + " at " + body.location() + " already planned!");
        if (eventsRepository.existsByDateAndLocation(body.date(), body.location()))
            throw new BadRequestException("Another event is already planned at " + body.location() + " on date " + body.date());
        Event newEvent = new Event(body.title(), body.description(), body.date(), body.location(), body.availableSeats(), currentAuthenticatedUser);
        Event savedEvent = this.eventsRepository.save(newEvent);
        log.info("Event with id " + savedEvent.getId() + " successfully saved!");
        return savedEvent;
    }

    public Event findById(UUID eventId) {
        return this.eventsRepository.findById((eventId)).orElseThrow(() -> new NotFoundException("event", eventId));
    }

    public Event findByIdAndUpdate(User currentAuthenticatedUser, UUID eventId, EventDTO body) {
        Event found = this.findById(eventId);
        if (!found.getPromoter().getId().equals(currentAuthenticatedUser.getId()))
            throw new UnauthorizedException("Access denied, you don't have the required permission");
        if (found.getDate().isBefore(LocalDate.now())) throw new BadRequestException("Event already passed");
        if (!found.getDate().equals(body.date()) || !found.getLocation().equals(body.location())) {
            if (eventsRepository.existsByTitle(body.title()) && eventsRepository.existsByDateAndLocation(body.date(), body.location()))
                throw new BadRequestException("Event with title '" + body.title() + "' on date " + body.date() + " at " + body.location() + " already planned!");
            if (eventsRepository.existsByDateAndLocation(body.date(), body.location()))
                throw new BadRequestException("Another event is already planned at " + body.location() + " on date " + body.date());
        }
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setDate(body.date());
        found.setLocation(body.location());
        //TODO: check di quante prenotazioni già esistono
        found.setAvailableSeats(body.availableSeats());
        Event updatedEvent = this.eventsRepository.save(found);
        log.info("Event with id " + updatedEvent.getId() + " successfully modified");
        return updatedEvent;
    }

    public Page<Event> findAll(int page, int size, String sortBy) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 5;
        Pageable pageable;
        pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventsRepository.findAll(pageable);
    }

    public Page<Event> findAllByPromoter(User currentAuthenticatedUser, int page, int size, String sortBy) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 5;
        Pageable pageable;
        pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventsRepository.findAllByPromoter_Id(currentAuthenticatedUser.getId(), pageable);
    }
}
