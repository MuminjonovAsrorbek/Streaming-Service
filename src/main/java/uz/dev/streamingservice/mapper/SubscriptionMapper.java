package uz.dev.streamingservice.mapper;

import org.springframework.stereotype.Component;
import uz.dev.streamingservice.dto.response.SubscriptionDTO;
import uz.dev.streamingservice.entity.Subscription;
import uz.dev.streamingservice.mapper.template.BaseMapper;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:39
 **/

@Component
public class SubscriptionMapper implements BaseMapper<Subscription, SubscriptionDTO> {
    @Override
    public Subscription toEntity(SubscriptionDTO subscriptionDTO) {

        return new Subscription(
                subscriptionDTO.getType(),
                subscriptionDTO.getStartTime(),
                subscriptionDTO.getEndTime(),
                subscriptionDTO.getStatus()
        );

    }

    @Override
    public SubscriptionDTO toDTO(Subscription subscription) {

        return new SubscriptionDTO(
                subscription.getId(),
                subscription.getType(),
                subscription.getStartTime(),
                subscription.getEndTime(),
                subscription.getStatus()
        );

    }
}
