package uz.dev.streamingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.dev.streamingservice.entity.Subscription;

import java.time.LocalDate;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Modifying
    @Query("""
                UPDATE Subscription s
                SET s.status = 'EXPIRED'
                WHERE s.endTime < :today AND s.status <> 'EXPIRED'
            """)
    void updateExpiredStatuses(@Param("today") LocalDate today);

}