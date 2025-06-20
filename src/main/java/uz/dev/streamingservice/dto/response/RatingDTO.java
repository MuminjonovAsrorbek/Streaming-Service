package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.dev.streamingservice.entity.Rating}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer rate;

    private String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long contentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;
}