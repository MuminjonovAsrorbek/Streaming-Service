package uz.dev.streamingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.streamingservice.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}