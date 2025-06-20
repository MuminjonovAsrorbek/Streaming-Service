package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 20:52
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateContentDTO {

    private Long userId;

    private Integer rate;

    private String comment;

}