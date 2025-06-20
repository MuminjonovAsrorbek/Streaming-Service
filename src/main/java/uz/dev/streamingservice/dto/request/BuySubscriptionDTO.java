package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.streamingservice.enums.PremiumStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 20:33
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuySubscriptionDTO {

    private PremiumStatus subscriptionType;

    private Integer durationMonths;

}
