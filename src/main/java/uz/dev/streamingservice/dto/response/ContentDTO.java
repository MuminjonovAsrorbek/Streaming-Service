package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.streamingservice.enums.AgeLimitEnum;
import uz.dev.streamingservice.enums.ContentTypeEnum;
import uz.dev.streamingservice.enums.PremiumStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link uz.dev.streamingservice.entity.Content}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO implements Serializable {

    private Long id;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<GenreDTO> genres;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ActorDTO> actors;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RatingDTO> ratings;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<WatchHistoryDTO> histories;
}