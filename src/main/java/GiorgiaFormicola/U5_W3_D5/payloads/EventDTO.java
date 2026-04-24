package GiorgiaFormicola.U5_W3_D5.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EventDTO(
        @NotBlank(message = "Title is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 255, message = "Title must contain between 2 and 255 characters")
        String title,
        @NotNull(message = "Description is mandatory")
        @Size(max = 500, message = "Description must contain maximum 500 characters")
        @Pattern(regexp = "^$|.*\\S.*", message = "Description can't contain only blank spaces")
        String description,
        @NotNull(message = "Date is mandatory")
        @Future(message = "Date must be in the future")
        LocalDate date,
        @NotBlank(message = "Location is mandatory and it can't contain only blank spaces")
        @Size(min = 2, max = 255, message = "Location must contain between 2 and 255 characters")
        String location,
        @Positive(message = "Available seats must be bigger than zero")
        int availableSeats
) {
}
