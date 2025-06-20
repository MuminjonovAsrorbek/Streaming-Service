package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link uz.dev.streamingservice.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long subscriptionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RatingDTO> ratings;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<WatchHistoryDTO> histories;
}