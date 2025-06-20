package uz.dev.streamingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.dev.streamingservice.entity.template.AbsLongEntity;
import uz.dev.streamingservice.enums.PremiumStatus;
import uz.dev.streamingservice.enums.SubscriptionStatusEnum;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:17
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Subscription extends AbsLongEntity {

    @Enumerated(value = EnumType.STRING)
    private PremiumStatus type;

    private LocalDate startTime;

    private LocalDate endTime;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatusEnum status;


}
