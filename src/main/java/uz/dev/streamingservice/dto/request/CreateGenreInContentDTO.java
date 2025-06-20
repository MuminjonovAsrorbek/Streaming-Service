package uz.dev.streamingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 12:59
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateGenreInContentDTO {

    private List<Long> genreIds;

}
