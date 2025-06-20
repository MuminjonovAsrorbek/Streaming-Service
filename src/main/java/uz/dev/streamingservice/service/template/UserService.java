package uz.dev.streamingservice.service.template;

import uz.dev.streamingservice.dto.request.BuySubscriptionDTO;
import uz.dev.streamingservice.dto.response.*;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 20:35
 **/

public interface UserService extends BaseService<UserDTO, Long> {

    PageableDTO getAll(Integer page, Integer pageSize);

    void buySubscription(Long id, BuySubscriptionDTO subscriptionDTO);

    SubscriptionDTO getUserSubscription(Long id);

    void cancelSubscription(Long id);

    List<WatchHistoryDTO> getUserWatchHistory(Long id);

    List<ContentDTO> getRecommendations(Long id);

    List<ContinueWatchingDTO> getUserWatching(Long id);

}
