package GiorgiaFormicola.U5_W3_D5.services;

import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.exceptions.NotFoundException;
import GiorgiaFormicola.U5_W3_D5.exceptions.UnauthorizedException;
import GiorgiaFormicola.U5_W3_D5.payloads.LoginDTO;
import GiorgiaFormicola.U5_W3_D5.security.TokenTools;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsersService usersService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bCryptEncoder;

    public String checkUserCredentialsAndGenerateToken(LoginDTO body) {
        try {
            User found = this.usersService.findByEmail(body.email());
            if (!bCryptEncoder.matches(body.password(), found.getPassword()))
                throw new UnauthorizedException("Wrong credentials supplied");
            return this.tokenTools.generateToken(found);
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Wrong credentials supplied");
        }
    }
}
