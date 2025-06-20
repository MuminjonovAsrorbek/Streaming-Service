package uz.dev.streamingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.streamingservice.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}