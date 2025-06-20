package uz.dev.streamingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.streamingservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIdNot(String email, Long id);

}