package uz.dev.streamingservice.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.RatingDTO;
import uz.dev.streamingservice.entity.Rating;
import uz.dev.streamingservice.mapper.template.BaseMapper;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:51
 **/

@Component
@Getter
public class RatingMapper implements BaseMapper<Rating, RatingDTO> {
    @Override
    public Rating toEntity(RatingDTO ratingDTO) {

        return null;

    }

    @Override
    public RatingDTO toDTO(Rating rating) {

        return new RatingDTO(
                rating.getId(),
                rating.getRate(),
                rating.getComment(),
                rating.getContent().getId(),
                rating.getUser().getId()
        );

    }
}
