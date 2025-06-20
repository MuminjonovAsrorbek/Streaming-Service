package uz.dev.streamingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 14:25
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContinueWatchingDTO {

    private Long contentId;
    private String name;
    private Integer watchedMinutes;
    private Integer totalDuration;

}
