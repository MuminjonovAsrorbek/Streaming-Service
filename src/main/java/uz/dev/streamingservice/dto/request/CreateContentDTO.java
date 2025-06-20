package uz.dev.streamingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.streamingservice.enums.AgeLimitEnum;
import uz.dev.streamingservice.enums.ContentTypeEnum;
import uz.dev.streamingservice.enums.PremiumStatus;

import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 17:43
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContentDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer publishedYear;

    @NotNull
    private Integer duration;

    @NotNull
    private ContentTypeEnum contentType;

    @NotNull
    private AgeLimitEnum ageLimit;

    @NotNull
    private PremiumStatus premiumStatus;

    private Set<Long> genresId;

    private Set<Long> actorsId;

}
