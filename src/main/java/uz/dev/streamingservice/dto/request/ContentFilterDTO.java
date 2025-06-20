package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.streamingservice.enums.AgeLimitEnum;
import uz.dev.streamingservice.enums.ContentTypeEnum;
import uz.dev.streamingservice.enums.PremiumStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 18:24
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentFilterDTO {

    private Integer page;

    private String name;

    private Integer publishedYear;

    private ContentTypeEnum contentType;

    private AgeLimitEnum ageLimit;

    private PremiumStatus premiumStatus;

    private String genre;

    private String actor;

    private Integer minRating;

    private Integer maxRating;

}
