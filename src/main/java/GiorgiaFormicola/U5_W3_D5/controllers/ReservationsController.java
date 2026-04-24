package GiorgiaFormicola.U5_W3_D5.controllers;

import GiorgiaFormicola.U5_W3_D5.entities.Reservation;
import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.PayloadValidationException;
import GiorgiaFormicola.U5_W3_D5.payloads.ReservationDTO;
import GiorgiaFormicola.U5_W3_D5.services.ReservationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationsController {
    private final ReservationsService reservationsService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PROMOTER')")
    public Reservation saveMyReservation(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated ReservationDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.reservationsService.save(currentAuthenticatedUser, body);
    }
}
