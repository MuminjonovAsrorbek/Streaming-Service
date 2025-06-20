package uz.dev.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.streamingservice.repository.SubscriptionRepository;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 15:37
 **/

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void markExpiredSubscriptions() {

        LocalDate today = LocalDate.now();

        subscriptionRepository.updateExpiredStatuses(today);

    }
}

