package com.example.springbootAngular.exceptionHandler;
import com.example.springbootAngular.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ErrorMessageDTO> handleCustomBadRequestException(CustomBadRequestException ex) {
        return new ResponseEntity<>(new ErrorMessageDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenRequestException.class)
    public ResponseEntity<ErrorMessageDTO> handleCustomForbiddenRequestException(CustomForbiddenRequestException ex) {
        return new ResponseEntity<>(new ErrorMessageDTO(ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}

