package GiorgiaFormicola.U5_W3_D5.repositories;

import GiorgiaFormicola.U5_W3_D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
