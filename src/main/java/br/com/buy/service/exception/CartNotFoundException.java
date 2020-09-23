package br.com.buy.service.exception;

public class CartNotFoundException extends RuntimeException {
  public CartNotFoundException(String id) {
    super("cart not found with id[" + id + "]");
  }
}
