package uz.dev.streamingservice.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.streamingservice.dto.response.ErrorDTO;
import uz.dev.streamingservice.dto.response.FieldErrorDTO;
import uz.dev.streamingservice.exceptions.EntityAlreadyExist;
import uz.dev.streamingservice.exceptions.EntityNotFound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 5/28/25 14:48
 **/

@RestControllerAdvice("uz.dev.streamingservice")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> handle(RuntimeException e) {

        ErrorDTO error = new ErrorDTO(
                500,
                "Internal Server Error: " + e.getMessage()
        );

        return ResponseEntity
                .status(500)
                .body(error);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();

        List<FieldErrorDTO> fieldErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            fieldErrors.add(new FieldErrorDTO(fieldName, message));
        }

        ErrorDTO error = new ErrorDTO(
                400,
                "Field not valid",
                fieldErrors
        );

        return ResponseEntity
                .status(400)
                .body(error);
    }

    @ExceptionHandler(value = EntityAlreadyExist.class)
    public ResponseEntity<ErrorDTO> handle(EntityAlreadyExist e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

    @ExceptionHandler(value = EntityNotFound.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotFound e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

}
