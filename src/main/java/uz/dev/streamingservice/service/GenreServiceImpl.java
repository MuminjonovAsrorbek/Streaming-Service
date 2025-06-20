package uz.dev.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.streamingservice.dto.response.GenreDTO;
import uz.dev.streamingservice.entity.Genre;
import uz.dev.streamingservice.mapper.GenreMapper;
import uz.dev.streamingservice.repository.GenreRepository;
import uz.dev.streamingservice.service.template.GenreService;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:43
 **/

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreMapper genreMapper;

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void create(GenreDTO genreDTO) {

        Genre genre = new Genre();

        genre.setName(genreDTO.getName());

        genreRepository.save(genre);
    }
}
