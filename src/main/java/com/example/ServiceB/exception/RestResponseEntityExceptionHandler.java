package com.example.ServiceB.exception;

import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.response.ApiResponseBody;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

  /**
   * Because any function handle for all exception relate to 4xx, 5xx error are use an common logic,
   * this function help return a common error body.
   *
   * @param exception common exception
   * @param code      HTTP STATUS CODE
   * @return ApiResponseBody
   */
  private ResponseEntity<ApiResponseBody> response(Exception exception,
      HttpStatus code) {

    exception.printStackTrace();
    ApiResponseBody body = ApiResponseBody.builder()
        .status(code)
        .message(exception.getMessage())
        .build();

    return ResponseEntity.status(code).body(body);
  }

  /**
   * Handle the others exception, that usually occurs in the internal system.
   *
   * @param exception common exception
   * @return 500 status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseBody> handleAllException(Exception exception) {
    return response(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle all errors relative to 404 (example: not found item, item doesn't exits, or item was
   * deleted)
   *
   * @param exception ItemNotFoundException
   * @return 404 status
   */
  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<ApiResponseBody> handleNotFoundResourceException(
      Exception exception) {
    return response(exception, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle all error
   *
   * @param exception ItemAlreadyExistsException
   * @return response body with error and status code message
   */
  @ExceptionHandler(ItemAlreadyExistsException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public ResponseEntity<ApiResponseBody> handleItemAlreadyExistsException(
      Exception exception) {
    return response(exception, HttpStatus.CONFLICT);
  }

  /**
   * All invalid data error with be response with bad request message.
   *
   * @param exception Invalid Data Exception
   * @return bad request message
   */
  @ExceptionHandler(InvalidDataException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponseBody> handleInvalidDataException(Exception exception) {
    return response(exception, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponseBody handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ApiResponseBody.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message("BAD_REQUEST")
        .data(errors)
        .build();
  }

  /**
   * Handle common exception instead of extends ResponseEntityExceptionHandler
   *
   * @param ex      exception
   * @param request request
   * @return response body with error
   * @throws Exception exception
   */
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
      HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
      MissingPathVariableException.class, MissingServletRequestParameterException.class,
      ServletRequestBindingException.class, ConversionNotSupportedException.class,
      TypeMismatchException.class, HttpMessageNotReadableException.class,
      HttpMessageNotWritableException.class,
      MissingServletRequestPartException.class, BindException.class,
      NoHandlerFoundException.class, AsyncRequestTimeoutException.class})
  @Nullable
  public final ResponseEntity<ApiResponseBody> handleMyException(Exception ex,
      WebRequest request) throws Exception {

    ex.printStackTrace();

    ApiResponseBody body = ApiResponseBody.builder()
        .status(HttpStatus.BAD_REQUEST)
        .data("400 bad request")
        .build();

    return ResponseEntity.badRequest().body(body);
  }

}
