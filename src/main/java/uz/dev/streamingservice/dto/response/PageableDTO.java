package uz.dev.streamingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 17:12
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableDTO {

    private Integer totalPages;

    private Long totalElements;

    private boolean hasNext;

    private boolean hasPrevious;

    private Object objects;

}
