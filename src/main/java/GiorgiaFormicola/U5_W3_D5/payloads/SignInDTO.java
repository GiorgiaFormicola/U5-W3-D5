package GiorgiaFormicola.U5_W3_D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignInDTO(
        @NotBlank(message = "Name is mandatory and it can't contain only blank spaces")
        @Size(min = 2, message = "Name must contain minimum 2 characters")
        String name,
        @NotBlank(message = "Surname is mandatory and it can't contain only blank spaces")
        @Size(min = 2, message = "Surname must contain minimum 2 characters")
        String surname,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email must follow a valid email format")
        String email,
        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, message = "Password must contain at least 8 characters")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Password must follow a valid password format")
        String password,
        @NotBlank(message = "Role is mandatory")
        @Pattern(regexp = "^(CUSTOMER|PROMOTER)$", message = "Role must be one of the following values: CUSTOMER, PROMOTER")
        String role
) {
}
