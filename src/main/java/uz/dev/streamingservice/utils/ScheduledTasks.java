package uz.dev.streamingservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.dev.streamingservice.service.SubscriptionService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 1 * * *")
    public void updateExpiredSubscriptions() {
        subscriptionService.markExpiredSubscriptions();
    }
}
