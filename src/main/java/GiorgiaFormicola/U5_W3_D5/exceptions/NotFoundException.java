package GiorgiaFormicola.U5_W3_D5.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String email) {
        super("The user with email " + email + " has not been found.");
    }
}
