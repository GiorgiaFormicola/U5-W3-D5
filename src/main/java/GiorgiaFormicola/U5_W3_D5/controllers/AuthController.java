package GiorgiaFormicola.U5_W3_D5.controllers;

import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.PayloadValidationException;
import GiorgiaFormicola.U5_W3_D5.payloads.SignInDTO;
import GiorgiaFormicola.U5_W3_D5.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UsersService usersService;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public User signIn(@RequestBody @Validated SignInDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.usersService.save(body);
    }

}
