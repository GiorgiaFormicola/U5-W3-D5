package GiorgiaFormicola.U5_W3_D5.services;

import GiorgiaFormicola.U5_W3_D5.entities.Event;
import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W3_D5.payloads.EventDTO;
import GiorgiaFormicola.U5_W3_D5.repositories.EventsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
