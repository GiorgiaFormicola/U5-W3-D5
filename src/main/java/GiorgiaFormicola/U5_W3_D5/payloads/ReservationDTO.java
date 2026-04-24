package GiorgiaFormicola.U5_W3_D5.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationDTO(
        @NotNull(message = "Event id is mandatory")
        UUID eventId
) {
}
