package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.streamingservice.enums.PremiumStatus;
import uz.dev.streamingservice.enums.SubscriptionStatusEnum;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link uz.dev.streamingservice.entity.Subscription}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO implements Serializable {

    private Long id;

    private PremiumStatus type;

    private LocalDate startTime;

    private LocalDate endTime;

    private SubscriptionStatusEnum status;
}