package uz.dev.streamingservice.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.GenreDTO;
import uz.dev.streamingservice.entity.Genre;
import uz.dev.streamingservice.mapper.template.BaseMapper;

import java.util.HashSet;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:47
 **/

@Component
@Getter
public class GenreMapper implements BaseMapper<Genre, GenreDTO> {

    @Override
    public Genre toEntity(GenreDTO genreDTO) {

        return new Genre(
                genreDTO.getName(),
                new HashSet<>()
        );

    }

    @Override
    public GenreDTO toDTO(Genre genre) {

        return new GenreDTO(
                genre.getId(),
                genre.getName()
        );

    }
}
