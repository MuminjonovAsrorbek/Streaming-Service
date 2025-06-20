package uz.dev.streamingservice.mapper;

import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.WatchHistoryDTO;
import uz.dev.streamingservice.entity.WatchHistory;
import uz.dev.streamingservice.mapper.template.BaseMapper;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:54
 **/

@Component
public class WatchHistoryMapper implements BaseMapper<WatchHistory, WatchHistoryDTO> {
    @Override
    public WatchHistory toEntity(WatchHistoryDTO watchHistoryDTO) {
        return null;
    }

    @Override
    public WatchHistoryDTO toDTO(WatchHistory watchHistory) {

        return new WatchHistoryDTO(
                watchHistory.getId(),
                watchHistory.getWatchDate(),
                watchHistory.getProgress(),
                watchHistory.getUser().getId(),
                watchHistory.getContent().getId()
        );

    }
}
