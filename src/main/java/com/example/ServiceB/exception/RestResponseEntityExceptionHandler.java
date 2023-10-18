package com.example.ServiceB.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.response.ErrorResponseBody;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Because any function handle for all exception relate to 4xx, 5xx error are
     * use an common logic, this function help return an common error body.
     *
     * @param exception
     * @param code      HTTP STATUS CODE
     * @return ErrorResponseBody
     */
    private ResponseEntity<ErrorResponseBody> response(Exception exception,
            HttpStatus code) {
        ErrorResponseBody body = ErrorResponseBody.builder()
                .code(code.value())
                .messgage(exception.getMessage())
                .build();

        return ResponseEntity.status(code).body(body);
    }

    /**
     * Handle the others exception, that usually occurs in the internal system.
     *
     * @param exception
     * @return 500 status
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseBody> handleAllException(Exception exception) {
        return response(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all errors relative to 404 (example: not found item, item doesn't exits, or item was
     * deleted)
     *
     * @param exception
     * @return 404 status
     */
    @ExceptionHandler(value = {ItemNotFoundException.class})
    protected ResponseEntity<ErrorResponseBody> handleNotFoundResourceException(
            Exception exception) {
        return response(exception, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle all error
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {ItemAlreadyExistsException.class})
    @ResponseStatus(code = HttpStatus.CONFLICT)
    protected ResponseEntity<ErrorResponseBody> handleItemAlreadyExistsException(
            Exception exception) {
        return response(exception, HttpStatus.CONFLICT);
    }
}
