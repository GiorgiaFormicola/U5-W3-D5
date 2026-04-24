package GiorgiaFormicola.U5_W3_D5.exceptions;

import GiorgiaFormicola.U5_W3_D5.payloads.ErrorDTO;
import GiorgiaFormicola.U5_W3_D5.payloads.ErrorsListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequestException(BadRequestException ex) {
        return new ErrorDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(PayloadValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsListDTO handlePayloadValidationException(PayloadValidationException ex) {
        return new ErrorsListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrors());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ErrorDTO("Not valid value provided", LocalDateTime.now());
    }
}
