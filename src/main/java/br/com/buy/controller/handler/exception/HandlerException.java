package br.com.buy.controller.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import br.com.buy.controller.handler.exception.model.ApiException;
import br.com.buy.service.exception.CartNotFoundException;
import br.com.buy.service.exception.FoodNotFoundException;
import br.com.buy.service.exception.ProductNotFoundException;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

  private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<ApiException> handleCartNotFoundException(CartNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND)
        .body(createResponse(ex.getMessage(), NOT_FOUND.value()));
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiException> handleProductNotFoundException(ProductNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND)
        .body(createResponse(ex.getMessage(), NOT_FOUND.value()));
  }

  @ExceptionHandler(FoodNotFoundException.class)
  public ResponseEntity<ApiException> handleFoodNotFoundException(FoodNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND)
        .body(createResponse(ex.getMessage(), NOT_FOUND.value()));
  }

  private ApiException createResponse(String message, int httpValue) {
    return new ApiException(message, httpValue);
  }

}
