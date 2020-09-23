package br.com.buy.service.exception;

public class FoodNotFoundException extends RuntimeException {

  public FoodNotFoundException(String message) {
    super(message);
  }

}
