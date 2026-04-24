package GiorgiaFormicola.U5_W3_D5.controllers;

import GiorgiaFormicola.U5_W3_D5.entities.Event;
import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.PayloadValidationException;
import GiorgiaFormicola.U5_W3_D5.payloads.EventDTO;
import GiorgiaFormicola.U5_W3_D5.services.EventsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventsController {
    private final EventsService eventsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('PROMOTER')")
    public Event saveNewEvent(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated EventDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.eventsService.save(currentAuthenticatedUser, body);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PROMOTER')")
    public Page<Event> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return this.eventsService.findAll(page, size, sortBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('PROMOTER')")
    public Page<Event> getPromoterEvents(
            @AuthenticationPrincipal User currentAuthenticatedUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy) {
        return this.eventsService.findAllByPromoter(currentAuthenticatedUser, page, size, sortBy);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('PROMOTER')")
    public Event getEventByIdAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @PathVariable UUID eventId, @RequestBody @Validated EventDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errorsList);
        }
        return this.eventsService.findByIdAndUpdate(currentAuthenticatedUser, eventId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('PROMOTER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getEventByIdAndDelete(@AuthenticationPrincipal User currentAuthenticatedUser, @PathVariable UUID eventId) {
        this.eventsService.findByIdAndDelete(currentAuthenticatedUser, eventId);
    }

    @GetMapping("/me/reservations")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PROMOTER')")
    public Page<Event> getMyReservedEvents(
            @AuthenticationPrincipal User currentAuthenticatedUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "event.date") String sortBy) {
        return this.eventsService.findAllEventsReservedByCustomer(currentAuthenticatedUser, page, size, sortBy);
    }
}
