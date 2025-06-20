package uz.dev.streamingservice.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.*;
import uz.dev.streamingservice.entity.*;
import uz.dev.streamingservice.mapper.template.BaseMapper;
import uz.dev.streamingservice.utils.CommonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:36
 **/

@Component
@Getter
public class ContentMapper implements BaseMapper<Content, ContentDTO> {

    private final GenreMapper genreMapper;

    private final ActorMapper actorMapper;

    private final RatingMapper ratingMapper;

    private final WatchHistoryMapper watchHistoryMapper;

    public ContentMapper(GenreMapper genreMapper, ActorMapper actorMapper, RatingMapper ratingMapper, WatchHistoryMapper watchHistoryMapper) {
        this.genreMapper = genreMapper;
        this.actorMapper = actorMapper;
        this.ratingMapper = ratingMapper;
        this.watchHistoryMapper = watchHistoryMapper;
    }

    @Override
    public Content toEntity(ContentDTO contentDTO) {

        return null;

    }


    @Override
    public ContentDTO toDTO(Content content) {

        Set<GenreDTO> genreDTOS = CommonUtil.getOrDefault(content.getGenres(), new HashSet<Genre>())
                .stream()
                .map(genreMapper::toDTO).collect(Collectors.toSet());

        Set<ActorDTO> actorDTOS = CommonUtil.getOrDefault(content.getActors(), new HashSet<Actor>())
                .stream()
                .map(actorMapper::toDTO).collect(Collectors.toSet());

        List<RatingDTO> ratingDTOS = CommonUtil.getOrDefault(content.getRatings(), new HashSet<Rating>())
                .stream()
                .map(ratingMapper::toDTO).toList();

        List<WatchHistoryDTO> historyDTOS = CommonUtil.getOrDefault(content.getHistories(), new HashSet<WatchHistory>())
                .stream()
                .map(watchHistoryMapper::toDTO).toList();

        return new ContentDTO(
                content.getId(),
                content.getName(),
                content.getDescription(),
                content.getPublishedYear(),
                content.getDuration(),
                content.getContentType(),
                content.getAgeLimit(),
                content.getPremiumStatus(),
                genreDTOS,
                actorDTOS,
                ratingDTOS,
                historyDTOS
        );

    }
}
