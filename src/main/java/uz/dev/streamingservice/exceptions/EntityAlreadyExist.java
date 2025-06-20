package uz.dev.streamingservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/19/25 10:49
 **/

@Getter
public class EntityAlreadyExist extends RuntimeException {
    private final HttpStatus status;

    public EntityAlreadyExist(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
