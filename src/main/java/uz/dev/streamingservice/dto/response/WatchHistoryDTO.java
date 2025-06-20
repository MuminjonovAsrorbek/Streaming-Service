package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link uz.dev.streamingservice.entity.WatchHistory}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WatchHistoryDTO implements Serializable {

    private Long id;

    private LocalDate watchDate;

    private Integer progress;

    private Long userId;

    private Long contentId;
}