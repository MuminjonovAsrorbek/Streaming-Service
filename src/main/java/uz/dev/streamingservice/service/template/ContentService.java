package uz.dev.streamingservice.service.template;

import uz.dev.streamingservice.dto.response.ContentDTO;
import uz.dev.streamingservice.dto.response.PageableDTO;
import uz.dev.streamingservice.dto.request.*;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:39
 **/

public interface ContentService {

    PageableDTO getAll(Integer page, Integer pageSize);

    ContentDTO getById(Long id);

    void create(CreateContentDTO contentDTO);

    void update(CreateContentDTO contentDTO, Long id);

    void delete(Long id);

    PageableDTO getAllByFilter(ContentFilterDTO contentFilterDTO);

    void rateContent(Long contentId, RateContentDTO rateContentDTO);

    void watchContent(Long id, WatchContentDTO contentDTO);

    Double getAvgRating(Long id);

    void addActors(CreateActorInContentDTO actorDTO, Long id);

    void addGenres(CreateGenreInContentDTO genreDTO, Long id);
}
