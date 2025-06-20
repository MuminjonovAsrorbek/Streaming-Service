package uz.dev.streamingservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/13/25 17:55
 **/

@Getter
public class EntityNotFound extends RuntimeException {

    private final HttpStatus status;

    public EntityNotFound(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
