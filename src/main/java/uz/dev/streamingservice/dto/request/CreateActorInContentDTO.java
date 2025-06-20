package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:53
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateActorInContentDTO {

    private List<Long> actorIds;

}
