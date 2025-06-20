package uz.dev.streamingservice.mapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.RatingDTO;
import uz.dev.streamingservice.dto.response.UserDTO;
import uz.dev.streamingservice.dto.response.WatchHistoryDTO;
import uz.dev.streamingservice.entity.Rating;
import uz.dev.streamingservice.entity.User;
import uz.dev.streamingservice.entity.WatchHistory;
import uz.dev.streamingservice.mapper.template.BaseMapper;
import uz.dev.streamingservice.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:50
 **/

@Component
@Getter
public class UserMapper implements BaseMapper<User, UserDTO> {

    private final RatingMapper ratingMapper;

    private final WatchHistoryMapper watchHistoryMapper;

    public UserMapper(RatingMapper ratingMapper, WatchHistoryMapper watchHistoryMapper) {
        this.ratingMapper = ratingMapper;
        this.watchHistoryMapper = watchHistoryMapper;
    }

    @Override
    public User toEntity(UserDTO userDTO) {

        return null;

    }

    @Override
    public UserDTO toDTO(User user) {

        List<RatingDTO> ratingDTOS = CommonUtil.getOrDefault(user.getRatings(), new ArrayList<Rating>())
                .stream()
                .map(ratingMapper::toDTO).toList();

        List<WatchHistoryDTO> historyDTOS = CommonUtil.getOrDefault(user.getHistories(), new ArrayList<WatchHistory>())
                .stream()
                .map(watchHistoryMapper::toDTO).toList();

        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getSubscription().getId(),
                ratingDTOS,
                historyDTOS
        );

    }
}
