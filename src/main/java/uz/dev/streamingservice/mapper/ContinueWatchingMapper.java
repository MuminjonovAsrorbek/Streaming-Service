package uz.dev.streamingservice.mapper;

import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.ContinueWatchingDTO;
import uz.dev.streamingservice.entity.Content;
import uz.dev.streamingservice.entity.WatchHistory;

import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 14:34
 **/

@Component
public class ContinueWatchingMapper {

    public ContinueWatchingDTO toDTO(Content content, Long userId) {

        Optional<Integer> integer = content.getHistories().stream().filter(history -> history.getUser().getId().equals(userId)).findFirst().map(WatchHistory::getProgress);

        Integer watchedMinutes = null;

        if (integer.isPresent()) {

            watchedMinutes = integer.get();

        }

        return new ContinueWatchingDTO(
                content.getId(),
                content.getName(),
                watchedMinutes,
                content.getDuration()
        );

    }
}
