package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 21:01
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WatchContentDTO {

    private Long userId;

    private Integer progressInMinutes;

}