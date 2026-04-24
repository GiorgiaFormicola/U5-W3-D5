package GiorgiaFormicola.U5_W3_D5.services;

import GiorgiaFormicola.U5_W3_D5.entities.User;
import GiorgiaFormicola.U5_W3_D5.enums.RoleType;
import GiorgiaFormicola.U5_W3_D5.exceptions.BadRequestException;
import GiorgiaFormicola.U5_W3_D5.payloads.SignInDTO;
import GiorgiaFormicola.U5_W3_D5.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder bCryptEncoder;

    public User save(SignInDTO body) {
        if (usersRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already in use!");
        User newUser = new User(body.name(), body.surname(), body.email(), this.bCryptEncoder.encode(body.password()), RoleType.valueOf(body.role()));
        User savedUser = this.usersRepository.save(newUser);
        log.info(savedUser.getRole().name() + " with id " + savedUser.getId() + " successfully saved");
        return savedUser;
    }
}
